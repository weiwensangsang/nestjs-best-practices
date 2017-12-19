package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.bike.Faker;

import com.weiwensangsang.repository.FakerRepository;
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
    public ResponseEntity<Void> deleteFaker(@PathVariable Long id) {
        log.debug("REST request to delete Faker : {}", id);
        fakerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
