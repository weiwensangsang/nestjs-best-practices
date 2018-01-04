package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.Location;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Location entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findOneByPositionX(Long positionX);

    @Query("select location from Location location where location.type <> '凶'")
    List<Location> queryAllLuckyLocation();

}
