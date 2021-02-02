package io.pivotal.services.pivotMart.streams.analytics;

import io.pivotal.services.pivotMart.streams.dao.AssociateRepository;
import io.pivotal.services.pivotMart.streams.entity.ProductAssociationEntity;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.machineLearning.associations.AssociationProbabilities;
import nyla.solutions.core.patterns.machineLearning.associations.ProductAssociation;
import nyla.solutions.core.patterns.machineLearning.associations.ProductTransition;
import nyla.solutions.core.util.Organizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductOrderAnalyzerTest
{
    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AssociateRepository associateRepository;

    private ProductOrderAnalyzer subject;

    @Mock
    private ResultSet resultSet;

    private String sql = "";

    @Mock
    private AssociationProbabilities associationProbabilities ;

    @BeforeEach
    void setUp()
    {
        subject = new ProductOrderAnalyzer(sql,jdbcTemplate, associationProbabilities,associateRepository);
    }

    @Test
    void constructProductsAssociations()
    {
        //assoc_rules
        subject.constructProductsAssociations();

        verify(this.associationProbabilities).notifyFavoriteAssociations();

        verify(jdbcTemplate).query(anyString(),any(RowCallbackHandler.class));

    }

    @Test
    void transitionRowMapper() throws SQLException
    {
        ProductTransition<Integer> expected = new ProductTransition<Integer>(1,"hotdogs");
        when(resultSet.getInt(anyString())).thenReturn(expected.getTransitionId());
        when(resultSet.getString(anyString())).thenReturn(expected.getProductName());

        RowCallbackHandler rowMapper = subject.transitionRowMapper();
        assertNotNull(rowMapper);
        assertDoesNotThrow( () ->  rowMapper.processRow(null));

        assertDoesNotThrow( () ->  rowMapper.processRow(resultSet));
        when(resultSet.next()).thenReturn(true);

        assertDoesNotThrow( () -> rowMapper.processRow(resultSet));
        verify(associationProbabilities).learn(any(ProductTransition.class));
        

    }

    @Test
    void update()
    {
        String id = "hello";
        ProductAssociation data = new ProductAssociation(id);

        subject.update(id,data);

        verify(this.associateRepository).save(any());
    }

    @Test
    void toProductEntity()
    {
        assertNull(subject.toProductEntity(null));
        Set<String> expectedAssociate = Organizer.toSet("world");

        ProductAssociationEntity expected= JavaBeanGeneratorCreator.of(
                ProductAssociationEntity.class).create();
        expected.setAssociations(expectedAssociate);

        ProductAssociation productTransition = new ProductAssociation(expected.getId());

        productTransition.addAssociate(expectedAssociate.iterator().next());
        ProductAssociationEntity actual = subject.toProductEntity(productTransition);
        assertNotNull(actual);
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getAssociations()[0],actual.getAssociations()[0]);
    }
}