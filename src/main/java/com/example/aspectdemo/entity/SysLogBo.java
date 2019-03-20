package com.example.aspectdemo.entity;

import lombok.Data;

/**
 * @Author zhoutf
 * @Date 2019/3/20 9:21
 * @Description
 */
@Data
public class SysLogBo {

    private String className;

    private String methodName;

    private  String params;

    private  Long exeuTime;

    private  String remark;

    private String createDate;
}
