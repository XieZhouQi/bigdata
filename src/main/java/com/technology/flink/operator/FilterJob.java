package com.technology.flink.operator;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @description: filter是进行筛选
 * @author: xzq
 * @date: 2021/2/19
 * @version:
 */
public class FilterJob {

    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("127.0.0.1", 9000);

        // filter操作，筛选符合条件的(字符中包含1的)
        DataStream<String> result = text.filter(line->line.trim().contains("1"));

        //4.打印输出sink
        result.print();
        //5.开始执行
        env.execute();
    }

}
