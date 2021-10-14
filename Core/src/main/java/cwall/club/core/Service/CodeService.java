package cwall.club.core.Service;

import cwall.club.common.Util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CodeService {

    public void sendCode(String phone,String code){
        String host = "http://smsbanling.market.alicloudapi.com";
        String path = "/smsapis";
        String method = "GET";
        String appcode = "57ba6530113845d2a64e5d834b67d59e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("msg", "你的验证码是"+code);
        querys.put("sign", "消息通");


        try {

            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
