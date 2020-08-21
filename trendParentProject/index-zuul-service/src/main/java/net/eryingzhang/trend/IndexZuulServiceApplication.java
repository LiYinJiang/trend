package net.eryingzhang.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import cn.hutool.core.util.NetUtil;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
public class IndexZuulServiceApplication {
	
	public static int DEFAULT_PORT = 8031;

	public static final int EUREKA_SERVER_PORT = 8761;

	public static void main(String[] args) {

		if (NetUtil.isUsableLocalPort(EUREKA_SERVER_PORT)) {
			System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", EUREKA_SERVER_PORT);
			System.exit(1);
		}
		new SpringApplicationBuilder(IndexZuulServiceApplication.class).properties("server.port="+ DEFAULT_PORT).run(args);
	}
}
