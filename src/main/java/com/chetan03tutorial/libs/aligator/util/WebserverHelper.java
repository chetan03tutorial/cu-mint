package com.chetan03tutorial.libs.aligator.util;

import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WebserverHelper {

    private static int port;
    private static String contextPath;

    public static int getPort() {
        return port;
    }

    public static String getContextPath(){
       return contextPath;
    }

    @EventListener
    public void onWebServerStart(ServletWebServerInitializedEvent event) {
        port = event.getWebServer().getPort();
        contextPath = event.getApplicationContext().getServletContext().getContextPath();
    }
}
