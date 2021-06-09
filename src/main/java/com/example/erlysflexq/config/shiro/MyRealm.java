package com.example.erlysflexq.config.shiro;

import com.example.erlysflexq.pojo.Userinfo;
import com.example.erlysflexq.utils.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {


    @Autowired
    JwtUtil jwtUtil;

    @Value("${flexq.jwt.secret}")
    private String secret;

//    @Autowired
    SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
    {
        super.setName("customRealm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Token;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String account = (String) principalCollection.getPrimaryPrincipal();
        Set<String> roles = getRolesByAccount(account);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(roles);
        return info;
    }

    private Set<String> getRolesByAccount(String account) {
        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
        Set<String> roles = new HashSet<>();
        roles.add(userinfo.getRoles());
        return roles;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        
        String token = (String) authenticationToken.getPrincipal();

        Userinfo userinfo = suidRich.selectById(new Userinfo(), jwtUtil.getUserId(token));
        jwtUtil.verifyToken(token);

        if(userinfo == null){
            throw new LockedAccountException("账号被锁定");
        }
        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(userinfo, token, "customRealm");
//        authInfo.setCredentialsSalt(ByteSource.Util.bytes(secret));
        return authInfo;
    }

//    private String getPasswordByAccount(String account) {
//        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
//        if(userinfo == null)
//            return null;
//        return userinfo.getPassword();
//    }
}
