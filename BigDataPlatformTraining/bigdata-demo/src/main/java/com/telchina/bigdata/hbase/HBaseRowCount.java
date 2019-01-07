package com.telchina.bigdata.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;

public class HBaseRowCount {
    public static void main(String args[]) {
        rowCountByCoprocessor("university");
    }
    public static void rowCountByCoprocessor(String tablename){
        try {
            Configuration configuration = HBaseConfiguration.create();
            configuration.set("hbase.zookeeper.property.clientPort", "2181");
            configuration.set("hbase.zookeeper.quorum", "bdp43.bigdata,bdp44.bigdata,bdp45.bigdata");
            configuration.set("hbase.master", "bdp45.bigdata:16000");
            configuration.set("zookeeper.znode.parent", "/hbase-unsecure");
            Connection connection = ConnectionFactory.createConnection(configuration);
            Admin admin = connection.getAdmin();
            TableName name=TableName.valueOf(tablename);
            //先disable表，添加协处理器后再enable表
            admin.disableTable(name);
            HTableDescriptor descriptor = admin.getTableDescriptor(name);
            String coprocessorClass = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
            if (! descriptor.hasCoprocessor(coprocessorClass)) {
                descriptor.addCoprocessor(coprocessorClass);
            }
            admin.modifyTable(name, descriptor);
            admin.enableTable(name);
            Scan scan = new Scan();
            AggregationClient aggregationClient = new AggregationClient(configuration);

            System.out.println("RowCount: " + aggregationClient.rowCount(name, new LongColumnInterpreter(), scan));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
