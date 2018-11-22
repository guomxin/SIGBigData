package com.telchina.bigdata.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;


public class HDFSClient {
    public static void main(String[] args) {
        HDFSClient hdfsClient = new HDFSClient();
        FileSystem fs = hdfsClient.connect();
        //数据写入
        //hdfsClient.write(fs);
        //数据读取
        //byte b[] = hdfsClient.read(fs);
        //System.out.println(new String(b));
        //数据追加
        //hdfsClient.append(fs);
        //文件删除
        hdfsClient.delete(fs);
    }
    /*
     * 创建文件系统连接
     */
    public FileSystem connect() {
        try {
            Configuration conf = new Configuration();
            conf.setBoolean("dfs.support.append", true);
            FileSystem fs = FileSystem.get(new URI("hdfs://bdp43.bigdata:8020"), conf);
            return  fs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /*
     * 数据读取
     */
    public byte[] read(FileSystem fs) {
        try {
            FSDataInputStream fin = fs.open(new Path("/tmp/hdfs/helloworld.txt"));
            byte b[] = new byte[20];
            int resLen = fin.read(0,b,0,10);
            if(resLen > 0 ) {
                return b;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
    /*
     * 数据写入
     */
    public void write(FileSystem fs) {
        try {
            FSDataOutputStream fout = fs.create(new Path("/tmp/hdfs/helloworld.txt"));
            fout.write("This is test \r\n".getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 删除文件
     */
    public void delete(FileSystem fs) {
        try {
            if(fs.exists(new Path("/tmp/hdfs/helloworld.txt"))) {
                fs.delete(new Path("/tmp/hdfs/helloworld.txt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*
     * 追加数据
     */
    public void append(FileSystem fs) {
        try {
            FSDataOutputStream fout = fs.append(new Path("/tmp/hdfs/helloworld.txt"));
            fout.write("new row\r\n".getBytes());
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
