package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.ResponseMessage;
import com.weiwensangsang.repository.ElectricBikeRepository;
import com.weiwensangsang.repository.LocationRepository;
import com.weiwensangsang.repository.SmsCodeRepository;
import com.weiwensangsang.service.util.face.CommonOperate;
import com.weiwensangsang.service.util.face.FaceOperate;
import com.weiwensangsang.service.util.face.FaceSetOperate;
import com.weiwensangsang.service.util.face.Response;
import com.weiwensangsang.web.rest.vm.FaceVM;
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


    @PostMapping("/fakers/face/faceset-operation")
    @Timed
    public ResponseEntity<?> faceSet(@Valid @RequestBody FaceVM data) throws Exception {
        FaceSetOperate set = new FaceSetOperate(false);
        String control = data.getControl();
        switch (control) {
            case "create": {
                Response r = set.createFaceSet(data.getName(), data.getOuterId(), null, null, null, 0);
                return ResponseEntity.ok(r);
            }
            case "get-all": {
                Response r = set.getFaceSets(null);
                return ResponseEntity.ok(r);
            }
            case "get-detail": {
                Response r = set.getDetailByOuterId(data.getOuterId());
                return ResponseEntity.ok(r);
            }
            default: {
                Response r = set.getDetailByOuterId(control);
                return ResponseEntity.ok(r);
            }
        }

    }

    @PostMapping("/fakers/face/face-operation")
    @Timed
    public ResponseEntity<?> face(@Valid @RequestBody FaceVM data) throws Exception {
        CommonOperate common = new CommonOperate(false);
        FaceSetOperate set = new FaceSetOperate(false);
        FaceOperate face = new FaceOperate(false);
        String control = data.getControl();
        switch (control) {
            case "create": {
                Response r = common.detectBase64(data.getContent(), 0, FACESTATUS);
                return ResponseEntity.ok(r);
            }
            case "get-detail": {
                Response r = face.faceGetDetail(data.getContent());
                return ResponseEntity.ok(r);
            }
            case "search": {
                Response r = set.
                return ResponseEntity.ok(r);
            }
            default: {
                String token = control;
                String name = content;
                Response r = set.addFaceByFaceToken(token, "d0ec8159f8aed9f6c47b462a81d51c5d");
                face.faceSetUserId(token, name);
                return ResponseEntity.ok(r);
            }
        }
    }
}
