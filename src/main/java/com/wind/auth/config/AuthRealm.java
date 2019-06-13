package com.wind.auth.config;

import com.wind.auth.model.*;
import com.wind.auth.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * AuthRealm
 *
 * @author qianchun
 * @date 2019/5/28
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 为用户授权
     * 
     * @param principals principals
     * @return 返回结果
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取前端输入的用户信息，封装为User对象
        User userweb = (User) principals.getPrimaryPrincipal();
        // 获取前端输入的用户名
        String username = userweb.getUsername();
        // 根据前端输入的用户名查询数据库中对应的记录
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }

        // 如果数据库中有该用户名对应的记录，就进行授权操作
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Long userId = user.getId();

        // addRoles
        Collection<String> roleValueList = new HashSet<>();
        Collection<String> permissionValueList = new HashSet<>();
        List<Role> roleList = userService.findRoleByUserId(userId);
        if (CollectionUtils.isNotEmpty(roleList)) {
            roleList.forEach(role -> {
                roleValueList.add(role.getName());
            });
        }
        info.addRoles(roleValueList);

        // addStringPermissions
        List<Permission> permissionList = userService.findPermissionByUserId(userId);
        if (CollectionUtils.isNotEmpty(permissionList)) {
            permissionList.forEach(permission -> {
                permissionValueList.add(permission.getValue());
            });
        }
        info.addStringPermissions(permissionValueList);
        return info;
    }

    /**
     * 认证登录
     * 
     * @param token token
     * @return 返回结果
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // token携带了用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        // 获取前端输入的用户名
        String userName = usernamePasswordToken.getUsername();

        // 根据用户名查询数据库中对应的记录
        User user = userService.findByUsername(userName);
        if(user == null) {
            throw new UnknownAccountException();
        }

        // 盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        // 封装用户信息，构建AuthenticationInfo对象并返回
        return new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
    }
}
