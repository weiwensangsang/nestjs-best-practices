package com.weiwensangsang.service.util.face;

import java.util.HashMap;
import static com.weiwensangsang.config.Constants.FACEPPAPIKEY;
import static com.weiwensangsang.config.Constants.FACEPPAPISECRET;

/**
 * 这个类里的所有方法都是网络请求，所以请在异步线程中调用
 * All the function in class is HTTP request, so please call in an asynchronous thread
 */
public class CommonOperate {

    private String apiKey = FACEPPAPIKEY;
    private String apiSecret = FACEPPAPISECRET;
    private String webUrl;

    /**
     *
     * @param apiKey
     * @param apiSecret
     * @param isInternationalVersion 是否是使用国际版
     *                               is use international version
     */
    public CommonOperate(boolean isInternationalVersion){
        if(isInternationalVersion){
            webUrl = Key.WEB_BASE_INTERNATIONAL;
        }else{
            webUrl = Key.WEB_BASE;
        }
    }


    /**
     * 这是一个网络请求的方法，传入不同的参数和API接口，可以请求不同的API
     * this is a HTTP request function, you can call all API through it
     * @param url API接口
     *            API url
     * @param map 字符串参数
     *            parameter of string
     * @param fileByte 文件参数
     *                 parameter of file
     * @return
     * @throws Exception
     */
    public static Response postTo(String url, HashMap<String, String> map, HashMap<String, byte[]> fileByte) throws Exception {
        return HttpRequest.post(url, map, fileByte);
    }


