package com.wind.auth.controller;

import com.wind.auth.model.EMail;
import com.wind.auth.service.UserService;
import com.wind.common.ErrorCode;
import com.wind.dict.model.CodeItem;
import com.wind.utils.JsonResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bs")
public class BSController {
    private final static Logger logger = LoggerFactory.getLogger(BSController.class);

//    @Autowired
//    private UserService userService;

    /**
     * 列表
     *
     * @param pageNo 当前页
     * @param pageSize 每页大小
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@RequestParam("pageNo") String pageNo, String pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("list", data);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("列表, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 子集列表
     * @param parentCode 父级code
     * @param pageNo 当前页
     * @param pageSize 每页大小
     * @return`
     */
    @ResponseBody
    @RequestMapping(value = "/children", method = RequestMethod.GET)
    public Object list(@RequestParam("parentCode") String parentCode, @RequestParam("pageNo") String pageNo,
            String pageSize) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("list", data);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("子集列表, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(@RequestParam("id") Long id) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("codeItem", data);
            return JsonResponseUtil.ok(data);
        } catch (Exception e) {
            logger.error("详情, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public Object delete(@RequestParam("id") Long id) {
        try {
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("删除, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 新增
     * 
     * @param codeItem 待新增codeItem
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Object delete(CodeItem codeItem) {
        try {
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("新增, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 更新
     * 
     * @param codeItem 待更新codeItem
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public Object update(CodeItem codeItem) {
        try {
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("更新, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }

    /**
     * 启用/停用
     * 
     * @param id id
     * @param status 状态
     * @return 返回结果
     */
    @ResponseBody
    @RequestMapping(value = "/update/status", method = RequestMethod.GET)
    public Object update(@RequestParam("id") Long id, @RequestParam("status") Integer status) {
        try {
            return JsonResponseUtil.ok();
        } catch (Exception e) {
            logger.error("启用/停用, 异常", e);
            return JsonResponseUtil.fail(ErrorCode.SYS_ERROR);
        }
    }
}