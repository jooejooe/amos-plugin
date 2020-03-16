package org.steps.entity;

import lombok.Data;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-18 11:18
 **/
@Data
public class HiveBean {
    private String uri;
    private String jdbc;
    private String user;
    private String password;
    private String sql;
    private String output;
}