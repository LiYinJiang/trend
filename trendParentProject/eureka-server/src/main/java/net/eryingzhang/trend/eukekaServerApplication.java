package net.eryingzhang.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import cn.hutool.core.util.NetUtil;

@SpringBootApplication
@EnableEurekaServer
public class eukekaServerApplication {
	public static final int DEFAULT_PORT = 8761;

	public static void main(String[] args) {
		if (!NetUtil.isUsableLocalPort(DEFAULT_PORT)) {
			System.err.print(DEFAULT_PORT + " already bind!");
			System.exit(1);
		}
		new SpringApplicationBuilder(eukekaServerApplication.class).properties("server.port=" + DEFAULT_PORT).run(args);
	
		

	}
}
