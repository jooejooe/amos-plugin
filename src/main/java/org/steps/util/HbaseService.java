package org.steps.util;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.steps.entity.HbaseBean;

import java.util.Arrays;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-18 12:57
 **/
public class HbaseService {
    /**配置参考
     * https://www.cnblogs.com/JamesXiao/p/6202372.html
     * @param bean
     * @return
     * @throws Exception
     */
    public static String createTable(HbaseBean bean) throws Exception{
        try{
            //本地的host要配置zopecker地址
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.property.clientPort", bean.getZoopeckerPort());
            configuration.set("hbase.zookeeper.quorum", bean.getZoopeckerHost());
            Connection connection = ConnectionFactory.createConnection(configuration);
            System.out.println(connection.isClosed());
            HBaseAdmin admin = new HBaseAdmin(configuration);
            HTableDescriptor table = new HTableDescriptor(Bytes.toBytes(bean.getTable()));
            Arrays.stream(bean.getFamilly().split(",")).forEach(e->{
                HColumnDescriptor family = new HColumnDescriptor(Bytes.toBytes(e));
                table.addFamily(family);
            });
            admin.createTable(table);
            return ConstAttr.SUCCESS;
        }catch (Exception e){
            return ConstAttr.FAILURE;
        }
    }

    public static void main(String[] args)throws Exception {
        HbaseBean bean=new HbaseBean();
        bean.setZoopeckerHost("192.168.30.9");
        bean.setZoopeckerPort("2181");
        bean.setMaster("192.168.30.9:16010");
        bean.setTable("student");
        bean.setFamilly("col,col1");
        System.out.println(createTable(bean));

    }
}