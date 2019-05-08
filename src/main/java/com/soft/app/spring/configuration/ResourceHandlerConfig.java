package com.soft.app.spring.configuration;

import com.soft.app.constant.ApplicationConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.FixedVersionStrategy;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
@EnableWebMvc
public class ResourceHandlerConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // TODO Auto-generated method stub
        VersionResourceResolver versionResourceResolver = new VersionResourceResolver()
                .addVersionStrategy(new FixedVersionStrategy(ApplicationConstant.APPLICATION_VERSION), "/**");


        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/")
                .addResourceLocations("classpath:/META-INF/web-resources/")
                .setCachePeriod(60 * 60 * 24 * 365) /* one year */
                .resourceChain(true)
                .addResolver(versionResourceResolver);

    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}