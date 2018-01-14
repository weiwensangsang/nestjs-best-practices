package com.weiwensangsang.service.util.face;

import java.util.HashMap;
import static com.weiwensangsang.config.Constants.FACEPPAPIKEY;
import static com.weiwensangsang.config.Constants.FACEPPAPISECRET;

/**
 *  使用国际版请忽视这个类
 *  please ignore if you are use international version
 *
 * Created by Qi Wang on 2016/12/13.
 */
public class CardOperate {

    private String apiKey = "";
    private String apiSecret = "";

    public CardOperate(String apiKey, String apiSecret){
        this.apiKey = FACEPPAPIKEY;
        this.apiSecret = FACEPPAPISECRET;
    }


    /**
     * 身份证检测
     * @param imageUrl 身份证图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地身份证图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 身份证图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @param legality 返回身份证照片合法性检查结果，值只取“0”或“1”。“1”：返回； “0”：不返回
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response ocrIDcard(String imageUrl, byte[] fileByte, String image_base64, int legality) throws Exception {
        String url = Key.WEB_OCR + Key.SPLIT + Key.OCR_CARD;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_LEGALITY, String.valueOf(legality));
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
     * 机动车驾驶证检测
     * @param imageUrl 身份证图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地身份证图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 身份证图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response ocrDriverLicense(String imageUrl, byte[] fileByte, String image_base64) throws Exception {
        String url = Key.WEB_OCR + Key.SPLIT + Key.OCR_DRIVER_LICENSE;
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
        return HttpRequest.post(url, map, fileMap);
    }

    /**
     * 机动车行驶证检测
     * @param imageUrl 身份证图片的网络地址，和fileByte，image_base64三选一上传
     * @param fileByte 本地身份证图片的二进制数据，和imageUrl，image_base64三选一上传
     * @param image_base64 身份证图片的base64格式数据，和fileByte，imageUrl三选一上传
     * @return  返回结果，Response实例
     * @throws Exception
     */
    public Response ocrVehicleLicense(String imageUrl, byte[] fileByte, String image_base64) throws Exception {
        String url = Key.WEB_OCR + Key.SPLIT + Key.OCR_VEHICLE_LICENSE;
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
        return HttpRequest.post(url, map, fileMap);
    }


}
