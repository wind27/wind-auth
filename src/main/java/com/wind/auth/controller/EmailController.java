package com.wind.auth.controller;

import com.wind.auth.model.EMail;
import com.wind.auth.model.Permission;
import com.wind.auth.model.User;
import com.wind.auth.service.UserService;
import com.wind.common.ErrorCode;
import com.wind.common.Page;
import com.wind.utils.JsonResponseUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/email")
public class EmailController {
    private final static Logger logger = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private UserService userService;

    /**
     * 发送邮件 tName 邮件模板名称
     * 
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public Object list(@RequestParam("tName") String tName) {
        try {
            EMail email = new EMail();

            String title;
            String content;
            String contentType;
            Map<String, Object> params = new HashMap<>();
            List<String> recivers = new ArrayList<>();



            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("发送邮件, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }
}