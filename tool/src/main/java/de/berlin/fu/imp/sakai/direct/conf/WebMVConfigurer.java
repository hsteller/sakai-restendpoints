package de.berlin.fu.imp.sakai.direct.conf;

import java.util.List;

import javax.servlet.Filter;
import javax.xml.transform.Source;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebMvc
@Slf4j
@ComponentScan(basePackages= {"de.berlin.fu.imp.sakai.direct"})
public class WebMVConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
		// final MappingJackson2HttpMessageConverter converter = new
		// MappingJackson2HttpMessageConverter();
		// converters.add(converter);

		log.info("configure Converters");
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setWriteAcceptCharset(false);
		messageConverters.add(new ByteArrayHttpMessageConverter());
		messageConverters.add(stringConverter);
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(new SourceHttpMessageConverter<Source>());
		messageConverters.add(new AllEncompassingFormHttpMessageConverter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/jsp/", ".jsp");
	}

	 @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        // registry.addViewController("/").setViewName("forward:/hello/world.html");
		 registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
	    }
	 
	 
	 @Bean
	 public Filter logFilter() {
	     CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
	     filter.setIncludeQueryString(false);
	     filter.setIncludePayload(false);
	     filter.setIncludeClientInfo(true);
	     return filter;
	 }

}
