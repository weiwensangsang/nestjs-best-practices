package com.weiwensangsang.service.util.face;

import java.util.HashMap;

import static com.weiwensangsang.config.Constants.FACEPPAPIKEY;
import static com.weiwensangsang.config.Constants.FACEPPAPISECRET;

/**
 *  使用国际版请忽视
 *  please ignore if you are use international version
 *
 * Created by Qi Wang on 2016/12/13.
 */
public class ImageOperate {

    public final static String IMAGE_RE = "https://api-cn.faceplusplus.com/imagepp/beta/detectsceneandobject";
    public final static String TEXT_RE = "https://api-cn.faceplusplus.com/imagepp/v1/recognizetext";

    private String apiKey = FACEPPAPIKEY;
    private String apiSecret = FACEPPAPISECRET;

    public ImageOperate(String apiKey, String apiSecret){
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }


    /**
     * 图片分析，识别图片场景和图片主体。
     * @param imageUrl 身份证图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地身份证图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 身份证图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response imageRecognition(String imageUrl, byte[] fileByte, String image_base64) throws Exception {
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
        return HttpRequest.post(IMAGE_RE, map, fileMap);
    }

    /**
     * 找出图片中出现的文字信息。
     * @param imageUrl 身份证图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地身份证图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 身份证图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response textRecognition(String imageUrl, byte[] fileByte, String image_base64) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        if(!HttpRequest.isEmpty(imageUrl)){
            map.put(Key.KEY_FOR_IMAGE_URL, imageUrl);
        }
        if(fileByte != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, fileByte);
        }
        if(!HttpRequest.isEmpty(image_base64)){
            map.put(Key.KEY_FOR_IMAGE_BASE64, image_base64);
        }
        return HttpRequest.post(TEXT_RE, map, fileMap);
    }

}
