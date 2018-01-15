package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.ResponseMessage;
import com.weiwensangsang.repository.ElectricBikeRepository;
import com.weiwensangsang.repository.LocationRepository;
import com.weiwensangsang.repository.SmsCodeRepository;
import com.weiwensangsang.service.util.face.CommonOperate;
import com.weiwensangsang.service.util.face.FaceSetOperate;
import com.weiwensangsang.service.util.face.Response;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.weiwensangsang.config.Constants.FACESTATUS;

/**
 * REST controller for managing Faker.
 */
@RestController
@RequestMapping("/api")
public class FaceResource {

    private final Logger log = LoggerFactory.getLogger(FaceResource.class);

    @Autowired
    public SmsCodeRepository smsCodeRepository;

    @Autowired
    public LocationRepository locationRepository;

    @Autowired
    private ElectricBikeRepository bikeRepository;


    @PostMapping("/fakers/face/faceset-operation/{control}")
    @Timed
    public ResponseEntity<?> faceSet(@PathVariable String control) throws Exception {
        FaceSetOperate set = new FaceSetOperate(false);
        if (control.equals("create")) {
            Response r = set.createFaceSet("faker", "faker_2", null, null, null, 0);
            return ResponseEntity.ok(r);
        } else if (control.equals("get-all")) {
            Response r = set.getFaceSets(null);
            return ResponseEntity.ok(r);
        } else if (control.equals("get-v")) {
            Response r = set.getFaceSets(null);
            return ResponseEntity.ok(r);
        } else {
            Response r = set.getDetailByOuterId(control);
            return ResponseEntity.ok(r);
        }

    }

    @PostMapping("/fakers/face/face-operation/{control}")
    @Timed
    public ResponseEntity<?> face(@PathVariable String control, @Valid @RequestBody String content) throws Exception {
        CommonOperate common = new CommonOperate(false);
        FaceSetOperate set = new FaceSetOperate(false);
        if (control.equals("create")) {
            Response r = common.detectBase64(content, 0, FACESTATUS);
            return ResponseEntity.ok(r);
        } else {
            Response r = set.addFaceByFaceToken(control, "d0ec8159f8aed9f6c47b462a81d51c5d");
            return ResponseEntity.ok(r);
        }
    }


    public static boolean generateImage(String imgStr, String path) {
        if(imgStr == null){
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            //解密
            byte[] b = decoder.decodeBuffer(imgStr);
            //处理数据
            for (int i = 0;i<b.length;++i){
                if(b[i]<0){
                    b[i]+=256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
