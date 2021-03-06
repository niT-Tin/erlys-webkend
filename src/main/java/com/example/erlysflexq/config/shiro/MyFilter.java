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
//            //??????????????????
//            resp.setHeader("Access-Control-Allow-Credentials", "true");
//            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//
//            threadLocalToken.clear();
//            //????????????token?????????token????????????????????????401
//            String token = getRequestToken((HttpServletRequest) servletRequest);
//            if (StringUtils.isBlank(token)) {
//                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
//                resp.getWriter().print("???????????????");
//                return false;
//            }
//
//            try {
//                jwtUtil.verifyToken(token); //????????????????????????
//            } catch (TokenExpiredException e) {
//                //??????????????????????????????Redis?????????????????????????????????????????????????????????????????????????????????
//                if (redisTemplate.hasKey(token)) {
//                    redisTemplate.delete(token);//????????????
//                    int userId = jwtUtil.getUserId(token);
//                    token = jwtUtil.generateToken((long) userId);  //??????????????????
//                    //????????????????????????Redis???
//                    redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
//                    //???????????????????????????
//                    threadLocalToken.setToken(token);
//                } else {
//                    //??????Redis???????????????????????????????????????
//                    resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
//                    resp.getWriter().print("??????????????????");
//                    return false;
//                }
//
//            } catch (JWTDecodeException e) {
//                resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
//                resp.getWriter().print("???????????????");
//                return false;
//            }
//
//            boolean bool = executeLogin(servletRequest, servletResponse);
//            return bool;


        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????corsConfig??????
        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) servletRequest).getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        RqObject r = new RqObject();
        r.setMessage("????????????");
        httpServletResponse.getWriter().write(JSONObject.toJSON(r).toString());
        return false;

//        if(logger.isErrorEnabled()) {
//            logger.error("account need login for: {}",  ((HttpServletRequest)servletRequest).getServletPath());
//        }
//
//        // ??????????????????????????????json?????????????????????
//        servletResponse.getWriter().write("?????????");
//        servletResponse.getWriter().flush();
//        servletResponse.getWriter().close();
//        return false;
    }
}
