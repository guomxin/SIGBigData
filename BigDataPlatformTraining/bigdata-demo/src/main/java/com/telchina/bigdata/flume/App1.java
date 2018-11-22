package com.telchina.bigdata.flume;

import java.util.Properties;

public class App1 {
    public static void main(String[] args) {

        MyRpcClientFacade client = new MyRpcClientFacade();
        try {
            /*Properties props = new Properties();
            props.put("client.type", "default_failover");
            props.put("hosts", "h1 h2 h3");

            String host1 = "10.10.70.46:44444";
            String host2 = "10.10.70.47:44444";
            String host3 = "10.10.70.48:44444";
            props.put("hosts.h1", host1);
            props.put("hosts.h2", host2);
            props.put("hosts.h3", host3);
            client.init(props);
            String sampleData = "Hello Flume app1 " + String.valueOf(System.currentTimeMillis());
            while (1 == 1) {
                client.sendDataToFlume(sampleData);
            }*/
            client.init("10.10.70.46", 44444);
            String sampleData = "Hello Flume app1 " + String.valueOf(System.currentTimeMillis());
            for (int i = 0; i < 10; i++) {
                client.sendDataToFlume(sampleData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.cleanUp();
        }
    }
}
