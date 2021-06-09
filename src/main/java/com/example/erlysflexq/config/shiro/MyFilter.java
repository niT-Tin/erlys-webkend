package com.example.erlysflexq.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.erlysflexq.pojo.RqObject;
import com.example.erlysflexq.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class MyFilter extends AuthenticatingFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);


    @Autowired
    ThreadLocalToken threadLocalToken;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    RedisTemplate redisTemplate;

    @Value("${flexq.jwt.cache-expire}")
    private Integer cacheExpire;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = getRequestToken((HttpServletRequest) servletRequest);
        if(StringUtils.isEmpty(token)){
            return null;
        }
        return new Token(token);
    }

    private String getRequestToken(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = httpRequest.getParameter("token");
        }
        return token;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if(((HttpServletRequest) request).getHeader("token") != null){
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
//                responseError();
                return false;
            }
        }
        return true;
//        return req.getMethod().equals(RequestMethod.OPTIONS.name());
    }


    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String token = request1.getHeader("token");
        Token token1 = new Token(token);
        getSubject(request, response).login(token1);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

//            HttpServletRequest req = (HttpServletRequest) servletRequest;
//            HttpServletResponse resp = (HttpServletResponse) servletResponse;
//
//            resp.setHeader("Content-Type", "text/html;charset=UTF-8");
//            //允许跨域请求
//            resp.setHeader("Access-Control-Allow-Credentials", "true");
//            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//
//            threadLocalToken.clear();
//            //获取请求token，如果token不存在，直接返回401
//            String token = getRequestToken((HttpServletRequest) servletRequest);
//            if (StringUtils.isBlank(token)) {
//                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
//                resp.getWriter().print("无效的令牌");
//                return false;
//            }
//
//            try {
//                jwtUtil.verifyToken(token); //检查令牌是否过期
//            } catch (TokenExpiredException e) {
//                //客户端令牌过期，查询Redis中是否存在令牌，如果存在令牌就重新生成一个令牌给客户端
//                if (redisTemplate.hasKey(token)) {
//                    redisTemplate.delete(token);//删除令牌
//                    int userId = jwtUtil.getUserId(token);
//                    token = jwtUtil.generateToken((long) userId);  //生成新的令牌
//                    //把新的令牌保存到Redis中
//                    redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
//                    //把新令牌绑定到线程
//                    threadLocalToken.setToken(token);
//                } else {
//                    //如果Redis不存在令牌，让用户重新登录
//                    resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
//                    resp.getWriter().print("令牌已经过期");
//                    return false;
//                }
//
//            } catch (JWTDecodeException e) {
//                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
//                resp.getWriter().print("无效的令牌");
//                return false;
//            }
//
//            boolean bool = executeLogin(servletRequest, servletResponse);
//            return bool;


        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) servletRequest).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        RqObject r = new RqObject();
        r.setMessage("登陆错误");
        httpServletResponse.getWriter().write(JSONObject.toJSON(r).toString());
        return false;

//        if(logger.isErrorEnabled()) {
//            logger.error("account need login for: {}",  ((HttpServletRequest)servletRequest).getServletPath());
//        }
//
//        // 请求被拦截后直接返回json格式的响应数据
//        servletResponse.getWriter().write("未登录");
//        servletResponse.getWriter().flush();
//        servletResponse.getWriter().close();
//        return false;
    }
}
