package app.security.config;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    private final JwtService jwtService;

    public FilterConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public FilterRegistrationBean<Filter> jwtFilterConfig() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilter(this.jwtService));
        filterRegistrationBean.addUrlPatterns("/api/user/*");
        return filterRegistrationBean;
    }
}
