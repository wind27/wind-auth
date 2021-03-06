package com.wind.auth.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * RequestWrapper
 *
 * @author qianchun
 * @date 2019/5/9
 **/
public class RequestWrapper extends HttpServletRequestWrapper {
    private final static Logger logger = LoggerFactory.getLogger(RequestWrapper.class);

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        value = HtmlUtils.htmlEscape(value);
         value = this.cleanXSS(value);
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] oldParams = super.getParameterValues(name);
        String[] values = null;
        if (oldParams == null || oldParams.length <= 0) {
            return null;
        } else {
            values = new String[oldParams.length];
            for (int i = 0; i < oldParams.length; i++) {
                values[i] = HtmlUtils.htmlEscape(oldParams[i]);
                 values[i] = this.cleanXSS(values[i]);
            }
        }
        return values;
    }

    /**
     * XSS
     * 
     * @param value 参数
     * @return 返回结果
     */
    private String cleanXSS(String value) {
        // You'll need to remove the spaces from the html entities below
        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
        value = value.replaceAll("'", "& #39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("script", "");
        value = value.replaceAll("[*]", "[" + "*]");
        value = value.replaceAll("[+]", "[" + "+]");
        value = value.replaceAll("[?]", "[" + "?]");

        // replace sql 这里可以自由发挥
        String[] values = value.split(" ");

        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|"
                + "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
                + "table|from|grant|use|group_concat|column_name|"
                + "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
                + "chr|mid|master|truncate|char|declare|or|;|-|--|,|like|//|/|%|#";

        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if (values[j].equalsIgnoreCase(badStrs[i])) {
                    values[j] = "forbid";
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (i == values.length - 1) {
                sb.append(values[i]);
            } else {
                sb.append(values[i] + " ");
            }
        }
        value = sb.toString();
        return value;
    }
}
