
package database;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleFileException;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.vfs.KettleVFS;

import java.io.File;

/**presto-jdbc-0.100.jar在lib下
 *输入sql实例select id as id,cast(user_name as varchar) as name from mysql.gaoxin.user
 */

@DatabaseMetaPlugin(
  type = "Presto",
  typeDescription = "Presto"
  )
public class PrestoDatabaseMeta extends BaseDatabaseMeta implements DatabaseInterface {

  public int[] getAccessTypeList() {
    return new int[] { DatabaseMeta.TYPE_ACCESS_NATIVE, DatabaseMeta.TYPE_ACCESS_JNDI };
  }

  public int getDefaultDatabasePort() {
    return 34445;
  }
  public String getSQLQueryFields( String tableName ) {
    return "SELECT * FROM " + tableName + " WHERE 1=0";
  }
  public String getSQLTableExists( String tablename ) {
    return getSQLQueryFields( tablename );
  }

  public String getSQLColumnExists( String columnname, String tablename ) {
    return "SELECT " + columnname + " FROM " + tablename + " WHERE 1=0";
  }

  public String getDriverClass() {
    return "com.facebook.presto.jdbc.PrestoDriver";
  }
  public String getURL( String hostname, String port, String databaseName ) throws KettleDatabaseException {
    File dbName = new File( databaseName );
    if ( dbName != null && !dbName.exists() ) {
      // CSV-JDBC requires local file paths
      // It may be a VFS path, test and convert to local if it is a VFS path
      FileObject vfsObject;
      try {
        vfsObject = KettleVFS.getFileObject( databaseName );
        if ( vfsObject != null && vfsObject.exists() ) {
          File temp = new File( vfsObject.getURL().getPath() );
          if ( temp.exists() ) {
            dbName = temp;
          }
        }
      } catch ( KettleFileException | FileSystemException e ) {
        throw new KettleDatabaseException( e );
      }
    }
    return "jdbc:presto://"+hostname +":"+port+"/system/runtime";
  }

  public boolean supportsOptionsInURL() {
    return true;
  }


  public String getExtraOptionsHelpText() {
    return "http://csvjdbc.sourceforge.net/";
  }

  public String[] getReservedWords() {
    return new String[] { "SELECT", "DISTINCT", "AS", "FROM", "WHERE", "NOT", "AND", "OR", "ORDER", "BY", "ASC", "DESC",
      "NULL", "COUNT", "LOWER", "MAX", "MIN", "ROUND", "UPPER", "BETWEEN", "IS", "LIKE" };
  }
  public String[] getUsedLibraries() {
    return new String[] { "presto-jdbc-0.100.jar" };
  }

  public boolean supportsPreparedStatementMetadataRetrieval() {
    return true;
  }

  public boolean supportsResultSetMetadataRetrievalOnly() {
    return true;
  }

  public boolean releaseSavepoint() {
    return true;
  }

  public boolean supportsTransactions() {
    return true;
  }


  public String getFieldDefinition( ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr ) {
    return "";
  }


  public String getAddColumnStatement( String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon ) {
    return "";
  }


  public String getModifyColumnStatement( String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon ) {
    return "";
  }
}
