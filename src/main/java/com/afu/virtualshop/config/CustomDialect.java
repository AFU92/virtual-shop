package com.afu.virtualshop.config;

import org.hibernate.dialect.PostgreSQL10Dialect;

import java.sql.Types;

public class CustomDialect extends PostgreSQL10Dialect {
    public CustomDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json"); // JAVA_OBJECT = 2000
    }
}
