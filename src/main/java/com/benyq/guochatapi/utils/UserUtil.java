package com.benyq.guochatapi.utils;

/**
 * @author benyq
 * @time 2021/10/24
 * @e-mail 1520063035@qq.com
 * @note 用户的工具类
 */
public class UserUtil {

    private static final String salt = "^&dsj78";

    /**
     * 加盐md5
     * @param pwd 已经md5
     * @return
     */
    public static String saltEncryptionPwd(String pwd) {
        return MD5Util.stringToMD5(salt + pwd);
    }

}
