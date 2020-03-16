package org.steps.util;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.steps.entity.HadoopBean;

import java.net.URI;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 13:47
 **/
public class HdfsUtil {
    public static String mkdir(HadoopBean hadoop)throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
        Path newFolderPath= new Path(hadoop.getPath());
        if(!fs.exists(newFolderPath)) {
            fs.mkdirs(newFolderPath);
            fs.close();
            return ConstAttr.SUCCESS;
        }else{
            return ConstAttr.FAILURE;
        }
    }
    public static String delete(HadoopBean hadoop)throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
        Path newFolderPath= new Path(hadoop.getPath());
        if(fs.exists(newFolderPath)) {

            fs.close();
            return ConstAttr.SUCCESS;
        }else{
            return ConstAttr.FAILURE;
        }
    }
    public static String copyFromLocal(HadoopBean hadoop)throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
        try{
            fs.copyFromLocalFile(new Path(hadoop.getPathFrom()),new Path(hadoop.getPath()));
            fs.close();
            return ConstAttr.SUCCESS;
        }catch (Exception e){
            return ConstAttr.FAILURE;
        }

    }

    public static String getFileFromHDFS(HadoopBean hadoop) {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        try{
            FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
            Path dest = new Path(hadoop.getPath());
            Path src = new Path(hadoop.getPathFrom());
            fs.copyToLocalFile(false, src, dest, true);
            fs.close();
            return ConstAttr.SUCCESS;
        }catch (Exception e){
            return ConstAttr.FAILURE;
        }
    }
    public static String move(HadoopBean hadoop)throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
        try{
            fs.rename(new Path(hadoop.getPathFrom()),new Path(hadoop.getPath()));
            fs.close();
            return ConstAttr.SUCCESS;
        }catch (Exception e){
            return ConstAttr.FAILURE;
        }

    }
    public static String cat(HadoopBean hadoop)throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
        Path readPath = new Path(hadoop.getPath());
        FSDataInputStream inStream = fs.open(readPath);
        try{
            byte[] bs = new byte[1024 * 1024];
            int len = 0;
            String str="";
            while((len = inStream.read(bs)) != -1){
                str+=new String(bs, 0, len);
            }
            fs.close();
            return str;
        }catch (Exception e){
            IOUtils.closeStream(inStream);
            return ConstAttr.FAILURE;
        }

    }

    public static String chmod(HadoopBean hadoop)throws Exception{
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", hadoop.getUri());
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
        conf.set("fs.file.impl", org.apache.hadoop.fs.LocalFileSystem.class.getName());
        System.setProperty("HADOOP_USER_NAME",hadoop.getUser());
        System.setProperty("hadoop.home.dir", "/");
        try{
            FileSystem fs = FileSystem.get(URI.create(hadoop.getUri()), conf);
            Path path = new Path(hadoop.getPath());
//            FsPermission permission = new FsPermission(FsAction.ALL,FsAction.ALL,FsAction.ALL);
            fs.setPermission((path), new FsPermission(hadoop.getPrivige()));
            fs.close();
            return ConstAttr.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ConstAttr.FAILURE;
        }
    }

    public static void main(String[] args)throws Exception {
        HadoopBean bean =new HadoopBean();
        bean.setUri("hdfs://192.168.30.9:9000/");
        bean.setUser("root");
        bean.setPath("/123.jar");
        bean.setPrivige("777");
        String hah=HdfsUtil.chmod(bean);
        System.out.println(hah);
    }
}