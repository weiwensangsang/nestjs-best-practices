package com.weiwensangsang.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.SmsCode;

import com.weiwensangsang.repository.SmsCodeRepository;
import com.weiwensangsang.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SmsCode.
 */
@RestController
@RequestMapping("/api")
public class SmsCodeResource {

    private final Logger log = LoggerFactory.getLogger(SmsCodeResource.class);

    private static final String ENTITY_NAME = "smsCode";

    private final SmsCodeRepository smsCodeRepository;

    public SmsCodeResource(SmsCodeRepository smsCodeRepository) {
        this.smsCodeRepository = smsCodeRepository;
    }

    /**
     * POST  /sms-codes : Create a new smsCode.
     *
     * @param smsCode the smsCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new smsCode, or with status 400 (Bad Request) if the smsCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sms-codes")
    @Timed
    public ResponseEntity<SmsCode> createSmsCode(@RequestBody SmsCode smsCode) throws URISyntaxException {
        log.debug("REST request to save SmsCode : {}", smsCode);
        if (smsCode.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new smsCode cannot already have an ID")).body(null);
        }
        SmsCode result = smsCodeRepository.save(smsCode);
        return ResponseEntity.created(new URI("/api/sms-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sms-codes : Updates an existing smsCode.
     *
     * @param smsCode the smsCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated smsCode,
     * or with status 400 (Bad Request) if the smsCode is not valid,
     * or with status 500 (Internal Server Error) if the smsCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sms-codes")
    @Timed
    public ResponseEntity<SmsCode> updateSmsCode(@RequestBody SmsCode smsCode) throws URISyntaxException {
        log.debug("REST request to update SmsCode : {}", smsCode);
        if (smsCode.getId() == null) {
            return createSmsCode(smsCode);
        }
        SmsCode result = smsCodeRepository.save(smsCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, smsCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sms-codes : get all the smsCodes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of smsCodes in body
     */
    @GetMapping("/sms-codes")
    @Timed
    public List<SmsCode> getAllSmsCodes() {
        log.debug("REST request to get all SmsCodes");
        return smsCodeRepository.findAll();
        }

    /**
     * GET  /sms-codes/:id : get the "id" smsCode.
     *
     * @param id the id of the smsCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the smsCode, or with status 404 (Not Found)
     */
    @GetMapping("/sms-codes/{id}")
    @Timed
    public ResponseEntity<SmsCode> getSmsCode(@PathVariable Long id) {
        log.debug("REST request to get SmsCode : {}", id);
        SmsCode smsCode = smsCodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(smsCode));
    }

    /**
     * DELETE  /sms-codes/:id : delete the "id" smsCode.
     *
     * @param id the id of the smsCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sms-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteSmsCode(@PathVariable Long id) {
        log.debug("REST request to delete SmsCode : {}", id);
        smsCodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
