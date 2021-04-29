package com.technology.flink.execption;

import java.io.Serializable;

/**
 * 自定义：业务返回异常类
 * 
 * @author loaf 2018年7月10日14
 */
public class BussinessException extends RuntimeException implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 异常描述码
     */
    private Integer num;
    /**
     * 异常名称
     */
    private String code;
    /**
     * 请求id
     */
    private String desc;

    public BussinessException() {

    }

    public BussinessException(ResultIdMsg resultIdMsg) {

        this.num = resultIdMsg.getNum();
        this.code = resultIdMsg.getCode();
        this.desc = resultIdMsg.getDesc();

    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
