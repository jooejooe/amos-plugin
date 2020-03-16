package org.steps.util;

import org.pentaho.di.core.database.DatabaseMeta;

public class JdbcMaker {
    public static String judge(String type,DatabaseMeta rDatabase){
        if (ConstAttribute.POSTGRESQL.equalsIgnoreCase(type)) {
            return getPostgreSqlProtocol(rDatabase);
        }
        if (ConstAttribute.MARIADB.equalsIgnoreCase(type)) {
            return getMySqlProtocol(rDatabase);
        }
        if (ConstAttribute.MYSQL.equalsIgnoreCase(type)) {
            return getMySqlProtocol(rDatabase);
        }
        if (ConstAttribute.MSSQL.equalsIgnoreCase(type)) {
            return getSQLServerProtocol(rDatabase);
        }
        if (ConstAttribute.HIVE2.equalsIgnoreCase(type)) {
            return getHiveProtocol(rDatabase);
        }
        if (ConstAttribute.HIVE.equalsIgnoreCase(type)) {
            return getHiveProtocol(rDatabase);
        }
        return   getPostgreSqlProtocol(rDatabase);
    }
    public static String getPostgreSqlProtocol(DatabaseMeta db){
            return "jdbc:postgresql://"+db.getHostname()+":"+db.getDatabasePortNumberString()+"/"+db.getDatabaseName();
        }


    public static String getMySqlProtocol(DatabaseMeta db){
            return "jdbc:mysql://"+db.getHostname()+":"+db.getDatabasePortNumberString()+"/"+db.getDatabaseName();

    }
    public static String getSQLServerProtocol(DatabaseMeta db){
            return "jdbc:sqlserver://"+db.getHostname()+":"+db.getDatabasePortNumberString()+";DatabaseName="+db.getDatabaseName();

    }

    public static String getHiveProtocol(DatabaseMeta db) {
            return "jdbc:hive2://" + db.getHostname() + ":" + db.getDatabasePortNumberString()+ "/" + db.getDatabaseName();

    }


}
