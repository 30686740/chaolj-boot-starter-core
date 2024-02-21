package com.chaolj.core.bootUtils.bootConfig;

import com.chaolj.core.MyConst;
import com.chaolj.core.bootUtils.bootInterceptor.HttpTrackInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 控制器拦截配置
@Configuration
public class AppInterceptor implements WebMvcConfigurer {
    // 拦截优先级
    private final int order = -98;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Track
        registry.addInterceptor(new HttpTrackInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(MyConst.INTERCEPTOR_EXCLUDE)
                .order(this.order);
    }
}
