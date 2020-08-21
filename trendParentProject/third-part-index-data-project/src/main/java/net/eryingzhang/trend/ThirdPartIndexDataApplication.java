package net.eryingzhang.trend;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import ch.qos.logback.core.pattern.ConverterUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.StrUtil;

@SpringBootApplication
@EnableEurekaClient
public class ThirdPartIndexDataApplication {

	public static int PORT = 8090;
	public static final int EUREKA_SERVER_PORT = 8761;

	public static void main(String[] args) {

		if (NetUtil.isUsableLocalPort(EUREKA_SERVER_PORT)) {
			System.err.printf("检查到端口%d 未启用，判断 eureka 服务器没有启动，本服务无法使用，故退出%n", EUREKA_SERVER_PORT);
			System.exit(1);
		}

		if (args != null && args.length != 0) {
			for (String string : args) {
				if (string.startsWith("port=")) {
					String port = StrUtil.subAfter(string, "port=", false);
					if (Convert.toInt(port) != null)
						PORT = Convert.toInt(port);
				}
			}
		}
		if (!NetUtil.isUsableLocalPort(PORT)) {
			System.err.printf("检查到端口%d 被占用，无法启动%n", PORT);
			System.exit(1);
		}
		new SpringApplicationBuilder(ThirdPartIndexDataApplication.class).properties("server.port=" + PORT).run(args);

	}

}
