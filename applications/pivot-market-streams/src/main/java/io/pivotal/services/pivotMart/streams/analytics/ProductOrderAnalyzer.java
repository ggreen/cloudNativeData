package io.pivotal.services.pivotMart.streams.analytics;

import io.pivotal.services.pivotMart.streams.dao.AssociateRepository;
import io.pivotal.services.pivotMart.streams.entity.ProductAssociationEntity;
import nyla.solutions.core.patterns.machineLearning.associations.AssociationProbabilities;
import nyla.solutions.core.patterns.machineLearning.associations.ProductAssociation;
import nyla.solutions.core.patterns.machineLearning.associations.ProductTransition;
import nyla.solutions.core.patterns.observer.SubjectObserver;
import nyla.solutions.core.util.Organizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductOrderAnalyzer implements SubjectObserver<ProductAssociation>
{
    private final JdbcTemplate jdbcTemplate;
    private final String selectSql;
    private final AssociationProbabilities analyzer;
    private final AssociateRepository associateRepository;

    public ProductOrderAnalyzer(@Value("${select.orders}") String selectSql, JdbcTemplate jdbcTemplate,
                                AssociationProbabilities analyzer,
                                AssociateRepository associateRepository)
    {
        this.jdbcTemplate = jdbcTemplate;
        this.selectSql = selectSql;
        this.analyzer = analyzer;
        this.associateRepository = associateRepository;
        this.analyzer.addObserver(this);

    }

    @Scheduled(fixedDelay = 5000)
    public void constructProductsAssociations()
    {
        /*
         *   itemid integer,
         *   orderid integer,
         *   productid integer,
         *   quantity float8,
         *   productname text
         */

        RowCallbackHandler callback = transitionRowMapper();
        jdbcTemplate.query(this.selectSql,callback);

        this.analyzer.notifyFavoriteAssociations();
    }

    protected RowCallbackHandler transitionRowMapper()
    {
        return (rs) ->
        {
            if(rs == null || !rs.next())
                return ;

            this.analyzer.learn(
                    new ProductTransition(rs.getInt("orderid"),rs.getString("productname")));
        };
    }

    @Override
    public void update(String subjectName, ProductAssociation data)
    {
        this.associateRepository.save(toProductEntity(data));
    }

    protected ProductAssociationEntity toProductEntity(ProductAssociation data)
    {
        if(data == null)
            return null;

        ProductAssociationEntity entity = new ProductAssociationEntity(data.getProductName(),
                data.getAssociateNames());

        return entity;


    }
}