    /**
     * 调用者提供图片URL，进行人脸检测。
     * detect api through network image url
     * @param imageUrl 图片链接
     *                 image url
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     *                 Whether or return 83 key points of facial features and contour，1：return，0：not return
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     *                   detect face attributes: gender,age,smiling,glass,headpose,facequality,blur
     * @return
     * @throws Exception
     */
    public Response detectUrl(String imageUrl, int landmark, String attributes) throws Exception {
        String url = webUrl + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_IMAGE_URL, imageUrl);
        map.put(Key.KEY_FOR_RETURN_LANDMARK, String.valueOf(landmark));
        if(!HttpRequest.isEmpty(attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, attributes);
        }
        return HttpRequest.post(url, map, null);
    }

    /**
     * 调用者提供图片文件，进行人脸检测。
     * detect api through native image finle
     * @param fileByte 二进制数组
     *                 image binary array
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     *                 Whether or return 83 key points of facial features and contour，1：return，0：not return
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     *                   detect face attributes: gender,age,smiling,glass,headpose,facequality,blur
     * @return
     * @throws Exception
     */
    public Response detectByte(byte[] fileByte, int landmark, String attributes) throws Exception {
        String url = webUrl + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        fileMap.put(Key.KEY_FOR_IMAGE_FILE, fileByte);
        if(landmark != 0){
            map.put(Key.KEY_FOR_RETURN_LANDMARK, String.valueOf(landmark));
        }
        if(!HttpRequest.isEmpty(attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, attributes);
        }
        return HttpRequest.post(url, map, fileMap);
    }

    /**
     * 调用者提供图片文件，进行人脸检测。
     * detect api through native image finle
     * @param base64 Base64数据
     *                 image data for base64
     * @param landmark 是否返回人脸的关键点，1：返回，0：不返回
     *                 Whether or return 83 key points of facial features and contour，1：return，0：not return
     * @param attributes 检测人脸的属性 gender,age,smiling,glass,headpose,facequality,blur
     *                   detect face attributes: gender,age,smiling,glass,headpose,facequality,blur
     * @return
     * @throws Exception
     */
    public Response detectBase64(String base64, int landmark, String attributes) throws Exception {
        String url = webUrl + Key.SPLIT + Key.DETECT;
        HashMap<String, String> map = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_IMAGE_BASE64, base64);
        if(landmark != 0){
            map.put(Key.KEY_FOR_RETURN_LANDMARK, String.valueOf(landmark));
        }
        if(!HttpRequest.isEmpty(attributes)){
            map.put(Key.KEY_FOR_RETURN_ATTRIBUTES, attributes);
        }
        return HttpRequest.post(url, map, null);
    }

    /**
     * 将两个人脸进行比对，来判断是否为同一个人。
     * compare two faces
     * @param faceToken1 第一个人脸标识face_token
     *                   first face_token
     * @param image_url1 第一个人脸的url
     *                   image url of first face
     * @param fileByte1 第一个人脸的图片文件
     *                  file of first face
     *   三个参数只需要传一个就行了
     *   only need one of three parameter
     *
     * @param faceToken2 第二个人脸标识face_token
     *                   second face_token
     * @param image_url2 第二个人脸的url
     *                   image url of second face
     * @param fileByte2 第二个人脸的图片文件
     *                  file of second face
     *   三个参数只需要传一个就行了
     *   only need one of three parameter
     * @return
     * @throws Exception
     */
    public Response compare(String faceToken1, String image_url1, byte[] fileByte1, String base64_1,
                          String faceToken2, String image_url2, byte[] fileByte2, String base64_2) throws Exception {
        String url = webUrl + Key.SPLIT + Key.COMPARE;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileByte = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        if(!HttpRequest.isEmpty(faceToken1)){
            map.put(Key.KEY_FOR_FACE_TOKEN1, faceToken1);
        }
        if(!HttpRequest.isEmpty(faceToken2)){
            map.put(Key.KEY_FOR_FACE_TOKEN2, faceToken2);
        }
        if(!HttpRequest.isEmpty(image_url1)){
            map.put(Key.KEY_FOR_IMAGE_URL1, image_url1);
        }
        if(!HttpRequest.isEmpty(image_url2)){
            map.put(Key.KEY_FOR_IMAGE_URL2, image_url2);
        }
        if(fileByte1 != null){
            fileByte.put(Key.KEY_FOR_IMAGE_FILE1, fileByte1);
        }
        if(fileByte2 != null){
            fileByte.put(Key.KEY_FOR_IMAGE_FILE2, fileByte2);
        }
        if(!HttpRequest.isEmpty(base64_1)){
            map.put(Key.KEY_FOR_IMAGE_BASE64_1, base64_1);
        }
        if(!HttpRequest.isEmpty(base64_2)){
            map.put(Key.KEY_FOR_IMAGE_BASE64_2, base64_2);
        }
        return HttpRequest.post(url, map, fileByte);
    }

    /**
     *  在Faceset中找出与目标人脸最相似的一张或多张人脸。
     *  search the Most similar from FaceSet
     *  faceToken,image_url,image_file,buff四个参数只要传入一个就可以了，其他可以传空（null）
     *  only need one of faceToken,image_url,image_file,buff
     * @param faceToken 与Faceset中人脸比对的face_token
     *                  Identification of face
     * @param image_url 需要比对的人脸的网络图片URL
     *                  network image url
     * @param buff 需要比对的人脸的图片的二进制数组
     *             native image
     * @param faceSetToken Faceset的标识
     *                     Identification of faceSet
     * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
     *                          the number of result, 1-5,Defaults to 1
     * @return
     * @throws Exception
     */
    public Response searchByFaceSetToken(String faceToken, String image_url, byte[] buff, String faceSetToken, int returnResultCount) throws Exception {
        String url = webUrl + Key.SPLIT + Key.SEARCH;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_FACESET_TOKEN, faceSetToken);
        map.put(Key.KEY_FOR_RETURN_RESULT_COUNT, String.valueOf(returnResultCount));
        if(faceToken != null){
            map.put(Key.KEY_FOR_FACE_TOKEN, faceToken);
        }
        if(image_url != null){
            map.put(Key.KEY_FOR_IMAGE_URL, image_url);
        }
        if(buff != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, buff);
        }
        return HttpRequest.post(url, map, fileMap);
    }

    /**
     *  在Faceset中找出与目标人脸最相似的一张或多张人脸。
     *  search the Most similar from FaceSet
     *  faceToken,image_url,image_file,buff四个参数只要传入一个就可以了，其他可以传空（null）
     *  only need one of faceToken,image_url,image_file,buff
     * @param faceToken 与Faceset中人脸比对的face_token
     *                  Identification of face
     * @param image_url 需要比对的人脸的网络图片URL
     *                  network image url
     * @param buff 需要比对的人脸的图片的二进制数组
     *             native image
     * @param outerId Faceset的标识
     *                    Identification of faceSet which is definition by youself
     * @param returnResultCount 返回比对置信度最高的n个结果，范围[1,5]。默认值为1
     *                          the number of result, 1-5,Defaults to 1
     * @return
     * @throws Exception
     */
    public Response searchByOuterId(String faceToken, String image_url, byte[] buff, String outerId, int returnResultCount) throws Exception {
        String url = webUrl + Key.SPLIT + Key.SEARCH;
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, byte[]> fileMap = new HashMap<>();
        map.put(Key.KEY_FOR_APIKEY, apiKey);
        map.put(Key.KEY_FOR_APISECRET, apiSecret);
        map.put(Key.KEY_FOR_OUTER_ID, outerId);
        map.put(Key.KEY_FOR_RETURN_RESULT_COUNT, String.valueOf(returnResultCount));
        if(faceToken != null){
            map.put(Key.KEY_FOR_FACE_TOKEN, faceToken);
        }
        if(image_url != null){
            map.put(Key.KEY_FOR_IMAGE_URL, image_url);
        }
        if(buff != null){
            fileMap.put(Key.KEY_FOR_IMAGE_FILE, buff);
        }
        return HttpRequest.post(url, map, fileMap);
    }

}
