package net.eryingzhang.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {

	private int serverPort;

	public int getServerPort() {
		return serverPort;
	}

	@Override
	public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
		serverPort = webServerInitializedEvent.getWebServer().getPort();

	}

}
