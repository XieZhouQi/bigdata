package com.technology.flink;

import com.technology.flink.dto.UserBehaviorDto;
import lombok.val;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.scala.DataStream;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.java.StreamTableEnvironment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/2/10
 * @version:
 */
public class FlinkMain {
    /***
     * 用户
     */
    private static final String[] userIdArr = {"Leo","Peter","Chao","Athena"};

    /***
     * 商品
     */
    private static final String[] itemIdArr = {"apple","banner","tomato", "cucumber"};

    /***
     * 行为
     */
    private static final String[] behaviorArr = {"pv","buy","cart", "fav"};

    public static void main(String[] args) {

        // flink的流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //EnvironmentSettings bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build();
        //StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, bsSettings);

        // 设定time类型为eventTime
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 并发为1
        env.setParallelism(1);

        // 开启checkpoint，时间间隔为毫秒
        env.enableCheckpointing(5000L);

        // 初始化测试模拟数据
        List<UserBehaviorDto> userBehaviorDtoList = userBehaviorDtoListMock();

        // env.addSource(userBehaviorDtoList)


//        SingleOutputStreamOperator<UserBehaviorDto> localhost = env.socketTextStream("localhost", 8888).map(new MapFunction<String, UserBehaviorDto>() {
//            @Override
//            public UserBehaviorDto map(String s) throws Exception {
//                String[] splits = s.split("\\W+");
//
//                SortDowComplete sortDowComplete = new SortDowComplete(splits[0], Integer.parseInt(splits[1]), splits[2], splits[3], splits[4]);
//                return sortDowComplete;
//            }
//        });

        //DataStream<UserBehaviorDto> stream = env.fromCollection(userBehaviorDtoList);

    }

    /***
     * @Description 模拟数据
     * @Param
     * @Return java.util.List<com.technology.flink.dto.UserBehaviorDto>
     * @Author xzq
     * @Date 2021/2/10
     */
    private static List<UserBehaviorDto> userBehaviorDtoListMock(){
        List<UserBehaviorDto> userBehaviorDtoList = new ArrayList<>(10000);
        Random r = new Random();
        for (int i=0; i<10000; i++ ){
            userBehaviorDtoList.add(UserBehaviorDto.builder()
                    .userId(userIdArr[r.nextInt(3) + 1])
                    .itemId(itemIdArr[r.nextInt(3) + 1])
                    .categoryId(String.valueOf(r.nextInt(3) + 1))
                    .behavior(behaviorArr[r.nextInt(3) + 1])
                    .timestamp(String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"))))
                    .build());
        }
        return userBehaviorDtoList;
    }

    /***
     * @Description 获取有效数据
     * @Param
     * @Return org.apache.flink.streaming.api.scala.DataStream<com.technology.flink.dto.UserBehaviorDto>
     * @Author xzq
     * @Date 2021/2/10
     */
    public static DataStream<UserBehaviorDto> getAvailableData(DataStream<String> accessLog){
        return null;
    }
}
