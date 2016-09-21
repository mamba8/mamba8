package com.mamba.framework.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.util.StringUtils;

public class ApplicationMonitoring {
    @Bean
    public ManagementServerProperties managementServerProperties() {
        ManagementServerProperties msp = new ManagementServerProperties();
        // 配置文件可覆盖下面默认值
        msp.setContextPath("/monitoring");
        return msp;
    }

    @Bean
    public SecurityProperties securityProperties(ManagementServerProperties msp) {
        SecurityProperties sp = new SecurityProperties();
        // 配置文件可覆盖下面默认值
        sp.getUser().setName("mamba8");
        sp.getUser().setPassword("mamba8");
        sp.getBasic().setPath(msp.getContextPath() + "/**");
        return sp;
    }

    @Bean
    public ServletRegistrationBean druidServlet(ManagementServerProperties msp, @Value("${management.druid.urlMapping:}") String urlMapping) {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings(StringUtils.isEmpty(urlMapping) ? msp.getContextPath() + "/druid/*" : urlMapping);
        return reg;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(ManagementServerProperties msp, @Value("${management.druid.exclusions:}") String exclusions) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", StringUtils.isEmpty(exclusions) ? msp.getContextPath() + "/*" : exclusions);
        return filterRegistrationBean;
    }
}
