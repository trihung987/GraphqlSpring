package me.truong.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Bean(name="localeResolver")
	public LocaleResolver getLocaleResolver() {
		CookieLocaleResolver resolver=new CookieLocaleResolver();
		resolver.setCookieDomain("iotstar.vn");
		resolver.setDefaultLocale(Locale.ENGLISH);
		return resolver;	
	}
	@Bean(name="messageSource")
	public MessageSource getMessageSource()
	{
		ReloadableResourceBundleMessageSource messageResource=new ReloadableResourceBundleMessageSource();
		messageResource.setBasename("classpath:i18n/messages");
		messageResource.setDefaultEncoding("UTF-8");
		return messageResource;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		LocaleChangeInterceptor localeInterceptors=new LocaleChangeInterceptor();
		localeInterceptors.setParamName("language");
		registry.addInterceptor(localeInterceptors).addPathPatterns("/**");
	}
}
