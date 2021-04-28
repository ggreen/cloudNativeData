package io.pivotal.market.api.dao;


import com.vmware.data.retail.store.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;

@Component
public class PivotMarketPostgreDAO
{
	private final JdbcTemplate template;

	public PivotMarketPostgreDAO(JdbcTemplate template)
	{
		this.template = template;
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

		return template.query(sql ,rowMapper);
	}

}
