package org.steps.entity;

import lombok.Data;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-18 13:20
 **/
@Data
public class HbaseBean {
    private String zoopeckerHost;
    private String zoopeckerPort;
    private String master;
    private String table;
    private String familly;
    private String output;
}