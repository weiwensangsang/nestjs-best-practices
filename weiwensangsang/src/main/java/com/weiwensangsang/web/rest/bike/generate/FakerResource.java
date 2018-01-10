package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.ResponseMessage;
import com.weiwensangsang.domain.bike.Faker;
import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.domain.bike.SmsCode;
import com.weiwensangsang.repository.ElectricBikeRepository;
import com.weiwensangsang.repository.FakerRepository;
import com.weiwensangsang.repository.LocationRepository;
import com.weiwensangsang.repository.SmsCodeRepository;
import com.weiwensangsang.security.AuthoritiesConstants;
import com.weiwensangsang.service.util.RandomUtil;
import com.weiwensangsang.service.util.sdk.demo.mail.SendSMS;
import com.weiwensangsang.service.util.sdk.exception.SmsException;
import com.weiwensangsang.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Faker.
 */
@RestController
@RequestMapping("/api")
public class FakerResource {

    private final Logger log = LoggerFactory.getLogger(FakerResource.class);

    private static final String ENTITY_NAME = "faker";

    private final FakerRepository fakerRepository;

    public FakerResource(FakerRepository fakerRepository) {
        this.fakerRepository = fakerRepository;
    }

    @Autowired
    public SmsCodeRepository smsCodeRepository;

    @Autowired
    public LocationRepository locationRepository;

    @Autowired
    private ElectricBikeRepository bikeRepository;

    /**
     * POST  /fakers : Create a new faker.
     *
     * @param faker the faker to create
     * @return the ResponseEntity with status 201 (Created) and with body the new faker, or with status 400 (Bad Request) if the faker has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fakers")
    @Timed
    public ResponseEntity<Faker> createFaker(@RequestBody Faker faker) throws URISyntaxException {
        log.debug("REST request to save Faker : {}", faker);
        if (faker.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new faker cannot already have an ID")).body(null);
        }
        Faker result = fakerRepository.save(faker);
        return ResponseEntity.created(new URI("/api/fakers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /fakers : Updates an existing faker.
     *
     * @param faker the faker to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated faker,
     * or with status 400 (Bad Request) if the faker is not valid,
     * or with status 500 (Internal Server Error) if the faker couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fakers")
    @Timed
    public ResponseEntity<Faker> updateFaker(@RequestBody Faker faker) throws URISyntaxException {
        log.debug("REST request to update Faker : {}", faker);
        if (faker.getId() == null) {
            return createFaker(faker);
        }
        Faker result = fakerRepository.save(faker);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, faker.getId().toString()))
                .body(result);
    }

    /**
     * GET  /fakers : get all the fakers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fakers in body
     */
    @GetMapping("/fakers")
    @Timed
    public List<Faker> getAllFakers() {
        log.debug("REST request to get all Fakers");
        return fakerRepository.findAll();
    }

    /**
     * GET  /fakers/:id : get the "id" faker.
     *
     * @param id the id of the faker to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the faker, or with status 404 (Not Found)
     */
    @GetMapping("/fakers/{id}")
    @Timed
    public ResponseEntity<Faker> getFaker(@PathVariable Long id) {
        log.debug("REST request to get Faker : {}", id);
        Faker faker = fakerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(faker));
    }

    /**
     * DELETE  /fakers/:id : delete the "id" faker.
     *
     * @param id the id of the faker to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fakers/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteFaker(@PathVariable Long id) {
        log.debug("REST request to delete Faker : {}", id);
        fakerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/fakers/create")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> createFaker(@Valid @RequestBody String phone) throws URISyntaxException, SmsException, ClientProtocolException, IOException {
        Long number = Long.parseLong(phone);
        if (fakerRepository.findOneByPhone(phone).isPresent()) {
            return ResponseEntity.badRequest().body(ResponseMessage.message("验证码只有一次机会"));
        }
        if (smsCodeRepository.findOneByPhone(phone).isPresent()) {
            return ResponseEntity.badRequest().body(ResponseMessage.message("验证码只有一次机会"));
        }
        SmsCode code = SmsCode.create(phone, RandomUtil.get4SMSCode());
        try {
            SendSMS.sendActivated(phone, code.getCode());
            smsCodeRepository.save(code);
            fakerRepository.save(Faker.create(phone));
            return ResponseEntity.ok(ResponseMessage.message("已经发送验证码"));
        } catch (SmsException e) {
            return ResponseEntity.badRequest().body(ResponseMessage.message("短信异常"));
        }

    }

    @PostMapping("/fakers/activate")
    @Timed
    public ResponseEntity<?> activateFaker(@Valid @RequestBody SmsCode data) throws URISyntaxException {
        SmsCode code = smsCodeRepository.findOneByPhone(data.getPhone()).get();
        if (!code.getCode().equals(data.getCode())) {
            return ResponseEntity.badRequest().body(ResponseMessage.message("激活失败"));
        }
        Faker user = fakerRepository.findOneByPhone(data.getPhone()).get();
        user.setActivated(true);
        List<Location> locations = locationRepository.findAll();
        if (locations.size() > 2) {
            user.setState(locations.get(RandomUtil.getLocate(locations.size() - 1)).getPositionX().toString());
        }
        fakerRepository.save(user);
        return ResponseEntity.ok(ResponseMessage.message("激活成功"));
    }

    @PostMapping("/fakers/locate")
    @Timed
    public ResponseEntity<?> locateFaker() throws URISyntaxException {


        List<Location> locations = locationRepository.findAll();
        if (locations.size() <= 2) {
            return ResponseEntity.badRequest().body(ResponseMessage.message("请先生成拓扑"));
        }
        fakerRepository.findAllByActivated(true)
                .forEach(faker -> {
                    if (!bikeRepository.findOneByType(faker.getPhone()).isPresent()) {
                        faker.setState(locations.get(RandomUtil.getLocate(locations.size())).getPositionX().toString());
                        fakerRepository.save(faker);
                    }
                });
        return ResponseEntity.ok(ResponseMessage.message("随机放置成功, 已解锁车的用户不能瞎跑"));
    }

    @PostMapping("/fakers/deposit/{phone}")
    @Timed
    public ResponseEntity<?> depositFaker(@PathVariable String phone) {
        Faker faker = fakerRepository.findOneByPhone(phone).get();
        faker.setBalance(faker.getBalance() + 388L);
        fakerRepository.save(faker);
        return ResponseEntity.ok(ResponseMessage.message("已充值388元"));
    }
}
