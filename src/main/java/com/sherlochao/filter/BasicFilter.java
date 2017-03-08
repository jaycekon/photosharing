package com.sherlochao.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Sherlock_chao on 2016/11/28.
 */
public class BasicFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse rep = (HttpServletResponse) response;
        //指定允许其他域名访问
        rep.addHeader("Access-Control-Allow-Origin", "*");
        //响应类型
        rep.addHeader("Access-Control-Allow-Methods", "POST, OPTIONS, GET");
        //响应头设置
        //rep.addHeader("Access-Control-Allow-Headers", "POWERED-BY-FANTONG");
        rep.addHeader("Access-Control-Max-Age", "36000");
        rep.addHeader("Access-Control-Allow-Headers", "POWERED-BY-FANTONG, Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, Content-Language, Cache-Control, X-E4M-With");
        rep.addHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
        // 需要过滤的代码
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
