package com.weiwensangsang.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.LocationElectricBike;

import com.weiwensangsang.repository.LocationElectricBikeRepository;
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
 * REST controller for managing LocationElectricBike.
 */
@RestController
@RequestMapping("/api")
public class LocationElectricBikeResource {

    private final Logger log = LoggerFactory.getLogger(LocationElectricBikeResource.class);

    private static final String ENTITY_NAME = "locationElectricBike";

    private final LocationElectricBikeRepository locationElectricBikeRepository;

    public LocationElectricBikeResource(LocationElectricBikeRepository locationElectricBikeRepository) {
        this.locationElectricBikeRepository = locationElectricBikeRepository;
    }

    /**
     * POST  /location-electric-bikes : Create a new locationElectricBike.
     *
     * @param locationElectricBike the locationElectricBike to create
     * @return the ResponseEntity with status 201 (Created) and with body the new locationElectricBike, or with status 400 (Bad Request) if the locationElectricBike has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/location-electric-bikes")
    @Timed
    public ResponseEntity<LocationElectricBike> createLocationElectricBike(@RequestBody LocationElectricBike locationElectricBike) throws URISyntaxException {
        log.debug("REST request to save LocationElectricBike : {}", locationElectricBike);
        if (locationElectricBike.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new locationElectricBike cannot already have an ID")).body(null);
        }
        LocationElectricBike result = locationElectricBikeRepository.save(locationElectricBike);
        return ResponseEntity.created(new URI("/api/location-electric-bikes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /location-electric-bikes : Updates an existing locationElectricBike.
     *
     * @param locationElectricBike the locationElectricBike to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated locationElectricBike,
     * or with status 400 (Bad Request) if the locationElectricBike is not valid,
     * or with status 500 (Internal Server Error) if the locationElectricBike couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/location-electric-bikes")
    @Timed
    public ResponseEntity<LocationElectricBike> updateLocationElectricBike(@RequestBody LocationElectricBike locationElectricBike) throws URISyntaxException {
        log.debug("REST request to update LocationElectricBike : {}", locationElectricBike);
        if (locationElectricBike.getId() == null) {
            return createLocationElectricBike(locationElectricBike);
        }
        LocationElectricBike result = locationElectricBikeRepository.save(locationElectricBike);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, locationElectricBike.getId().toString()))
            .body(result);
    }

    /**
     * GET  /location-electric-bikes : get all the locationElectricBikes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locationElectricBikes in body
     */
    @GetMapping("/location-electric-bikes")
    @Timed
    public List<LocationElectricBike> getAllLocationElectricBikes() {
        log.debug("REST request to get all LocationElectricBikes");
        return locationElectricBikeRepository.findAll();
        }

    /**
     * GET  /location-electric-bikes/:id : get the "id" locationElectricBike.
     *
     * @param id the id of the locationElectricBike to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the locationElectricBike, or with status 404 (Not Found)
     */
    @GetMapping("/location-electric-bikes/{id}")
    @Timed
    public ResponseEntity<LocationElectricBike> getLocationElectricBike(@PathVariable Long id) {
        log.debug("REST request to get LocationElectricBike : {}", id);
        LocationElectricBike locationElectricBike = locationElectricBikeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(locationElectricBike));
    }

    /**
     * DELETE  /location-electric-bikes/:id : delete the "id" locationElectricBike.
     *
     * @param id the id of the locationElectricBike to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/location-electric-bikes/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocationElectricBike(@PathVariable Long id) {
        log.debug("REST request to delete LocationElectricBike : {}", id);
        locationElectricBikeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
