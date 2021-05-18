package io.pivotal.market.api.customer;

import io.pivotal.gemfire.domain.*;
import io.pivotal.market.api.order.OrderJdbcDAO;
import nyla.solutions.core.util.Text;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.*;

public class CustomerDao
{
    private final OrderJdbcDAO retailJdbcDAO;
    private JdbcTemplate jdbcTemplate;

    public CustomerDao(OrderJdbcDAO retailJdbcDAO)
    {
        this.retailJdbcDAO = retailJdbcDAO;
    }

    public Set<CustomerFavorites> selectCustomerFavorites(CustomerIdentifier customerIdentifer)
    {
        int customerId = selectCustomerId(customerIdentifer);

        //String sql = "select cf.customerid, cf.productid, cf.count, p.productname from pivotalmarkets.customer_favorites cf, pivotalmarkets.product p where customerid = ? and cf.productid = p.productid";

        String sql = "SELECT customerid ,productid ,count\n" +
                "FROM (\n" +
                "  SELECT *\n" +
                "        , max(count) OVER (PARTITION BY customerid) AS _max_\n" +
                "        , row_number() OVER (PARTITION BY customerid, count ORDER BY random()) AS _rank_  -- include this line to randomly select one if ties unacceptable\n" +
                "  FROM (select customerid, productid, count(*)\n" +
                "    from (select o.customerid, i.productid, i.productname\n" +
                "    from orders o, order_items i\n" +
                "    where o.orderid = i.orderid and customerid = ?) as custOrders\n" +
                "    group by productid,customerid\n" +
                "    order by customerid ) aggregateQuery\n" +
                ") foo\n" +
                "WHERE count = _max_\n" +
                "AND _rank_ = 1";


        System.out.println("selectCustomerFavorites sql:" + sql);

        RowMapper<CustomerFavorites> rm = (rs, rowNum) ->
        {
            CustomerFavorites cp = new CustomerFavorites();
            cp.setCustomerId(customerId);

            Collection<ProductQuantity> productQuatities = new ArrayList<ProductQuantity>();


            ProductQuantity productQuantity = new ProductQuantity();
            Product product = new Product();
            product.setProductName(retailJdbcDAO.selectProduct(rs.getInt("productid")).getProductName());
            productQuantity.setProduct(product);
            productQuantity.setQuantity(rs.getInt("count"));
            productQuatities.add(productQuantity);

            cp.setProductQuanties(productQuatities);
            return cp;
        };
        Integer[] args = {customerId};

        List<CustomerFavorites> list = jdbcTemplate.query(sql, args, rm);

        if (list == null || list.isEmpty())
            return null;

        return new HashSet<CustomerFavorites>(list);
    }//------------------------------------------------
    public int selectCustomerId(io.pivotal.gemfire.domain.CustomerIdentifier customerIdentier)
	throws EmptyResultDataAccessException

    {
        String firstName = Text.initCaps(customerIdentier.getFirstName());
        String lastName = Text.initCaps(customerIdentier.getLastName());

        String sql = "select customerid from pivotalmarkets.customers c where (c.firstname = ? and c.lastname = ?) limit 1 ";
        // Object[] args, Class<T> requiredTypex
        Object[] args = {firstName, lastName};
        int customerId = jdbcTemplate.queryForObject(sql, args, Integer.class);
        return customerId;
    }
    public int registerCustomerByIdentifer(io.pivotal.gemfire.domain.CustomerIdentifier customerIdentifier)
    {


        try {
            int customerid = selectCustomerId(customerIdentifier);
            if (customerid > 0)
                return -1; //already exists;
        }
        catch (EmptyResultDataAccessException e) {
        }
        Customer customer = new Customer();
        customer.setFirstName(Text.initCaps(customerIdentifier.getFirstName()));
        customer.setLastName(Text.initCaps(customerIdentifier.getLastName()));
        customer.setMobileNumber(customerIdentifier.getMobileNumber());
        int customerId = retailJdbcDAO.nextSeqVal("customer_seq");
        customer.setCustomerId(customerId);

        String sql = "INSERT INTO \"pivotalmarkets\".\"customers\" (customerid,firstname,lastname,mobilenumber) VALUES (?,?,?,?)";

        jdbcTemplate.update(sql, customer.getCustomerId(), customer.getFirstName(), customer.getLastName(), customer.getMobileNumber());

        return customerId;
    }//------------------------------------------------
    public int updateCustomerFavorites()
    {
        //TODO: move to stored procedures
//		String sql = "\n" +
//		"set search_path to pivotalmarkets;\n" +
//		"\n" +
//		"drop table if exists customer_favorites;\n" +
//		"drop table if exists cp1;\n" +
//		"create temp table cp1 as\n" +
//		"    select o.customerid, i.productid, i.productname\n" +
//		"    from orders o, order_items i\n" +
//		"    where o.orderid = i.orderid;\n" +
//		"\n" +
//		"\n" +
//		"drop table if exists cp2;\n" +
//		"create temp table cp2 as\n" +
//		"    select customerid, productid, count(*)\n" +
//		"    from cp1\n" +
//		"    group by productid,customerid\n" +
//		"    order by customerid ;\n" +
//		"\n" +
//		"CREATE TABLE customer_favorites as\n" +
//		"SELECT customerid ,productid ,count\n" +
//		"FROM (\n" +
//		"  SELECT *\n" +
//		"        , max(count) OVER (PARTITION BY customerid) AS _max_\n" +
//		"        , row_number() OVER (PARTITION BY customerid, count ORDER BY random()) AS _rank_  -- include this line to randomly select one if ties unacceptable\n" +
//		"  FROM cp2\n" +
//		") foo\n" +
//		"WHERE count = _max_\n" +
//		"AND _rank_ = 1;\n" +
//		"\n" +
//		"ALTER TABLE customer_favorites ADD COLUMN productname TEXT;\n" +
//		"UPDATE customer_favorites c SET productname = (SELECT productname FROM product p WHERE p.productid = c.productid limit 1);\n";
//
//		return this.jdbcTemplate.update(sql);

        return 0;

    }
}