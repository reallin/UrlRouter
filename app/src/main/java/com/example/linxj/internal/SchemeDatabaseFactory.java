package com.example.linxj.internal;

import com.example.SchemeDataBase;

public final class SchemeDatabaseFactory {
    public static final String SUFFIX = "$$SchemeDatabase";
    private static final String OUTER_CLASS = SchemeDispatcher.class.getName();

    public SchemeDatabaseFactory() {
    }

    public static String getDatabaseClassSimpleName() {
        return OUTER_CLASS.substring(OUTER_CLASS.lastIndexOf(".") + 1) + "$$SchemeDatabase";
    }

    public static String getDatabaseClassName() {
        return getDatabaseClassPackageName() + "." + getDatabaseClassSimpleName();
    }

    public static SchemeDataBase getDatabase() throws Exception {
        return (SchemeDataBase)Class.forName(getDatabaseClassName()).newInstance();
    }

    public static String getDatabaseClassPackageName() {
        return OUTER_CLASS.substring(0, OUTER_CLASS.lastIndexOf("."));
    }
}