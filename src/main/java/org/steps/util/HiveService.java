package org.steps.util;
import org.apache.hadoop.conf.Configuration;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 18:16
 **/
public class HiveService {
    //hive --service hiveserver2
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    public static String getHiveService(String uri,String jdbc, String user, String password, String sql)throws Exception{
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", uri);
            conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
            conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
            System.setProperty("hadoop.home.dir", "/");
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(jdbc, user, password);
            Statement stmt = con.createStatement();
             stmt.execute(sql);
             return ConstAttr.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ConstAttr.FAILURE;
        }
    }
    public static void main(String[] args) throws Exception {
        String sql="create table t_order(id int,name string ,money float) row format delimited fields terminated by ','";
        HiveService.getHiveService("hdfs://192.168.30.9:9000/","jdbc:hive2://192.168.30.9:10000/default","","","drop table t_order");
        HiveService.getHiveService("hdfs://192.168.30.9:9000/","jdbc:hive2://192.168.30.9:10000/default","","",sql);

    }
}