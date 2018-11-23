package com.telchina.bigdata.mr.flowsum;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

public class FlowBean implements Writable{

    private long upFlow;
    private long dFlow;
    private long sumFlow;

    //反序列化时，需要反射调用空参构造函数，所以要显示定义一个
    public FlowBean(){}

    public FlowBean(long upFlow, long dFlow) {
        this.upFlow = upFlow;
        this.dFlow = dFlow;
        this.sumFlow = upFlow + dFlow;
    }


    public long getUpFlow() {
        return upFlow;
    }
    public void setUpFlow(long upFlow) {
        this.upFlow = upFlow;
    }
    public long getdFlow() {
        return dFlow;
    }
    public void setdFlow(long dFlow) {
        this.dFlow = dFlow;
    }


    public long getSumFlow() {
        return sumFlow;
    }


    public void setSumFlow(long sumFlow) {
        this.sumFlow = sumFlow;
    }


    /**
     * 序列化方法
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(dFlow);
        out.writeLong(sumFlow);

    }


    /**
     * 反序列化方法
     * 注意：反序列化的顺序跟序列化的顺序完全一致
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readLong();
        dFlow = in.readLong();
        sumFlow = in.readLong();
    }

    @Override
    public String toString() {

        return upFlow + "\t" + dFlow + "\t" + sumFlow;
    }

}

