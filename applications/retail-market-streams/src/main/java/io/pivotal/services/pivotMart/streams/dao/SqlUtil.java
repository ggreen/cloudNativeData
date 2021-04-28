package io.pivotal.services.pivotMart.streams.dao;

public class SqlUtil
{
    public static String escape(String associations)
    {
        if (associations == null)
            return "''";

        return "'" + associations.replace("'", "''") + "'";
    }
}