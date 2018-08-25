package com.twsz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.twsz.constant.UserConstant;
import com.twsz.entity.Response;
import com.twsz.entity.dto.user.UserChangePwdDto;
import com.twsz.entity.dto.user.UserDto;
import com.twsz.entity.dto.user.UserLoginDto;
import com.twsz.entity.dto.user.UserResetPwdDto;
import com.twsz.enums.ResultEnum;
import com.twsz.service.BaseService;
import com.twsz.service.UserService;
import com.twsz.utils.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * }
 *
 * @author Administrator
 * @Description:
 * @date 2018/8/17 11:48
 */

@Service
public class UserServiceImpl extends BaseService implements UserService {

    private static Log log = LogFactory.getLog(UserServiceImpl.class);

    @Value("${userUrl}")
    private String userUrl;

    private static final String REGISTER = "register";
    private static final String LOGIN = "login";
    private static final String LOGIN_OUT = "loginOut";
    private static final String USER_INFO = "userInfo";
    private static final String CHANGE_PWD = "changeUserPassword";
    private static final String RESET_PWD = "resetUserPassword";


    @Override
    public Response registerUser(UserDto userDto) {
        String jsonData = JSONObject.toJSONString(userDto);
        String url = getReqUrl(userUrl, UserConstant.USER_URL_MAP.get(REGISTER));
        String result = HttpClientUtil.httpPostRequest(url, jsonData);
        log.info(url + "==" + "response:" + result);
        Response response = JSONObject.parseObject(result, Response.class);
        return response;
    }

    @Override
    public Response login(UserLoginDto userLoginDto) {
        String jsonData = JSONObject.toJSONString(userLoginDto);
        String url = getReqUrl(userUrl, UserConstant.USER_URL_MAP.get(LOGIN));
        String result = HttpClientUtil.httpPostRequest(url, jsonData);
        log.info(url + "==" + "response:" + result);
        Response response = JSONObject.parseObject(result, Response.class);
        if (ResultEnum.SUCCESS.getCode().equals(response.getCode())) {
            //TODO
        }
        return response;
    }

    @Override
    public Response loginOut(String token) {
        String url = getReqUrl(userUrl, UserConstant.USER_URL_MAP.get(LOGIN_OUT));
        url = url + "?token=" + token;
        String result = HttpClientUtil.httpGetRequest(url);
        log.info(url + "==" + "response:" + result);
        Response response = JSONObject.parseObject(result, Response.class);
        return response;
    }


    @Override
    public Response getUserInfo(String token) {
        String url = getReqUrl(userUrl, UserConstant.USER_URL_MAP.get(USER_INFO));
        url = url + "?token=" + token;
        String result = HttpClientUtil.httpGetRequest(url);
        log.info(url + "==" + "response:" + result);
        Response response = JSONObject.parseObject(result, Response.class);
        return response;
    }

    @Override
    public Response changeUserPassword(UserChangePwdDto userChangePwdDto) {
        String jsonData = JSONObject.toJSONString(userChangePwdDto);
        String url = getReqUrl(userUrl, UserConstant.USER_URL_MAP.get(CHANGE_PWD));
        String result = HttpClientUtil.httpPostRequest(url, jsonData);
        log.info(url + "==" + "response:" + result);
        Response response = JSONObject.parseObject(result, Response.class);
        return response;
    }

    @Override
    public Response resetUserPassword(UserResetPwdDto userResetPwdDto) {
        String jsonData = JSONObject.toJSONString(userResetPwdDto);
        String url = getReqUrl(userUrl, UserConstant.USER_URL_MAP.get(RESET_PWD));
        String result = HttpClientUtil.httpPostRequest(url, jsonData);
        log.info(url + "==" + "response:" + result);
        Response response = JSONObject.parseObject(result, Response.class);
        return response;
    }

    @Override
    public String getUserIdByToken(String token) {
        Response response = getUserInfo(token);
        Object data = response.getData();
        if (data != null) {
            String jsonStr = JSONObject.toJSONString(response.getData());
            JSONObject json = JSONObject.parseObject(jsonStr);
            return json.getString("userId");
        }
        return null;
    }

    private String getReqUrl(String url, String serverName) {
        if (!serverName.startsWith("/")) {
            serverName = "/" + serverName;
        }
        String resultUrl = url + serverName;
        return resultUrl;
    }

}
