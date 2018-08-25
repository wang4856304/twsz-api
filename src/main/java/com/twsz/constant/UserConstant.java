package com.twsz.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/20 13:55
 */
public class UserConstant {

    public static Map<String, String> USER_URL_MAP = new HashMap<>();
    static {
        USER_URL_MAP.put("register", "/user/register");
        USER_URL_MAP.put("login", "/user/login");
        USER_URL_MAP.put("loginOut", "/user/loginOut");
        USER_URL_MAP.put("userInfo", "/user/getUserInfo");
        USER_URL_MAP.put("changeUserPassword", "/user/changeUserPassword");
        USER_URL_MAP.put("resetUserPassword", "/user/resetUserPassword");
    }
}
