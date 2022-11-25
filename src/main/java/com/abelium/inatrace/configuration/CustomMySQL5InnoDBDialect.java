package com.abelium.inatrace.configuration;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class CustomMySQL5InnoDBDialect extends MySQL5InnoDBDialect {

    public CustomMySQL5InnoDBDialect() {
        super();
        registerFunction("GROUP_CONCAT", new StandardSQLFunction("group_concat", new StringType()));
    }

}
