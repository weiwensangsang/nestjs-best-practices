package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.ResponseMessage;
import com.weiwensangsang.domain.bike.ElectricBike;
import com.weiwensangsang.domain.bike.Faker;
import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.domain.bike.LocationElectricBike;
import com.weiwensangsang.repository.ElectricBikeRepository;
import com.weiwensangsang.repository.FakerRepository;
import com.weiwensangsang.repository.LocationElectricBikeRepository;
import com.weiwensangsang.repository.LocationRepository;
import com.weiwensangsang.security.AuthoritiesConstants;
import com.weiwensangsang.service.util.weather.WeatherUtil;
import com.weiwensangsang.web.rest.util.HeaderUtil;
import com.weiwensangsang.web.rest.vm.RelationVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ElectricBike.
 */
@RestController
@RequestMapping("/api")
public class ElectricBikeResource {

    private final Logger log = LoggerFactory.getLogger(ElectricBikeResource.class);

    private static final String ENTITY_NAME = "electricBike";

    private final ElectricBikeRepository electricBikeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationElectricBikeRepository relationRepository;

    @Autowired
    private FakerRepository fakerRepository;

    public ElectricBikeResource(ElectricBikeRepository electricBikeRepository) {
        this.electricBikeRepository = electricBikeRepository;
    }

    /**
     * POST  /electric-bikes : Create a new electricBike.
     *
     * @param electricBike the electricBike to create
     * @return the ResponseEntity with status 201 (Created) and with body the new electricBike, or with status 400 (Bad Request) if the electricBike has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/electric-bikes")
    @Timed
    public ResponseEntity<ElectricBike> createElectricBike(@RequestBody ElectricBike electricBike) throws URISyntaxException {
        log.debug("REST request to save ElectricBike : {}", electricBike);
        if (electricBike.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new electricBike cannot already have an ID")).body(null);
        }
        ElectricBike result = electricBikeRepository.save(electricBike);
        return ResponseEntity.created(new URI("/api/electric-bikes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /electric-bikes : Updates an existing electricBike.
     *
     * @param electricBike the electricBike to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated electricBike,
     * or with status 400 (Bad Request) if the electricBike is not valid,
     * or with status 500 (Internal Server Error) if the electricBike couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/electric-bikes")
    @Timed
    public ResponseEntity<ElectricBike> updateElectricBike(@RequestBody ElectricBike electricBike) throws URISyntaxException {
        log.debug("REST request to update ElectricBike : {}", electricBike);
        if (electricBike.getId() == null) {
            return createElectricBike(electricBike);
        }
        ElectricBike result = electricBikeRepository.save(electricBike);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, electricBike.getId().toString()))
                .body(result);
    }

    /**
     * GET  /electric-bikes : get all the electricBikes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of electricBikes in body
     */
    @GetMapping("/electric-bikes")
    @Timed
    public List<ElectricBike> getAllElectricBikes() {
        log.debug("REST request to get all ElectricBikes");
        return electricBikeRepository.findAll();
    }

    /**
     * GET  /electric-bikes/:id : get the "id" electricBike.
     *
     * @param id the id of the electricBike to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the electricBike, or with status 404 (Not Found)
     */
    @GetMapping("/electric-bikes/{id}")
    @Timed
    public ResponseEntity<ElectricBike> getElectricBike(@PathVariable Long id) {
        log.debug("REST request to get ElectricBike : {}", id);
        ElectricBike electricBike = electricBikeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(electricBike));
    }

    /**
     * DELETE  /electric-bikes/:id : delete the "id" electricBike.
     *
     * @param id the id of the electricBike to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/electric-bikes/{id}")
    @Timed
    public ResponseEntity<Void> deleteElectricBike(@PathVariable Long id) {
        log.debug("REST request to delete ElectricBike : {}", id);
        electricBikeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/electric-bikes/control")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> controlElectricBike(@Valid @RequestBody String control) {


        if (control.equals("reset")) {
            relationRepository.deleteAll();
            electricBikeRepository.deleteAll();
            return ResponseEntity.ok(ResponseMessage.message("删除所有电单车"));
        } else {
            if (electricBikeRepository.findAll().size() != 0) {
                return ResponseEntity.ok(ResponseMessage.message("单车已投放"));
            }
            locationRepository.findAll().forEach(location -> {
                for (int i = 0; i <= location.geteBikeNumber().intValue() - 1; i++) {
                    ElectricBike result = electricBikeRepository.save(ElectricBike.create());
                    relationRepository.save(LocationElectricBike.create(result, location));
                }
            });
            return ResponseEntity.ok(ResponseMessage.message("生成电单车成功"));
        }
    }

    @GetMapping("/electric-bikes/get-all")
    @Timed
    public ResponseEntity<?> getElectricBike() {
        List<RelationVM> data = new ArrayList<>();
        locationRepository.findAll().forEach(location -> {
            data.add(RelationVM.create(
                    location,
                    relationRepository.findAllByLocation(location)
                            .stream()
                            .map(LocationElectricBike::getElectricBike)
                            .collect(Collectors.toList()))
            );
        });
        return ResponseEntity.ok(data);
    }

    @GetMapping("/electric-bikes/get/{position}")
    @Timed
    public ResponseEntity<?> getElectricBikeById(@PathVariable Long position) {
        Location location = locationRepository.findOneByPositionX(position).get();
        RelationVM vm = RelationVM.create(
                location,
                relationRepository.findAllByLocation(location)
                        .stream()
                        .map(LocationElectricBike::getElectricBike)
                        .collect(Collectors.toList()),
                WeatherUtil.ask(location.getCity()));

        return ResponseEntity.ok(vm);
    }

    @PostMapping("/electric-bikes/lock-control/phone/{phone}/bike/{bikeid}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> controlElectricBike(@PathVariable String phone, @PathVariable Long bikeid, @Valid @RequestBody String control) {

        if (control.equals("unlock")) {
            if (electricBikeRepository.findOneByType(phone).isPresent()) {
                return ResponseEntity.badRequest().body(ResponseMessage.message("您有未解锁的车"));
            } else {
                ElectricBike bike = electricBikeRepository.findOne(bikeid);
                bike.setType(phone);
                electricBikeRepository.save(bike);
                return ResponseEntity.ok(ResponseMessage.message("解锁成功"));
            }
        } else if (control.equals("lock")) {
            ElectricBike bike = electricBikeRepository.findOneByType(phone).get();
            bike.setType("lock");
            return ResponseEntity.ok(ResponseMessage.message("还车成功"));
        }
        return ResponseEntity.badRequest().body(ResponseMessage.message("type error"));
    }


}
