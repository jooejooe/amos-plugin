package org.steps.entity;

import lombok.Data;

/**
 * @program: kettle-sdk-step-plugin
 * @description: ${description}
 * @author: Gou Ding Cheng
 * @create: 2019-09-17 10:37
 **/
@Data
public class HadoopBean{
    private String user;
    private String path;
    private String pathFrom;
    private String uri;
    private String output;
    private String privige;

}