package net.eryingzhang.trend;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.StrUtil;

@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class TradingBackTestServiceApplication {
	public static int DEFAULT_PORT = 8051;

	public static final int EUREKA_SERVER_PORT = 8761;

	public static void main(String[] args) {

		if (NetUtil.isUsableLocalPort(EUREKA_SERVER_PORT)) {
			System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", EUREKA_SERVER_PORT);
			System.exit(1);
		}

		int servicePort = 0;
		if (args != null && args.length != 0) {
			for (String string : args) {
				if (string.startsWith("port=")) {
					String port = StrUtil.subAfter(string, "port=", false);
					if (Convert.toInt(port) != null)
						servicePort = Convert.toInt(port);
				}
			}
		}
		if (servicePort == 0) {
			Future<Integer> future = ThreadUtil.execAsync(() -> {
				int p = 0;
				System.out.println(String.format("启动 模拟回测 微服务，设定端口。建议端口 %s, 若5s未设定将设定未 %s %n", DEFAULT_PORT, DEFAULT_PORT));
				Scanner sc = new Scanner(System.in);
				while (true) {
					if (sc.hasNext()) {
						String strPost = sc.next();
						if (Convert.toInt(strPost) != null) {
							p = Convert.toInt(strPost);
							break;
						} else {
							System.err.println("只能是数字");
							continue;
						}
					} else {
						p = DEFAULT_PORT;
						break;
					}

				}

				return p;
			});

			try {
				servicePort = future.get(5, TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				// TODO Auto-generated catch block
				servicePort = DEFAULT_PORT;
			}
		}

		if (!NetUtil.isUsableLocalPort(servicePort)) {
			System.err.printf("检查到端口%d 被占用，无法启动%n", servicePort);
			System.exit(1);
		} else
			System.out.println("启动 模拟回测 微服务 端口号: " + servicePort);
		new SpringApplicationBuilder(TradingBackTestServiceApplication.class).properties("server.port=" + servicePort)
				.run(args);
	}

}
