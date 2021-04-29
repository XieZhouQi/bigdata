package com.technology.flink;

import com.technology.flink.constants.SqlConstant;
import com.technology.flink.function.FlinkTest1TableFunction;
import com.technology.flink.function.FlinkTestTableFunction;
import com.technology.flink.mq.RocketMqSource;
import com.technology.flink.util.Constants;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.StreamTableEnvironment;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/4/22
 * @version:
 */
public class MqDataSourceTest {
    static long  delay = 5100L;
    static int windowSize = 20;
    public static void main(String[] args) throws Exception {
        // 获取执行环境配置信息
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, bsSettings);


       // env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        //env.setParallelism(1);
        //EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        //注册rocketMQSource,用来接收任务信息和图片特征提取信息
        //KeyedStream<Tuple3<String, String, Integer>, Tuple3<String, String, Integer>> text = env.addSource(new RocketMqSource()).keyBy(0);

        DataStream<Tuple3<String, String, Integer>> text = env.addSource(new RocketMqSource()).keyBy(0);

        //注册表
        tableEnv.registerDataStream("mqDataTable", text,
                "nob, nameb, ageb");
        tableEnv.registerFunction("flinkTableFunctionUDF", new FlinkTest1TableFunction());

        Table stageTable = tableEnv.sqlQuery(SqlConstant.testSql1());

        //Table grabDetectionTable = tableEnv.sqlQuery(SqlConstant.testSql("mqDataTable"));
        //DataStream<Row> stream = tableEnv.toAppendStream(grabDetectionTable, Row.class).keyBy(row->row.getField(0)).timeWindow(Time.seconds(20)).maxBy(2);

//        DataStream<Tuple4> stream =  tableEnv.toAppendStream(stageTable, Types.TUPLE(Types.STRING, Types.STRING, Types.INT, Types.STRING));
//        SingleOutputStreamOperator<Tuple4> keyByStream = stream.keyBy(0,1)
//                .timeWindow(Time.seconds(20)).maxBy(2).returns(Types.TUPLE(Types.STRING, Types.STRING, Types.INT, Types.STRING));

//        tableEnv.registerDataStream("keyByTable", keyByStream, "nob, nameb, ageb");

        //tableEnv.registerDataStream("keyByTable", keyByStream);

        //Table keyByTable = tableEnv.sqlQuery("SELECT nob, nameb, ageb FROM  keyByTable");
        //Table keyByTable = tableEnv.sqlQuery("SELECT * FROM  keyByTable");

        //DataStream<Row> result =  tableEnv.toAppendStream(keyByTable, Row.class);

        DataStream<Row> result =  tableEnv.toAppendStream(stageTable, Row.class);
//        stream.keyBy(new KeySelector<Row, String>() {
//            @Override
//            public String getKey(Row value) throws Exception {
//               return value.getField(0) +  value.getField(1).toString();
//                //return value.getField(0).toString();
//            }
//        }).timeWindow(Time.seconds(20)).maxBy(2).returns(Row.class);

        //stream.keyBy(row => row.).timeWindow(Time.seconds(20)).maxBy(2).returns(Row.class);



//                .process(new ProcessWindowFunction<Row, Object, String, TimeWindow>() {
//            @Override
//            public void process(String s, Context context, Iterable<Row> elements, Collector<Object> out) throws Exception {
//                System.out.println("====ss==:" + s +" ===window start" + context.window().getStart() + " ===window end" + context.window().getEnd() );
//            }
//        });

//        KeyedStream<Tuple3<String,String,Integer>, String> ks =text.keyBy(new KeySelector<Tuple3<String,String,Integer>, String>() {
//            @Override
//            public String getKey(Tuple3<String,String,Integer> value) throws Exception {
//                return value.f0 + value.f1;
//            }
//        });

//        ks.timeWindow(Time.seconds(20)).apply(new WindowFunction<Tuple3<String,String,Integer>, String, String, TimeWindow>() {
//            @Override
//            public void apply(String integer, TimeWindow window, Iterable<Tuple3<String,String,Integer>> input, Collector<String> out) throws Exception {
//                StringBuffer stringBuffer = new StringBuffer();
//                input.forEach(t -> {
//                    stringBuffer.append(t.toString()).append("  ");
//                });
//                System.out.println("窗口开始："+window.getStart() + " 窗口结束："+window.getEnd());
//                System.out.println(stringBuffer.toString());
//                System.out.println("===========================end=================:" + System.currentTimeMillis());
//            }
//        });

         //DataStream<Row> result = ks.timeWindow(Time.seconds(30)).max(3);
        result.print();
        env.execute();
    }
}
