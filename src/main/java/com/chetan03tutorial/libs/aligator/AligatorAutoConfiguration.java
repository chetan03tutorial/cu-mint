package com.chetan03tutorial.libs.aligator;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {"com.gapinc.stores.libs.aligator"})
public class AligatorAutoConfiguration {
}
