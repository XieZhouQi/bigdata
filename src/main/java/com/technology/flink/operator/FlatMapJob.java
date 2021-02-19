package com.technology.flink.operator;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @description: flatmap可以理解为将元素摊平，每个元素可以变为0个、1个、或者多个元素
 * @author: xzq
 * @date: 2021/2/19
 * @version:
 */
public class FlatMapJob {


    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("127.0.0.1", 9000);

        //3.flatMap操作，对每一行字符串进行分割
        DataStream<String> result = text.flatMap((String s, Collector<String> collector) -> {
            // split("\\W+") 使用非数字字母切分字符串
            for (String str : s.split("\\W+")) {
                collector.collect(str);
            }
        })
        //这个地方要注意，在flatMap这种参数里有泛型算子中。
        //如果用lambda表达式，必须将参数的类型显式地定义出来。
        //并且要有returns，指定返回的类型
        //详情可以参考Flink官方文档：https://ci.apache.org/projects/flink/flink-docs-release-1.6/dev/java_lambdas.html
        .returns(Types.STRING);

        //4.打印输出sink
        result.print();
        //5.开始执行
        env.execute();
    }

}
