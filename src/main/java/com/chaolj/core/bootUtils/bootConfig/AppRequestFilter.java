package com.chaolj.core.bootUtils.bootConfig;

import com.chaolj.core.bootUtils.bootObject.AppRequestWrapper;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "appRequestFilter", urlPatterns = {"/*"})
public class AppRequestFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof MultipartHttpServletRequest) {
            chain.doFilter(request, response);
            return;
        }

        if (request instanceof HttpServletRequest) {
            var requestWrapper = new AppRequestWrapper((HttpServletRequest) request);
            chain.doFilter(requestWrapper, response);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
