package com.geowarin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.isdc.wro.extensions.http.SpringWroFilter;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.manager.factory.ConfigurableWroManagerFactory;
import ro.isdc.wro.model.resource.processor.factory.ConfigurableProcessorsFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.HashMap;
import java.util.Properties;

/**
 * Date: 11/02/2014
 * Time: 21:58
 *
 * @author Geoffroy Warin (http://geowarin.github.io)
 */
@Configuration
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(Application.class)
                .showBanner(false)
                .run(args);
    }

    @Bean
    public FilterRegistrationBean wro4j() {
        WroFilter filter = new WroFilter();

        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        ConfigurableWroManagerFactory managerFactory = new ConfigurableWroManagerFactory();

        ConfigurableProcessorsFactory processorsFactory = new ConfigurableProcessorsFactory();
        Properties properties = new Properties();
        properties.put("preProcessors", "cssUrlRewriting,cssImport,semicolonAppender,sassCss,typeScript");
        properties.put("postProcessors", "cssVariables,cssMinJawr,jsMin");
        processorsFactory.setProperties(properties);

        managerFactory.setProcessorsFactory(processorsFactory);
        filter.setWroManagerFactory(managerFactory);

        registrationBean.addUrlPatterns("/wro/*");
        return registrationBean;
    }
}
