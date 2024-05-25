package com.hale.wepay;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WepayApplicationTests {

    @Autowired
    WepayApi wepayApi;

    @Test
    void contextLoads() {
    }


    @Test
    void login() {
        String result = (String) wepayApi.login();
        System.out.println(result);
    }

    @Test
    void creatOrdercollection() {
        String result = (String) wepayApi.login();
        JSONObject obj = JSON.parseObject(result);
        JSONObject tokenObj = obj.getJSONObject("data");
        String token = tokenObj.getString("Token");
        String orderResult = (String) wepayApi.creatOrdercollection(token);
        System.out.println(orderResult);
    }

    @Test
    void creatOrder() {
        String result = (String) wepayApi.login();
        JSONObject obj = JSON.parseObject(result);
        JSONObject tokenObj = obj.getJSONObject("data");
        String token = tokenObj.getString("Token");
        String orderResult = (String) wepayApi.creatOrder(token);
        System.out.println(orderResult);
    }

}
