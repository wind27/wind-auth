package com.wind.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.wind.auth.UserToken;
import com.wind.auth.constants.Constant;
import com.wind.auth.model.User;
import com.wind.auth.service.UserService;
import com.wind.auth.utils.SaltUtil;
import com.wind.common.ErrorCode;
import com.wind.passport.annotation.PwdDecrypt;
import com.wind.passport.util.RSACryptography;
import com.wind.passport.util.RequestUtil;
import com.wind.utils.IdCardUtil;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录等功能
 *
 * @author : qianchun 2018/12/30
 */
@Controller
@RequestMapping("/passport")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "encrypt", method = RequestMethod.GET)
    public String encrypt1(@RequestParam("password") String password) {
        try {
            Map<String, Object> data = new HashMap<>();
            byte[] encryptedBytes = this.encrypt(password);
            data.put("encrypt", encryptedBytes);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("登录,加密,异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "decrypt", method = RequestMethod.GET)
    public String decrypt1(@RequestParam("password") String password) {
        try {
            Map<String, Object> data = new HashMap<>();
            password = password.replaceAll(" ", "+");
            password = this.decrypt(password);
            data.put("decrypt", password);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("登录,解密,异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    private byte[] encrypt(String password) throws Exception {
        PublicKey publicKey = RSACryptography.getPublicKey(com.wind.passport.common.Constant.publicKeyIndex);
        return RSACryptography.encrypt(password.getBytes(), publicKey);
    }

    private String decrypt(String password) throws Exception {
        byte[] decodeBytes = Base64.getDecoder().decode(password.getBytes());
        PrivateKey privateKey = RSACryptography.getPrivateKey(com.wind.passport.common.Constant.privateKeyIndex);
        byte[] decryptedBytes = RSACryptography.decrypt(decodeBytes, privateKey);
        return new String(decryptedBytes, "UTF-8");
    }

    /**
     * 获取 pubsign
     *
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/pubsign", method = RequestMethod.GET)
    public String pubsign() {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("pubsign", Constant.RSA_PUBLIC_KEY);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("登录,获取pubsign,异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 密码登录
     *
     * @param username 登录名
     * @param password 密码
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String appPwdLogin(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("username") String username, @RequestParam("password") @PwdDecrypt String password) {
        try {
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                return JsonResponseUtil.fail(ErrorCode.PARAM_ERROR);
            }
            String accessToken = "";
            // accessToken 生成
            User user = userService.findByUsername(username);
            if (user == null) {
                logger.error("登录,失败,username={}", username);
                return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
            }
            setLoginCookie(response, user, accessToken);
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("登录,异常,username={}", username, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * APP 修改密码
     *
     * @param request request
     * @param response response
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String appLogout(HttpServletRequest request, HttpServletResponse response) {
        UserToken token = null;
        try {
            token = RequestUtil.getUidAndTokenMap(request);
            String salt = SaltUtil.generateSalt();

            // 设置token失效
            if (token != null) {
                logger.error("退出登录, 设置accessToken 失效, token={}", token);
            }

            // 删除cookie
            RequestUtil.setLoginCookie(response, "", "", "", 0);
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("退出登录, 异常, token={}", token, e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    // /**
    // * 修改密码
    // *
    // * @param inv inv
    // * @param oldpassword 原始密码
    // * @param newpassword 新密码
    // * @param confirmPassword 确认密码
    // * @param version 版本
    // * @return 返回结果
    // */
    // @FydLoginRequired
    // @Post("pwd/update")
    // @HttpFeatures(contentType = "json")
    // public String appPWDUpdate(Invocation inv, @PwdDecrypt @Param("oldpassword") String oldpassword,
    // @PwdDecrypt @Param("newpassword") String newpassword,
    // @PwdDecrypt @Param("confirmpassword") String confirmPassword, @Param("version") String version) {
    // long uid = 0;
    // try {
    // if (StringUtils.isEmpty(oldpassword) || StringUtils.isEmpty(newpassword)
    // || StringUtils.isEmpty(confirmPassword) || StringUtils.isEmpty(version)) {
    // return AppJsonResponseUtil.badResult(codeAPI, ErrorCode.PARAM_ERROR);
    // }
    // BaseUser user = passportHelper.getUserWithRequest(inv.getRequest());
    // if (user == null) {
    // return AppJsonResponseUtil.badResult(codeAPI, ErrorCode.PARAM_ERROR);
    // }
    // uid = user.getUid();
    // String sign = RequestUtil.signPassportParam(String.valueOf(uid), user.getUserName(), oldpassword,
    // newpassword, confirmPassword, version);
    // Map<String, Object> paramMap = new HashMap<>();
    // paramMap.put("uid", String.valueOf(uid));
    // paramMap.put("username", user.getUserName());
    // paramMap.put("oldpassword", oldpassword);
    // paramMap.put("newpassword", newpassword);
    // paramMap.put("confirmPassword", confirmPassword);
    // paramMap.put("sign", sign);
    // paramMap.put("version", version);
    // Map<String, Object> map = RequestUtil.getUidAndTokenMap(inv.getRequest());
    // paramMap.putAll(map);
    // String result = restTemplateService.post(Constant.APP_PWD_UPDATE_URL, null, paramMap);
    // if (result == null) {
    // logger.error("[fyd-app-web] 修改密码请求失败！");
    // return AppJsonResponseUtil.badResult(codeAPI, ErrorCode.SYS_ERROR);
    // }
    // logger.info("[fyd-app-web] 修改密码请求 param={}, result{}", JSONObject.toJSON(paramMap), result);
    // return "@" + result;
    // } catch (Exception e) {
    // logger.error("[fyd-app-web] 修改密码异常,uid={}, version={}", uid, version, e);
    // alarmService.mail(String.format("[fyd-app-web] 修改密码异常, uid=%s, version=%s", uid, version), e);
    // return AppJsonResponseUtil.badResult(codeAPI, ErrorCode.SYS_ERROR);
    // }
    // }

    // ------------------------------------------------------------------------------------------------------------------
    /**
     * 设置登录cookie
     *
     * @param response response
     * @param user 用户信息
     * @param accessToken 登录token
     */
    private void setLoginCookie(HttpServletResponse response, User user, String accessToken) throws Exception {
        String value = user.getId() + "::" + accessToken;
        String username = user.getUsername();
        String realname = user.getRealname();
        // 从passport获取用户实名信息
        if (StringUtils.isNotEmpty(user.getIdcard())) {
            Integer sex = IdCardUtil.getSex(user.getIdcard());
            realname = username.substring(0, 1) + (sex != null && sex == 0 ? "女士" : "先生");
        }
        realname = URLEncoder.encode(realname, "utf-8");
        RequestUtil.setLoginCookie(response, username, realname, value, Constant.MAXAGE);
    }

    public static void main(String[] args) throws Exception {
        LoginController controller = new LoginController();
        String password = "hello world !!!))**)";
        JSONObject result = JSONObject.parseObject(controller.encrypt1(password));
        byte[] encrypt = result.getJSONObject("data").getBytes("encrypt");
        String encryptPwd = new String(Base64.getEncoder().encode(encrypt), "UTF-8");
        System.out.println("加密:" + encryptPwd);

        result = JSONObject.parseObject(controller.decrypt1(encryptPwd));
        JSONObject decrypt = result.getJSONObject("data");
        System.out.println("解密:" + decrypt);
    }
}
