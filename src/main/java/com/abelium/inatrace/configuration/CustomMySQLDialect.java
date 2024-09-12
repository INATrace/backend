package com.abelium.inatrace.configuration;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQLDialect extends MySQLDialect {

    @Override
    public void initializeFunctionRegistry(QueryEngine queryEngine) {

        super.initializeFunctionRegistry(queryEngine);

        queryEngine.getSqmFunctionRegistry()
                .register("GROUP_CONCAT", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }

}
