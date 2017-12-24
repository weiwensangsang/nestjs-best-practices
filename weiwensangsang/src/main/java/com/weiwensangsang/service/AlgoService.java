package com.weiwensangsang.service;

import com.weiwensangsang.config.Constants;
import com.weiwensangsang.domain.Authority;
import com.weiwensangsang.domain.User;
import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.repository.AuthorityRepository;
import com.weiwensangsang.repository.LocationRepository;
import com.weiwensangsang.repository.UserRepository;
import com.weiwensangsang.security.AuthoritiesConstants;
import com.weiwensangsang.security.SecurityUtils;
import com.weiwensangsang.service.dto.UserDTO;
import com.weiwensangsang.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void generateTopo(Long height, Long weight) {
        List<Location> locations = new ArrayList<Location>();
        for (Long x = 1L; x<= height; x++) {
            for (Long y = 1L; y<= weight; y++) {
                locations.add(Location.create(x, y));
            }
        }
        log.error(locations.size() +"");
        locationRepository.save(locations);
        generateEdge(height, weight);
    }

    private void generateEdge(Long height, Long weight) {

    }

}
