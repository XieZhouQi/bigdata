package com.technology.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.dag.Transformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

/**
 * @description: 接受socket 端口数据进行统计 nc（https://blog.csdn.net/qq_21383435/article/details/106803232）
 * @author: xzq
 * @date: 2021/2/18
 * @version:
 */
public class SumJob {

    public static void main(String[] args) throws Exception {
        // set up the streaming execution environment
        // 设置streaming运行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, bsSettings);

        // 连接socket获取输入的数据
        DataStream<String> text = env.socketTextStream("127.0.0.1", 9000);

        // split("\\W+") 使用非数字字母切分字符串
        DataStream<Tuple2<String, Integer>> dataStream = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                String[] tokens = s.toLowerCase().split("\\W+");
                collector.collect(new Tuple2<String, Integer>(tokens[0], Integer.parseInt(tokens[1])));
//                for(String token : tokens) {
//                    if(token.length() > 0) {
//                        collector.collect(new Tuple2<String, Integer>(token, 1));
//                    }
//                }
            }
        }).keyBy(0).timeWindow(Time.seconds(15)).sum(1);

        dataStream.print();
        Transformation<Tuple2<String, Integer>> tr = dataStream.getTransformation();

        //注册nc数据源
        tableEnv.registerDataStream("ncSourceStream", dataStream,
                "sourceId,scount");

        Table dataTable = tableEnv.sqlQuery("select ncSourceStream.sourceId, ncSourceStream.scount from ncSourceStream ");

        DataStream<Row> countData = tableEnv.toAppendStream(dataTable, Row.class);

        System.out.println("开始打印统计数据");
       // countData.print();
        System.out.println("结束打印统计数据");
        // execute program
        env.execute("Flink Streaming Java API Skeleton");

    }

}
