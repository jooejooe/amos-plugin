//package database;
//
//import org.apache.commons.vfs2.FileObject;
//import org.apache.commons.vfs2.FileSystemException;
//import org.pentaho.di.core.database.BaseDatabaseMeta;
//import org.pentaho.di.core.database.DatabaseInterface;
//import org.pentaho.di.core.database.DatabaseMeta;
//import org.pentaho.di.core.exception.KettleDatabaseException;
//import org.pentaho.di.core.exception.KettleFileException;
//import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
//import org.pentaho.di.core.row.ValueMetaInterface;
//import org.pentaho.di.core.vfs.KettleVFS;
//
//import java.io.File;
//
///** ##################################https://blog.csdn.net/vah101/article/details/46881345/#############################
//############################################通用数据库组件##############################################
// *  /opt/cloudera/parcels/CLABS_PHOENIX-4.7.0-1.clabs_phoenix1.3.0.p0.000/bin/phoenix-sqlline.py localhost:2181:/hbase
// *  lib add phoenix-core-4.10.0-HBase-1.2.jar
// * select * from SYSTEM.CATALOG;
// */
//@DatabaseMetaPlugin(
//        type = "PhoenixJdbc",
//        typeDescription = "PhoenixJdbc"
//)
//public class PhoenixDatabaseMeta extends BaseDatabaseMeta implements DatabaseInterface {
//
//    public int[] getAccessTypeList() {
//        return new int[] { DatabaseMeta.TYPE_ACCESS_NATIVE, DatabaseMeta.TYPE_ACCESS_JNDI };
//    }
//
//    public int getDefaultDatabasePort() {
//        return 2181;
//    }
//    public String getSQLQueryFields( String tableName ) {
//        return "SELECT * FROM " + tableName + " WHERE 1=0";
//    }
//    public String getSQLTableExists( String tablename ) {
//        return getSQLQueryFields( tablename );
//    }
//
//    public String getSQLColumnExists( String columnname, String tablename ) {
//        return "SELECT " + columnname + " FROM " + tablename + " WHERE 1=0";
//    }
//
//    public String getDriverClass() {
//        return "org.apache.phoenix.jdbc.PhoenixDriver";
//    }
//    public String getURL( String hostname, String port, String databaseName ) throws KettleDatabaseException {
//        File dbName = new File( databaseName );
//        if ( dbName != null && !dbName.exists() ) {
//            // CSV-JDBC requires local file paths
//            // It may be a VFS path, test and convert to local if it is a VFS path
//            FileObject vfsObject;
//            try {
//                vfsObject = KettleVFS.getFileObject( databaseName );
//                if ( vfsObject != null && vfsObject.exists() ) {
//                    File temp = new File( vfsObject.getURL().getPath() );
//                    if ( temp.exists() ) {
//                        dbName = temp;
//                    }
//                }
//            } catch ( KettleFileException | FileSystemException e ) {
//                throw new KettleDatabaseException( e );
//            }
//        }
//        return "jdbc:phoenix:"+hostname+":"+port;
//    }
//
//    public boolean supportsOptionsInURL() {
//        return true;
//    }
//
//
//    public String getExtraOptionsHelpText() {
//        return "http://csvjdbc.sourceforge.net/";
//    }
//
//    public String[] getReservedWords() {
//        return new String[] { "SELECT", "DISTINCT", "AS", "FROM", "WHERE", "NOT", "AND", "OR", "ORDER", "BY", "ASC", "DESC",
//                "NULL", "COUNT", "LOWER", "MAX", "MIN", "ROUND", "UPPER", "BETWEEN", "IS", "LIKE","UPSERT" ,"OFFSET","LIMIT"};
//    }
//    public String[] getUsedLibraries() {
//        return new String[] { "phoenix-4.7.0-clabs-phoenix1.3.0-client.jar","phoenix-4.7.0-clabs-phoenix1.3.0-server.jar","phoenix-core-4.7.0-clabs-phoenix1.3.0.jar" };
//    }
//
//    public boolean supportsPreparedStatementMetadataRetrieval() {
//        return true;
//    }
//
//    public boolean supportsResultSetMetadataRetrievalOnly() {
//        return true;
//    }
//
//    public boolean releaseSavepoint() {
//        return true;
//    }
//
//    public boolean supportsTransactions() {
//        return true;
//    }
//
//
//    public String getFieldDefinition( ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr ) {
//        return "";
//    }
//
//
//    public String getAddColumnStatement( String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon ) {
//        return "";
//    }
//
//
//    public String getModifyColumnStatement( String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon ) {
//        return "";
//    }
//}
