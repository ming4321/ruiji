package com.ming.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.ming.reggie.common.BaseContext;
import com.ming.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取本次请求的uri
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{}",requestURI);
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //2判断本次请求是否需要被处理
        boolean check = checkPath(urls,requestURI);
        //3 如果不需要处理直接放行
        if(check){
            log.info("本次请求不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //4-1.判断登录状态，如果已经登录，放行
        if(request.getSession().getAttribute("employee") != null){
            log.info("已经登录，放行",requestURI);

           Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }
        //4-2.判断登录状态，如果已经登录，放行
        if(request.getSession().getAttribute("user") != null){
            log.info("已经登录，放行",requestURI);

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }
        //5.如果未登录，返回登录页面，通过输出流的方式向客户端页面响应数据、
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean checkPath(String[] urls,String requestURI){
        for(String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
