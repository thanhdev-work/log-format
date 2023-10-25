package vn.com.demologservice.filter;


import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.demologservice.util.UUIDUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//register app filer
@Component
public class AppFilter extends OncePerRequestFilter {
    public static final String REQUEST_ID_HEADER = "Request-Id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestId = request.getHeader("Request-Id") == null ? UUIDUtil.genRequestId() : request.getHeader("Request-Id");
        MDC.put("requestId", requestId);
//        custom response header here
//        response.addHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, PATCH, HEAD, OPTIONS");
//        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
//        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//        response.addIntHeader("Access-Control-Max-Age", 10);
        response.addHeader(REQUEST_ID_HEADER, requestId);
        filterChain.doFilter(request, response);
    }
}
