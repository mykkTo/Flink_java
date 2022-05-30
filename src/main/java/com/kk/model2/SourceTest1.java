package com.kk.model2;


import com.kk.model2.pojo.Event;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

/*
 * @Description:    集合中读取数据
 * @Author:         阿K
 * @CreateDate:     2022/5/24 22:04
 * @Param:
 * @Return:
 **/
public class SourceTest1 {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment ( );
        // 设置并行度为 1 ，保证有序运行
        env.setParallelism (1);

        // 1.从文件中读取数据
        DataStreamSource<String> stream1 = env.readTextFile ("input/clicks.csv");

        // 2.从集合中读取数据
        ArrayList<Integer> nums = new ArrayList<> ( );
        nums.add (2);
        nums.add (5);
        DataStreamSource<Integer> numStream = env.fromCollection (nums);

        ArrayList<Event> events = new ArrayList<> ( );
        events.add (new Event ("Mary", "./home", 1000L));
        events.add (new Event ("Bob", "./cart", 2000L));
        DataStreamSource<Event> stream2 = env.fromCollection (events);


        // 3.从元素读取数据
        DataStreamSource<Event> stream3 = env.fromElements (
                new Event ("Mary", "./home", 1000L),
                new Event ("Bob", "./cart", 2000L)
        );

        // 从Socket文本流读取
        DataStreamSource<String> stream4 = env.socketTextStream ("localhost", 7777);

    //    stream1.print("1");
      //  numStream.print("nums");
     //   stream2.print("2");
      //  stream3.print("3");

        stream4.print ("4");

        env.execute ( );

    }

}
