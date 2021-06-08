package com.example.erlysflexq.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Component
@Scope("prototype")
public class MyFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        return null;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//       if(isLoginRequest(request, response))
//           return true;
//       else{
//           Subject subject = getSubject(request, response);
//           return subject.getPrincipal() != null;
//       }
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getMethod().equals(RequestMethod.OPTIONS.name());
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        if(logger.isErrorEnabled()) {
            logger.error("account need login for: {}",  ((HttpServletRequest)servletRequest).getServletPath());
        }

        // 请求被拦截后直接返回json格式的响应数据
        servletResponse.getWriter().write("未登录");
        servletResponse.getWriter().flush();
        servletResponse.getWriter().close();
        return false;
    }
}
