package com.theorydance.myspringboot.datahandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.theorydance.myspringboot.utils.EsUtils;

@Component
public class P1upgradeLogHandler {
	
	@Value("${logsbyproduce.dir}")
	private String logsDir;

	public void uploadLogsToES() throws Exception{
		// pattern=%h,%l,%u,%t,%T,"%r",%s,%b,%{Referer}i,"%{User-Agent}i",%{X-Requested-With}i,%{passport}c
    	/*
    	 * %h 远程主机名
    	 * %l 总是返回'-'
    	 * %u 远程用户身份验证
    	 * %t 日期和时间
    	 * %T 处理请求的时间，单位秒
    	 * %r 第一行的请求信息
    	 * %s 响应的HTTP状态代码
    	 * %b 发送的字节数，不含http头， 如果为'-'表示没发送字节
    	 */
		File dir = new File(logsDir);
		if(!dir.isDirectory()) {
			return;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
		  try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = null;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
			Pattern p = Pattern.compile("(.*?),(.*?),(.*?),\\[(.*?)\\],(.*?),\"(.*?)\",(.*?),(.*?),(.*?),\"(.*?)\",(.*?),(.*?)");
			int lineNum = 0;
			TransportClient client = EsUtils.getClient();
			while((line=br.readLine())!=null) {
				lineNum ++;
				try {
					// line.matches("%h,%l,%u,%t,%T,\"%r\",%s,%b,%{Referer}i,\"%{User-Agent}i\",%{X-Requested-With}i,%{passport}c");
					Matcher m = p.matcher(line);
					boolean flag = m.matches();
					if(flag) {
						Map<String,Object> map = new HashMap<>();
						map.put("flag", flag);
						map.put("host", m.group(1));
						map.put("unkown", m.group(2));
						map.put("identify", m.group(3));
						map.put("time", sdf.parse(m.group(4)));
						map.put("cast", Double.parseDouble(m.group(5)));
						map.put("request", m.group(6));
						map.put("status", Integer.parseInt(m.group(7)));
						String bytes = m.group(8).trim();
						if(bytes.equals("-")) {
							map.put("bytes", 0);
						}else {
							map.put("bytes", Integer.parseInt(bytes));
						}
						map.put("Referer", m.group(9));
						map.put("User-Agent", m.group(10));
						map.put("X-Requested-With", m.group(11));
						map.put("passport", m.group(12));
						IndexResponse response = client
								.prepareIndex("logsbyproduce", "p1upgrade")
								.setSource(map).get();
						System.out.println(response);
					}else {
						System.out.println("lineNum="+lineNum+",is not parse");
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			br.close();
			client.close();
		  }catch (Exception e) {
			e.printStackTrace();
		  }
		}
	}
	
}
