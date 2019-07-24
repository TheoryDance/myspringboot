package com.theorydance.myspringboot.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsUtils {
	
	public final static String HOST1 = "master59";
	public final static String HOST2 = "slave113";
	public final static String HOST3 = "slave145";
	public final static int PORT = 9300;//http请求的端口是9200，客户端是9300
	
	/**
     *  初始化TransportClient对象， 这里只配置了单个节点，例如100.100.0.1:8090
     */
    public static TransportClient getClient() throws UnknownHostException {
    	/*
    	 * client.transport.sniff=true,客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
    	 * 这样做的好处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。
    	 */
        Settings settings = Settings.builder()
                //elasticsearch节点名称
                .put("cluster.name", "my-application")
//                .put("client.transport.sniff", true)
                .build();
        
        // 采用嗅探方式，不用配置所有的集群节点
//        InetAddress address = InetAddress.getByName(HOST1);
//        TransportClient client = new PreBuiltTransportClient(settings);
//        client.addTransportAddress(new TransportAddress(address, PORT));
        
        //集群连接
        @SuppressWarnings("resource")
		TransportClient client = new PreBuiltTransportClient(settings)
				.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST1), PORT))
				.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST2), PORT))
				.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST3), PORT));
        return client;
    }
    
}
