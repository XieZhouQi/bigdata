package com.technology.flink.mq;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/4/22
 * @version:
 */
public class MqData {

    private String no;

    private String name;

    private Integer age;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MqData(String no, String name, Integer age) {
        this.no = no;
        this.name = name;
        this.age = age;
    }
}
