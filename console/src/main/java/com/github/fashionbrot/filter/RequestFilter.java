package com.github.fashionbrot.filter;

import com.github.fashionbrot.tool.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "uuid")
public class RequestFilter implements Filter {

    public final static String UUID = "UUID";
    public final static String REQUEST_HEADER_UUID_KEY = "X-Request-ID";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String uuid = req.getHeader(REQUEST_HEADER_UUID_KEY);
        if(StringUtils.isEmpty(uuid)){
            uuid = UuidUtil.getUuid();
        }
        MDC.put(UUID, uuid);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
