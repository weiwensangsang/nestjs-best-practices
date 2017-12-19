package com.weiwensangsang.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.Path;

import com.weiwensangsang.repository.PathRepository;
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
}
