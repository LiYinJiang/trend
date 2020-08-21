package net.eryingzhang.trend.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

@Component
public class IpConfiguration implements ApplicationListener<WebServerInitializedEvent> {

	private int servicePort;

	public int getServicePort() {
		return servicePort;
	}

	@Override
	public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
		servicePort = webServerInitializedEvent.getWebServer().getPort();

	}

}
