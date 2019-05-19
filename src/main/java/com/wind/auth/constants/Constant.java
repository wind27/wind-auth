package com.wind.auth.constants;

import org.springframework.stereotype.Service;

/**
 * Redis Key值管理
 *
 * @author qianchun 2018/9/4
 **/
@Service
public class Constant {



    /**
     * cookie: 后端登录校验
     */
    public static final String AUTH_COOKIE_WEBVIEW_KEY = "auth_verify";

    /**
     * cookie: 登录账号
     */
    public static String SYD_COOKIE_LOGIN_NAME_KEY = "auth_login_name";

    /**
     * cookie: 实名
     */
    public static String SYD_COOKIE_NAME_KEY = "auth_name";

    /**
     * cookie 有效期
     */
    public static int MAXAGE = 1209600;

    /**
     * 域名
     */
    public static String DOMAIN = ".wind.com";

    /**
     * 获取图片验证码有效时间长度 (ms)
     */
    public static final int IMAGE_VALID_TIME = 300000;

    /**
     * kaptcha 图片宽度
     */
    public static final String KAPTCHA_IMAGE_WIDTH = "kaptcha.image.width";

    /**
     * kaptcha 图片高度
     */
    public static final String KAPTCHA_IMAGE_HEIGHT = "kaptcha.image.height";

    /**
     * 验证码的存储key
     * 找回密码注册通用
     */
    public final static String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

    /**
     * 验证码的存储时间
     * 找回密码注册通用
     */
    public final static String KAPTCHA_SESSION_DATE = "KAPTCHA_SESSION_DATE";

    /**
     * 请求访问次数限制
     */
    public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEfas4kYTV9TQTgAYspkm2ICh+O7B0NO15DwSJngXKFKl32AtUf1izWnW00A5KJlc1mz3WP1FAWmXcKMhdSITnKWb39Y3lDN/rzn6VEh5gIgtR7Rer0EnLvRdUl5DjxWC55mfwFSZIDgXpT1Zyqq618XNjoPb5ADZuQs+o00YdowIDAQAB";
    public static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMR9qziRhNX1NBOABiymSbYgKH47sHQ07XkPBImeBcoUqXfYC1R/WLNadbTQDkomVzWbPdY/UUBaZdwoyF1IhOcpZvf1jeUM3+vOfpUSHmAiC1HtF6vQScu9F1SXkOPFYLnmZ/AVJkgOBelPVnKqrrXxc2Og9vkANm5Cz6jTRh2jAgMBAAECgYAIfOE7lVWmpGv2d65MmXZPrr3xDgUGUA7+Dr8EbtYTL6dObP+4S7P6M2soP2COpW9Y+nbGXX/WBtGlnAFjvjE3c6dlJce7LfHvKWFRSHKypyeyGheGAuz24+0L3afrYBF/UeVzGa3FCitsIs2AjrW8Fh7otk9i0MstvemlQzyzqQJBAPUZDVvufOpb05mY/2u1Mf20capj02GY8nHdA/iwcKMN0tNXeWNE5eX0Qvd5axZ8gyamaKZ4fI3R1rqrpTVmjGcCQQDNOyAeBPXdJdwLQcK0P3vsPiqFSnz4O6vowhC3IXbXu5UclHzsN8RdvbScQl28rl+oQgIxX+kIrw5BNeckhd9lAkBqbp2RfUe7UXLasUjBUebKgBAX6M+DAzLM5SfzV1MiSz9wzPyGcgXPTLnD03MAScN+BidNmuajgyrW1vCf9s8jAkEAo1MpVRqHe24UtpSn2YAoI7K5bggAeiAqx/ohCUxFYpvyp6L345jh2eQN5sjeBjkTATk+u4JjVrjRdE3sW+F6DQJBAJeyIUOcINbbDI+ZFwrfEXl4WFwFMIOdEswyFaq6aqXKTCrmjp6Y9a9McLUxF/jCRVvuoeqeRGKtWbqq+fsyuHQ=";
}
