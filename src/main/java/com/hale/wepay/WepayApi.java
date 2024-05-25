package com.hale.wepay;


import com.alibaba.fastjson2.JSON;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Component
public class WepayApi {

//    @Autowired
//    OkHttpClientUtil okHttpClientUtil;

    private final String BASE_URL = "https://tgpay1.test-xyz.com/";

    private final String LOGIN_URL = BASE_URL + "api/base/login";

    private final String ORDER_COLLECTION_URL = BASE_URL + "api/ordercollection/createOrdercollection";

    private final String ORDER_URL = BASE_URL + "api/orderpayment/createOrderpayment";


    public Object login() {
        Map<String, String> param = new HashMap<>();
        param.put("key", "KDlyvCmI");
        param.put("username", "ppp");
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Cache-Control", "no-cache");
        return OkHttpClientUtil.doPostJson(LOGIN_URL, JSON.toJSONString(param), header);
//        return OkHttpClientUtil.doPost(LOGIN_URL, param, header);
    }


    public Object creatOrdercollection(String token) {
        Map<String, String> param = new HashMap<>();
        param.put("orderno", UUID.randomUUID().toString().replaceAll("-", ""));
        param.put("amount", "10.00");
        param.put("currency", "WP");
//        String sign = getSign(param, null, null);
        String sign = SHA256Utils.generateSignature(param, "KDlyvCmI");
        System.out.println(String.format("\n签名后的字符串：%s \n", sign));
        param.put("sign", sign);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("x-token", token);
        return OkHttpClientUtil.doPostJson(ORDER_COLLECTION_URL, JSON.toJSONString(param), header);
//        return OkHttpClientUtil.doPost(LOGIN_URL, param, header);
    }

    public Object creatOrder(String token) {
        Map<String, String> param = new HashMap<>();
        param.put("orderno", UUID.randomUUID().toString().replaceAll("-", ""));
        param.put("amount", "10.00");
        param.put("currency", "WP");
        param.put("code", "Wepacfefe59056b71b706466");
//        String sign = getSign(param, null, null);
        String sign = SHA256Utils.generateSignature(param, "KDlyvCmI");
        System.out.println(String.format("\n签名后的字符串：%s \n", sign));
        param.put("sign", sign);
        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("x-token", token);
        return OkHttpClientUtil.doPostJson(ORDER_URL, JSON.toJSONString(param), header);
//        return OkHttpClientUtil.doPost(LOGIN_URL, param, header);
    }
}
