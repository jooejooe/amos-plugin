package org.steps.entity;

import lombok.Data;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-18 15:30
 **/
@Data
public class SqoopBean {
    private String argument;
    private String method;
    private String uri;
    private String yarn;
    private String output;

}