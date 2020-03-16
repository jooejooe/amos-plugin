package org.steps.entity;

import lombok.Data;

@Data
public class KylinMetaBean {
    /**
     * kylin主机名
     */
    private String lhost;
    /**
     * Kylin端口
     */
    private String lport;
    /**
     * Kylin project
     */
    private String lproject;
    /**
     * Kylin username
     */
    private String lusername;
    /**
     * Kylin 密码
     */
    private String lpassword;
    /**
     * sql
     */
    private String lSql;

}
