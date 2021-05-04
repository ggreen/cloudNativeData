package com.vmware.data.demo.retail.store.analytics.streams.dao;

public class SqlUtil
{
    public static String escape(String associations)
    {
        if (associations == null)
            return "''";

        return "'" + associations.replace("'", "''") + "'";
    }
}