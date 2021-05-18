package com.vmware.data.demo.retail.store.api;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcUtil
{

    public static int nextSeqVal(JdbcTemplate jdbcTemplate,String sequenceName)
    {
        return jdbcTemplate.queryForObject("select nextval('"+sequenceName+"')", int.class);
    }
}
