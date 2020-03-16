package org.steps.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class RestTemplateUtil{
    private final static Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);
    private static Properties props;
    static {
        String fileName = "rest.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(RestTemplateUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常"+e.getMessage());
        }
    }
    public static String getSparkUri(){

        return  props.getProperty("sparkUri");

    }
    public static void send(String uri)throws Exception{
        String protocol=props.getProperty("protocol");
        String host=props.getProperty("host");
        String port=props.getProperty("port");
        String project=props.getProperty("project");
        String url=props.getProperty("url");
        String str=String.format(url, protocol,host,port,project,uri);
        URL link = new URL(str);
        HttpURLConnection connet = (HttpURLConnection) link.openConnection();
        connet.setRequestMethod("GET");
        connet.setRequestProperty("Charset", "UTF-8");
        connet.setRequestProperty("Content-Type", "application/json");
        connet.setConnectTimeout(15000);// 连接超时 单位毫秒
        connet.setReadTimeout(15000);// 读取超时 单位毫秒
        if(connet.getResponseCode() != 200){
            logger.debug("请求接口"+url+"成功");
            return ;
        }else{
            logger.debug("请求接口"+url+"失败");
        }
    }

    public static void main(String[] args) throws Exception {
        send("hahah");
    }
}
