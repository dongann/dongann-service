package com.dongann.app.common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @FileName: GetRequestContent
 * @Author: <a href="dongann@aliyun.com">dongchang'an</a>.
 * @CreateTime: 2018/6/1 下午2:26
 * @Version: v1.0
 * @description:
 */
public class GetRequestContent {
    private static Logger log = LoggerFactory.getLogger(GetRequestContent.class);

    public static String processRequest(HttpServletRequest request) {
        String res = "";
        try {
            request.setCharacterEncoding("UTF-8");
            int size = request.getContentLength();
            InputStream is = request.getInputStream();
            byte[] reqBodyBytes = readBytes(is, size);
            res = new String(reqBodyBytes, "UTF-8");
        } catch (Exception e) {
           log.error("GetRequestContent==>processRequest:"+e);
        }
        return res;
    }

    public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                log.error("GetRequestContent==>readBytes:"+e);
            }
        }
        return new byte[]{};
    }
}
