package com.wuhunyu.config;

import com.wuhunyu.utils.AssertUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换配置类
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2021-02-08 9:33
 */
@Component
public class DataConvertConfig implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        // 如果时间字符串为空
        if (source == null || "".equals(source)) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = null;
        try {
            parseDate = dateFormat.parse(source);
        } catch (ParseException e) {
            AssertUtil.isTrue(true, "时间状态异常");
        }
        return parseDate;
    }

}
