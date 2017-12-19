package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.ElectricBike;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ElectricBike entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElectricBikeRepository extends JpaRepository<ElectricBike, Long> {

}
