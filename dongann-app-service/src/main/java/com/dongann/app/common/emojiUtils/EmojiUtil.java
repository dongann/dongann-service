package com.dongann.app.common.emojiUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @FileName: EmojiUtil
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/22 上午11:38
 * @Version: v1.0
 * @description: 表情包转换工具类
 */
public class EmojiUtil {

    private static Logger log = LoggerFactory.getLogger(EmojiUtil.class);

    /**
     * @Description 将字符串中的emoji表情转换成可以在utf-8字符集数据库中保存的格式（表情占4个字节，需要utf8mb4字符集）
     * @param converEmoji 待转换字符串
     * @return 转换后字符串
     */
    public static String emojiConvert(String converEmoji) {
        String patternString = "([\\x{10000}-\\x{10ffff}\ud800-\udfff])";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(converEmoji);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, "[[" + URLEncoder.encode(matcher.group(1), "UTF-8") + "]]");
            } catch (Exception e) {
                log.error("emojiConvert error", e);
            }
        }
        matcher.appendTail(sb);
        log.debug("emojiConvert " + converEmoji + " to " + sb.toString() + ", len：" + sb.length());
        return sb.toString();
    }

    /**
     * @Description 还原utf8数据库中保存的含转换后emoji表情的字符串
     * @param recovEmoji 转换后的字符串
     * @return 转换前的字符串
     */
    public static String emojiRecovery(String recovEmoji) {
        String patternString = "\\[\\[(.*?)\\]\\]";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(recovEmoji);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(sb, URLDecoder.decode(matcher.group(1), "UTF-8"));
            } catch (Exception e) {
                log.error("emojiRecovery error", e);
            }
        }
        matcher.appendTail(sb);
        log.debug("emojiRecovery " + recovEmoji + " to " + sb.toString());
        return sb.toString();
    }
}
