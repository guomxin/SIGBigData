package com.telchina.bigdata.mr.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCount {
    public static class TokenizerMapper extends Mapper<LongWritable, Text,Text,LongWritable> {

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] words = line.split(" ");
            for(String word:words) {
                context.write(new Text(word),new LongWritable(1));
            }

        }
    }

    public static class IntSumReducer extends Reducer<Text,LongWritable,Text,LongWritable> {

        protected void reduce(Text key, Iterable<LongWritable> values, Mapper.Context context) throws Exception {
            int sum = 0;
            for (LongWritable val : values) {
                sum += val.get();
            }
            context.write(key,new LongWritable(sum));
        }
    }

    public static void main(String []args) throws Exception{
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        //指定job所在的jar包
        job.setJarByClass(WordCount.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setReducerClass(IntSumReducer.class);
        //指定输出的key和value的数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);
        //指定要处理的数据的路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        //指定结果集保存的路径
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        //作业提交
        job.waitForCompletion(true);
    }
}
