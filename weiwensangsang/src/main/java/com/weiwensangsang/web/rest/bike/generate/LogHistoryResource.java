package com.weiwensangsang.web.rest.bike.generate;

import com.codahale.metrics.annotation.Timed;
import com.weiwensangsang.domain.bike.LogHistory;

import com.weiwensangsang.repository.LogHistoryRepository;
import com.weiwensangsang.web.rest.util.HeaderUtil;
import com.weiwensangsang.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LogHistory.
 */
@RestController
@RequestMapping("/api")
public class LogHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LogHistoryResource.class);

    private static final String ENTITY_NAME = "logHistory";

    private final LogHistoryRepository logHistoryRepository;

    public LogHistoryResource(LogHistoryRepository logHistoryRepository) {
        this.logHistoryRepository = logHistoryRepository;
    }

    /**
     * POST  /log-histories : Create a new logHistory.
     *
     * @param logHistory the logHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logHistory, or with status 400 (Bad Request) if the logHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-histories")
    @Timed
    public ResponseEntity<LogHistory> createLogHistory(@RequestBody LogHistory logHistory) throws URISyntaxException {
        log.debug("REST request to save LogHistory : {}", logHistory);
        if (logHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new logHistory cannot already have an ID")).body(null);
        }
        LogHistory result = logHistoryRepository.save(logHistory);
        return ResponseEntity.created(new URI("/api/log-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-histories : Updates an existing logHistory.
     *
     * @param logHistory the logHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logHistory,
     * or with status 400 (Bad Request) if the logHistory is not valid,
     * or with status 500 (Internal Server Error) if the logHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-histories")
    @Timed
    public ResponseEntity<LogHistory> updateLogHistory(@RequestBody LogHistory logHistory) throws URISyntaxException {
        log.debug("REST request to update LogHistory : {}", logHistory);
        if (logHistory.getId() == null) {
            return createLogHistory(logHistory);
        }
        LogHistory result = logHistoryRepository.save(logHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-histories : get all the logHistories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logHistories in body
     */
    @GetMapping("/log-histories")
    @Timed
    public ResponseEntity<List<LogHistory>> getAllLogHistories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of LogHistories");
        Page<LogHistory> page = logHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log-histories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /log-histories/:id : get the "id" logHistory.
     *
     * @param id the id of the logHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logHistory, or with status 404 (Not Found)
     */
    @GetMapping("/log-histories/{id}")
    @Timed
    public ResponseEntity<LogHistory> getLogHistory(@PathVariable Long id) {
        log.debug("REST request to get LogHistory : {}", id);
        LogHistory logHistory = logHistoryRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logHistory));
    }

    /**
     * DELETE  /log-histories/:id : delete the "id" logHistory.
     *
     * @param id the id of the logHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-histories/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogHistory(@PathVariable Long id) {
        log.debug("REST request to delete LogHistory : {}", id);
        logHistoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
