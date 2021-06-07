package com.example.erlysflexq.config.shiro;

import com.example.erlysflexq.pojo.Userinfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.teasoft.bee.erlys.gets.GetMyWish;
import org.teasoft.bee.osql.SuidRich;
import org.teasoft.honey.osql.core.BeeFactory;

import java.util.HashSet;
import java.util.Set;

@Component
public class MyRealm extends AuthorizingRealm {

//    @Autowired
    SuidRich suidRich = BeeFactory.getHoneyFactory().getSuidRich();
    {
        super.setName("customRealm");
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
        
        String account = (String) authenticationToken.getPrincipal();
        
        String password = getPasswordByAccount(account);

        SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(account, password, "customRealm");
        
        return authInfo;
    }

    private String getPasswordByAccount(String account) {
        Userinfo userinfo = GetMyWish.selectByAccount(new Userinfo(), account);
        if(userinfo == null)
            return null;
        return userinfo.getPassword();
    }
}
