package com.twsz.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.twsz.entity.Response;
import com.twsz.service.BaseService;
import com.twsz.service.SmsSendService;
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
 * @date 2018/8/20 13:27
 */
@Service
public class SmsSendServiceImpl extends BaseService implements SmsSendService {

    private static Log log = LogFactory.getLog(SmsSendServiceImpl.class);

    @Value("${smsSendUrl}")
    private String smsSendUrl;

    @Override
    public Response sendVerifyCode(String mobile) {
        String url = smsSendUrl + "?" + mobile;
        String result = HttpClientUtil.httpGetRequest(url);
        Response response = JSONObject.parseObject(result, Response.class);
        return response;
    }
}
