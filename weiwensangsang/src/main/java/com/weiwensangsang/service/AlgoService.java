package com.weiwensangsang.service;

import com.weiwensangsang.config.Constants;
import com.weiwensangsang.domain.Authority;
import com.weiwensangsang.domain.User;
import com.weiwensangsang.domain.bike.Dijkstras;
import com.weiwensangsang.domain.bike.Faker;
import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.domain.bike.LogHistory;
import com.weiwensangsang.repository.*;
import com.weiwensangsang.security.AuthoritiesConstants;
import com.weiwensangsang.security.SecurityUtils;
import com.weiwensangsang.service.dto.UserDTO;
import com.weiwensangsang.service.util.RandomUtil;
import com.weiwensangsang.web.rest.vm.Link;
import com.weiwensangsang.web.rest.vm.PathVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class AlgoService {

    private final Logger log = LoggerFactory.getLogger(AlgoService.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private Dijkstras algo;

    @Autowired
    private PathRepository pathRepository;

    @Autowired
    private LogHistoryRepository logHistoryRepository;

    public void generateTopo(Long height, Long weight) {
        List<Location> locations = new ArrayList<Location>();
        for (Long x = 1L; x <= height; x++) {
            for (Long y = 1L; y <= weight; y++) {
                locations.add(Location.create(x, y));
            }
        }
        log.error(locations.size() + "");
        locationRepository.save(locations);
        generateEdge(height, weight);
    }

    private void generateEdge(Long height, Long weight) {

    }

    public ResponseEntity<?> countPrimaryPath(Long src, Long dst) {
        List<Long> longs = new ArrayList<Long>();
        Long sum = 0L;
        List<Link> links = new ArrayList<Link>();
        List<Location> locations = new ArrayList<Location>();
        longs = algo.countFirst(src, dst);
        locations = longs
            .stream()
            .map(primaryId -> locationRepository.findOneByPositionX(primaryId).get())
            .collect(Collectors.toList());

        if (longs.size() >= 2) {
            for (int i = 0; i <= longs.size() - 2; i++) {
                links.add(new Link(longs.get(i), longs.get(i + 1)));
                try {
                    sum += pathRepository.findOneByFromWhereAndToWhere(locations.get(i), locations.get(i + 1)).get().getLength();

                } catch (Exception e) {
                    sum += pathRepository.findOneByFromWhereAndToWhere(locations.get(i + 1), locations.get(i)).get().getLength();
                }
            }
        }
        return ResponseEntity.ok(PathVM.create(longs, locations, links, sum));
    }

    public ResponseEntity<?> countLuckyPath(Long src, Long dst) {
        fix(src, dst);
        List<Long> longs = new ArrayList<Long>();
        Long sum = 0L;
        List<Link> links = new ArrayList<Link>();
        List<Location> locations = new ArrayList<Location>();

        longs = algo.countLucky(src, dst);
        reset(src, dst);
        locations = longs
                .stream()
                .map(primaryId -> locationRepository.findOneByPositionX(primaryId).get())
                .collect(Collectors.toList());

        if (longs.size() >= 2) {
            for (int i = 0; i <= longs.size() - 2; i++) {
                links.add(new Link(longs.get(i), longs.get(i + 1)));
                try {
                    sum += pathRepository.findOneByFromWhereAndToWhere(locations.get(i), locations.get(i + 1)).get().getLength();

                } catch (Exception e) {
                    sum += pathRepository.findOneByFromWhereAndToWhere(locations.get(i + 1), locations.get(i)).get().getLength();
                }
            }
        }
        return ResponseEntity.ok(PathVM.create(longs, locations, links, sum));
    }

    public void fix(Long src, Long dst) {
        Location l1 = locationRepository.findOneByPositionX(src).get();
        l1.fixType();
        locationRepository.save(l1);
        Location l2 = locationRepository.findOneByPositionX(dst).get();
        l2.fixType();
        locationRepository.save(l2);
    }

    public void reset(Long src, Long dst) {
        Location l1 = locationRepository.findOneByPositionX(src).get();
        l1.resetType();
        locationRepository.save(l1);
        Location l2 = locationRepository.findOneByPositionX(dst).get();
        l2.resetType();
        locationRepository.save(l2);
    }

    public void log(String content) {
        logHistoryRepository.save(LogHistory.create(content));
    }

    public void log(String content, Faker faker) {
        logHistoryRepository.save(LogHistory.create(content, faker));
    }

}
