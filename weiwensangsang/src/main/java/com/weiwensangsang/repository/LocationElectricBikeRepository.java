package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.LocationElectricBike;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LocationElectricBike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationElectricBikeRepository extends JpaRepository<LocationElectricBike, Long> {

}
