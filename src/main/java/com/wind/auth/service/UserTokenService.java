package com.wind.auth.service;

import com.wind.auth.dao.UserTokenExDao;
import com.wind.auth.model.User;
import com.wind.auth.model.UserToken;
import com.wind.utils.IPUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * UserTokenService
 *
 * @author qianchun 2019/1/10
 **/
@Service
public class UserTokenService {

    private static final int JWT_EXPIRE_TIME = 7200000;

    @Autowired
    private UserTokenExDao userTokenExDao;

    /**
     * 创建新token
     *
     * @param username 用户名
     * @return 返回结果
     */
    private String createNewToken(String username) {
        // 获取当前时间
        Date now = new Date(System.currentTimeMillis());
        // 过期时间
        Date expiration = new Date(now.getTime() + JWT_EXPIRE_TIME);
        return Jwts.builder().setSubject(username)
                // .claim(YAuthConstants.Y_AUTH_ROLES, userDbInfo.getRoles())
                .setIssuedAt(now).setIssuer("Online YAuth Builder").setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "Wind2019.1.0.0").compact();
    }

    /**
     * 新增
     * 
     * @param userToken userToken 实体
     * @return 返回结果
     */
    private boolean save(UserToken userToken) {
        return userToken != null && userToken.getId() == null && userTokenExDao.save(userToken) > 0;
    }

    /**
     * 获取最新的userToken
     * 
     * @param username 用户名
     * @return 返回结果
     */
    public UserToken getLastestByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        UserToken userToken = userTokenExDao.getLastestByUsername(username);
        if (userToken != null && (userToken.getCreateTime().getTime() + JWT_EXPIRE_TIME) > System.currentTimeMillis()) {
            return userToken;
        }
        return null;
    }

    /**
     * 新增
     *
     * @param user 用户
     * @return 返回结果
     */
    public UserToken save(HttpServletRequest request, User user) {
        if (StringUtils.isEmpty(user.getUsername()) || user.getId() == null) {
            return null;
        }
        UserToken userToken = new UserToken();
        String token = createNewToken(user.getUsername());
        userToken.setToken(token);
        userToken.setUserId(user.getId());
        userToken.setUsername(user.getUsername());
        userToken.setCreateTime(new Date());
        userToken.setClientIp(IPUtil.getIP(request));
        boolean flag = this.save(userToken);
        if (flag) {
            return userToken;
        } else {
            return null;
        }
    }
}
