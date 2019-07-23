package com.theorydance.myspringboot.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class EsUtils {
	/**
     *  初始化TransportClient对象， 这里只配置了单个节点，例如100.100.0.1:8090
     */
    public static TransportClient getClient() throws UnknownHostException {
        String host = "master59";
        int port = 9300;
        Settings settings = Settings.builder()
                //elasticsearch节点名称
                .put("cluster.name", "my-application")
                .put("client.transport.sniff", true).build();
        
        InetAddress address = InetAddress.getByName(host);
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new TransportAddress(address, port));
        return client;
    }
    
}
