package com.github.freshchen.cn.common.util;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * @author freshchen
 * @since 2022/1/11
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogUtils {

    public static final Pattern BLANK_REMOVE_PATTERN = Pattern.compile(" + ");

    public static String trimWhiteAndBlankSpace(String str) {
        String trimAllWhitespace = StrUtil.removeAllLineBreaks(str);
        if (StringUtils.isBlank(str)) {
            return trimAllWhitespace;
        }
        return BLANK_REMOVE_PATTERN.matcher(trimAllWhitespace).replaceAll(" ");
    }
}
