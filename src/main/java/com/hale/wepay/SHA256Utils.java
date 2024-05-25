package com.hale.wepay;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class SHA256Utils {

    private static Logger logger = LogManager.getLogger(SHA256Utils.class);
    private static final String ALGORITHM = "HmacSHA256";


    /**
     * 生成签名（SHA256）
     *
     * @param data   待签名数据
     * @param key API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            String k = keyArray[i];
            if ("sign".equals(k)) {
                continue;
            }
            if (data.get(k) instanceof String) {
                // 参数值为空，则不参与签名
                if (data.get(k).trim().length() > 0) {
                    sb.append(k).append("=").append(data.get(k).trim());
                }
                if (i < keyArray.length - 1) {
                    sb.append("&");
                }
            }


        }
        System.out.println(String.format("\n签名字符串：%s \n", sb.toString().trim()));
        return sign(sb.toString(), key);
    }

    public static boolean valid(String message, String secret, String signature) {
        return signature != null && signature.equals(sign(message, secret));
    }

    public static String sign(String message, String secret) {
        try {

            Mac hmac = Mac.getInstance(ALGORITHM);
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            hmac.init(secret_key);
            byte[] bytes = hmac.doFinal(message.getBytes());
            logger.info("service sign is " + byteArrayToHexString(bytes));
            return byteArrayToHexString(bytes);
        } catch (Exception ex) {
            logger.error("签名错误：", ex);
        }
        return null;
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder hs = new StringBuilder();
        String tempStr;
        for (int index = 0; bytes != null && index < bytes.length; index++) {
            tempStr = Integer.toHexString(bytes[index] & 0XFF);
            if (tempStr.length() == 1)
                hs.append('0');
            hs.append(tempStr);
        }
        return hs.toString().toLowerCase();
    }

}
