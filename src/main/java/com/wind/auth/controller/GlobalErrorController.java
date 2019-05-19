package com.wind.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wind.auth.handler.ErrorInfo;
import com.wind.auth.handler.ErrorInfoBuilder;
import com.wind.common.ErrorCode;
import com.wind.utils.JsonResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * GlobalErrorController
 *
 * @author qianchun
 * @date 2019/4/24
 **/
@Controller
@RequestMapping("${server.error.path:/error}")
public class GlobalErrorController implements ErrorController {
    private final static Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);

    @Autowired
    private ErrorInfoBuilder errorInfoBuilder;// 错误信息的构建工具.

    // private final static String DEFAULT_ERROR_VIEW = "error";// 错误信息页
    // /**
    // * 若预期返回类型为text/html,则返回错误信息页(View).
    // */
    // @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    // public ModelAndView errorHtml(HttpServletRequest request) {
    // return new ModelAndView(DEFAULT_ERROR_VIEW, "errorInfo", errorInfoBuilder.getErrorInfo(request));
    // }

    /**
     * 返回详细的错误信息(JSON).
     */
    @RequestMapping
    @ResponseBody
    public String error(HttpServletRequest request) {
        String params = JSON.toJSONString(request.getParameterMap());
        ErrorInfo info = errorInfoBuilder.getErrorInfo(request);
        logger.error("操作请求异常: error={}, params={}", JSONObject.toJSONString(info), params);
        return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
    }

    @Override
    public String getErrorPath() {// 获取映射路径
        return errorInfoBuilder.getErrorProperties().getPath();
    }
}
