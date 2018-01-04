package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.ResponseMessage;
import com.weiwensangsang.domain.bike.Dijkstras;
import com.weiwensangsang.domain.bike.ElectricBike;
import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.domain.bike.Path;

import com.weiwensangsang.repository.ElectricBikeRepository;
import com.weiwensangsang.repository.LocationRepository;
import com.weiwensangsang.repository.PathRepository;
import com.weiwensangsang.security.AuthoritiesConstants;
import com.weiwensangsang.service.AlgoService;
import com.weiwensangsang.web.rest.util.HeaderUtil;
import com.weiwensangsang.web.rest.vm.Link;
import com.weiwensangsang.web.rest.vm.PathVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Path.
 */
@RestController
@RequestMapping("/api")
public class PathResource {

    private final Logger log = LoggerFactory.getLogger(PathResource.class);

    private static final String ENTITY_NAME = "path";

    private final PathRepository pathRepository;

    public PathResource(PathRepository pathRepository) {
        this.pathRepository = pathRepository;
    }

    @Autowired
    private Dijkstras algo;

    @Autowired
    private ElectricBikeRepository bikeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AlgoService algoService;

    /**
     * POST  /paths : Create a new path.
     *
     * @param path the path to create
     * @return the ResponseEntity with status 201 (Created) and with body the new path, or with status 400 (Bad Request) if the path has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paths")
    @Timed
    public ResponseEntity<Path> createPath(@RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to save Path : {}", path);
        if (path.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new path cannot already have an ID")).body(null);
        }
        Path result = pathRepository.save(path);
        return ResponseEntity.created(new URI("/api/paths/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paths : Updates an existing path.
     *
     * @param path the path to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated path,
     * or with status 400 (Bad Request) if the path is not valid,
     * or with status 500 (Internal Server Error) if the path couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paths")
    @Timed
    public ResponseEntity<Path> updatePath(@RequestBody Path path) throws URISyntaxException {
        log.debug("REST request to update Path : {}", path);
        if (path.getId() == null) {
            return createPath(path);
        }
        Path result = pathRepository.save(path);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, path.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paths : get all the paths.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paths in body
     */
    @GetMapping("/paths")
    @Timed
    public List<Path> getAllPaths() {
        log.debug("REST request to get all Paths");
        return pathRepository.findAll();
        }

    /**
     * GET  /paths/:id : get the "id" path.
     *
     * @param id the id of the path to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the path, or with status 404 (Not Found)
     */
    @GetMapping("/paths/{id}")
    @Timed
    public ResponseEntity<Path> getPath(@PathVariable Long id) {
        log.debug("REST request to get Path : {}", id);
        Path path = pathRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(path));
    }

    /**
     * DELETE  /paths/:id : delete the "id" path.
     *
     * @param id the id of the path to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paths/{id}")
    @Timed
    public ResponseEntity<Void> deletePath(@PathVariable Long id) {
        log.debug("REST request to delete Path : {}", id);
        pathRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @PostMapping("/paths/algo-recommend/src/{src}/dst/{dst}/type/{type}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> countPath(@PathVariable Long src, @PathVariable Long dst, @PathVariable String type) {
        if (type.equals("路径最短")) {
            return algoService.countPrimaryPath(src, dst);
        } else if (type.equals("出行最吉")) {
            try {
                return algoService.countLuckyPath(src, dst);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(ResponseMessage.message("无法找到一条大吉大利的路"));
            }
        } else {
            return ResponseEntity.badRequest().body(ResponseMessage.message("不支持的类型"));
        }

    }
}
