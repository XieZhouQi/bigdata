package com.technology.flink.execption;

/**
 * 
 * @author
 * @date 2019/06/28
 */
public class ResultIdMsg {
    private Integer num;
    private String code;
    private String desc;

    /**
     * 按照模块定义CodeMsg 通用异常
     */
    public static final ResultIdMsg SUCCESS = new ResultIdMsg(0, "SUCCESS", "成功");
    public static final ResultIdMsg FAIL = new ResultIdMsg(1, "FAIL", "失败");
    public static final ResultIdMsg EXCEPTION = new ResultIdMsg(2, "EXCEPTION", "异常");

    public ResultIdMsg(int num, String code, String desc) {
        this.num = num;
        this.code = code;
        this.desc = desc;
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

    @Override
    public String toString() {
        return "CodeMsg [num=" + num + ",code=" + code + ", desc=" + desc + "]";
    }
}
