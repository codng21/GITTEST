package com.geowarin;

import java.util.Properties;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ro.isdc.wro.config.jmx.ConfigConstants;
import ro.isdc.wro.http.ConfigurableWroFilter;

@Configuration
@EnableConfigurationProperties(Wro4jConfiguration.class)
public class Wro4jConfiguration {
	
	@Bean
	ConfigurableWroFilter wroFilter(){
		ConfigurableWroFilter wroFilter = new ConfigurableWroFilter();
		wroFilter.setProperties(wro4jProperties());
		//WroManagerFactory wroManagerFactory
		//wroFilter.setWroManagerFactory(wroManagerFactory);		
		return wroFilter;
	}
     
	@Bean
	Properties wro4jProperties() {
		Properties properties = new Properties();
		properties.put(ConfigConstants.debug, true);
		properties.put(ConfigConstants.cacheGzippedContent, true);
		properties.put(ConfigConstants.disableCache, false);
		properties.put(ConfigConstants.resourceWatcherUpdatePeriod, 1);
		properties.put("preProcessors", "cssUrlRewriting,cssImport,semicolonAppender,sassCss,typeScript");
        properties.put("postProcessors", "cssVariables,cssMinJawr,jsMin");
		return properties;
	}
	
	@Bean
	FilterRegistrationBean wro4jFilterRegister(ConfigurableWroFilter wroFilter )
	{
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean(wroFilter);
		filterRegBean.addUrlPatterns("/wro/*");
		return filterRegBean;
	}
	
	
	
	
	
	
	
}
