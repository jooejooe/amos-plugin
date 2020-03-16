package org.steps.util;

import com.cloudera.sqoop.Sqoop;
import com.cloudera.sqoop.tool.SqoopTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.steps.entity.SqoopBean;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-18 09:28
 **/
public class SqoopService {
          //确保权限，不然在本地运行
          //https://www.cnblogs.com/claren/p/7240735.html
        public static String sqoopService(SqoopBean bean, String[] argument)throws Exception {
//            System.setProperty("HADOOP_USER_NAME","root");
            Configuration conf= new Configuration();
            conf.set("fs.default.name",bean.getUri());
            conf.set("yarn.resourcemanager.address", bean.getYarn());
            conf.set("mapreduce.framework.name", "yarn");
            conf.set("mapreduce.app-submission.cross-platform","true");
            String property=System.getProperty("HADOOP_MAPRED_HOME");
            if(null==property||"".equalsIgnoreCase(property)){
                throw new Exception("NO HADOOP_MAPRED_HOME Configure in Env");
            }

            try{
                SqoopTool tool=SqoopTool.getTool(bean.getMethod());
                Sqoop sqoop = new Sqoop((com.cloudera.sqoop.tool.SqoopTool) tool, conf);
                int res = Sqoop.runSqoop(sqoop, argument);
                return ConstAttr.SUCCESS;
            }catch (Exception e){
                return ConstAttr.FAILURE;
            }
    }

    public static void main(String[] args)throws Exception {
        SqoopBean bean =new SqoopBean();
        bean.setMethod("import");
        bean.setUri("hdfs://hadoop:9000/");
        bean.setYarn("hadoop:8032");
        String[] argument = new String[]{
                "--connect", "jdbc:mysql://192.168.30.56:3306/kettle?useUnicode=true&characterEncoding=UTF-8&&serverTimezone=UTC",
                "--username", "paohaijiao",
                "--password", "13579admin",
                "--table", "r_user",
                "--target-dir","/sqoop/table15" ,
                "--fields-terminated-by","paohaijiao"
        };
//        bean.setMethod("export");
//        String[] argument = new String[]{
//                "--connect", "jdbc:mysql://192.168.30.56:3306/kettle?useUnicode=true&characterEncoding=UTF-8&&serverTimezone=UTC",
//                "--username", "paohaijiao",
//                "--password", "13579admin",
//                "--table", "r_user",
//                "--export-dir","/sqoop/table13/part-m-00000" ,
//                "--input-fields-terminated-by","\001"
//        };
        System.out.println(StringUtils.join(argument,","));

        sqoopService(bean,argument);
        }
}