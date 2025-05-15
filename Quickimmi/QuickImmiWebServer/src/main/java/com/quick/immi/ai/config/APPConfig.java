/* (C) 2024 */
package com.quick.immi.ai.config;

import com.quick.immi.ai.interceptor.CaseVerifierInterceptor;
import com.quick.immi.ai.interceptor.LoginAuthenticationInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class APPConfig extends WebMvcConfigurationSupport {

  @Autowired private LoginAuthenticationInterceptor loginAuthenticationInterceptor;
  @Autowired private CaseVerifierInterceptor caseVerifierInterceptor;

  @Autowired private RestTemplateBuilder builder;

  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginAuthenticationInterceptor).addPathPatterns("/api/" + "/**");
    registry.addInterceptor(caseVerifierInterceptor).addPathPatterns("/api/" + "/**");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowedMethods("*");
  }

  private CorsConfiguration addCorsConfig() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    List<String> list = new ArrayList<>();
    list.add("*");
    corsConfiguration.setAllowedOrigins(list);
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    return corsConfiguration;
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", addCorsConfig());
    return new CorsFilter(source);
  }

  @Bean
  public RestTemplate restTemplate() {
    return builder.build();
  }
}
