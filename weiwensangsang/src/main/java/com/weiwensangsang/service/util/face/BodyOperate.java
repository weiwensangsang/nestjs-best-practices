package com.weiwensangsang.service.util.face;

import java.util.HashMap;
import static com.weiwensangsang.config.Constants.FACEPPAPIKEY;
import static com.weiwensangsang.config.Constants.FACEPPAPISECRET;

/**
 * Created by Qi Wang on 2017/4/27.
 */

public class BodyOperate {

    private String apiKey = "";
    private String apiSecret = "";

    public BodyOperate(String apiKey, String apiSecret){
        this.apiKey = FACEPPAPIKEY;
        this.apiSecret = FACEPPAPISECRET;
    }


    /**
     * 人体检测
     * @param imageUrl 图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @param return_attributes 是否检测并返回根据行人特征判断出的年龄，服装颜色属性，需要将需要检测的属性组织成一个用逗号分隔的字符串
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response HumanBodyDetect(String imageUrl, byte[] fileByte, String image_base64, String return_attributes) throws Exception {
        String url = Key.HUMANBODY_DETECT;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        if(!HttpRequest.isEmpty(return_attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, String.valueOf(return_attributes));
        }
        if(!HttpRequest.isEmpty(imageUrl)){
            map.put(Key.KEY_FOR_IMAGE_URL, imageUrl);
        }
        if(!HttpRequest.isEmpty(image_base64)){
            map.put(Key.KEY_FOR_IMAGE_BASE64, image_base64);
        }
        if(fileByte != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, fileByte);
        }
        return HttpRequest.post(url, map, fileMap);
    }



    /**
     * 人像扣图
     * @param imageUrl 图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response HumanBodySegment(String imageUrl, byte[] fileByte, String image_base64) throws Exception {
        String url = Key.HUMANBODY_SEGMENT;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        if(!HttpRequest.isEmpty(imageUrl)){
            map.put(Key.KEY_FOR_IMAGE_URL, imageUrl);
        }
        if(!HttpRequest.isEmpty(image_base64)){
            map.put(Key.KEY_FOR_IMAGE_BASE64, image_base64);
        }
        if(fileByte != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, fileByte);
        }
        return HttpRequest.post(url, map, fileMap);
    }






}
