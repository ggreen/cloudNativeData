package io.pivotal.market.api.product;

import io.pivotal.gemfire.domain.Beacon;
import io.pivotal.gemfire.domain.Product;
import io.pivotal.gemfire.domain.ProductAssociate;
import io.pivotal.gemfire.domain.Promotion;
import io.pivotal.market.api.order.OrderJdbcDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductJdbcDao
{
    private final OrderJdbcDAO orderJdbcDAO;
    private final JdbcTemplate jdbcTemplate;

    public ProductJdbcDao(OrderJdbcDAO orderJdbcDAO, JdbcTemplate jdbcTemplate)
    {
        this.orderJdbcDAO = orderJdbcDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Set<Product> selectProductsByBeacon(Beacon beacon)
    {

        String sql = "select distinct c.categoryid, c.categoryname,c.subcategoryname,p.productid,p.productname, p.unit,p.cost,p.price \n" +
                "from pivotalmarkets.beacon b, \n" +
                "pivotalmarkets.category c,\n" +
                "pivotalmarkets.product p\n" +
                "where b.category = c.categoryname \n" +
                "and \n" +
                "( p.categoryid = c.categoryid or\n" +
                "  p.subcategoryid = c.categoryid)\n" +
                "  and b.uuid = ? or (b.major = ? and b.minor = ?)";

        System.out.println("sql:" + sql);
        RowMapper<Product> rm = (rs, rowNbr) ->
        {
            Product p = new Product();
            p.setProductName(rs.getString("productname"));
            p.setPrice(rs.getBigDecimal("price"));
            p.setProductId(rs.getInt("productid"));
            p.setUnit(rs.getBigDecimal("unit"));
            return p;
        };

        Object[] args = {beacon.getUuid(), beacon.getMajor(), beacon.getMinor()};
        List<Product> list = jdbcTemplate.query(sql,rm,args);

        return list != null ? new HashSet<Product>(list) : null;
    }

    public java.util.Set<io.pivotal.gemfire.domain.Promotion> selectPromotionsByProduct(io.pivotal.gemfire.domain.Product product)
    {
        if (product == null)
            return null;
		
		/*
		 * startdate
			enddate
			marketingmessage
			marketingimageurl

		 */
        String sql = "select * from pivotalmarkets.promotion where productid = ?";

        System.out.println("sql:" + sql);

        RowMapper<Promotion> rm = (rs, rowNbr) ->
        {
            Promotion p = new Promotion();
            p.setPromotionId(rs.getInt("promotionid"));
            p.setStartDate(new Date(rs.getDate("startdate").getTime()));
            p.setEndDate(new Date(rs.getDate("enddate").getTime()));
            p.setMarketingMessage(rs.getString("marketingmessage"));
            p.setMarketingUrl(rs.getString("marketingimageurl"));
            p.setProductId(rs.getInt("productid"));
            return p;
        };

        Object[] args = {product.getProductId()};
        List<Promotion> list = jdbcTemplate.query(sql, rm,args);

        return list != null ? new HashSet<Promotion>(list) : null;
    }

    public Product findProductById(int productId)
    {
        String sql = "SELECT productid, productname, categoryid, subcategoryid, unit, cost, price, startdate, enddate, " +
                " createddate, lastupdateddate " +
                " FROM pivotalmarkets.product where productid = "+productId;

        ResultSetExtractor<Product> rowMapper = (ResultSet rs) ->
        {
            if(!rs.next())
                return null;

            Product product = new Product();
            product.setProductId(rs.getInt("productid"));
            product.setCategoryId(rs.getString("categoryid"));
            product.setSubCategoryId(rs.getString("subcategoryid"));
            product.setUnit(rs.getBigDecimal("unit"));
            product.setCost(rs.getBigDecimal("cost"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setStartDate(rs.getDate("startdate"));
            product.setEndDate(rs.getDate("enddate"));
            product.setProductName(rs.getString("productname"));
            return product;
        };

        return jdbcTemplate.query(sql ,rowMapper);
    }


    public java.util.List<java.lang.Integer> selectProductIds()
    {
        //ResultSet rs, int rowNum
        RowMapper<Integer> mapper = (rs, rowNum) -> rs.getInt(1);

        return jdbcTemplate.query("select productid from pivotalmarkets.product", mapper);
    }

    public Set<ProductAssociate> selectProductAssociates(Product product)
    {
        String sql = "select pre[1],post from pivotalmarkets.assoc_rules where lower(pre[1]) like lower('%" +
                product.getProductName().trim() + "%')";

        RowMapper<ProductAssociate> mapper = (rs, i) -> {
            Array posts = rs.getArray(2);
            String[] postsArray = (String[]) posts.getArray();
            ProductAssociate pa = new ProductAssociate(rs.getString(1), postsArray);
            return pa;
        };

        List<ProductAssociate> list = jdbcTemplate.query(sql, mapper);
        //this.jdbcTemplate.query(sql, mapper,product.getProductName());

        if (list == null || list.isEmpty())
            return null;

        return new HashSet<ProductAssociate>(list);
    }//------------------------------------------------
}