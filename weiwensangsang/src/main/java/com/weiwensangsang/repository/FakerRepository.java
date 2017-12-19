package com.weiwensangsang.repository;

import com.weiwensangsang.domain.Faker;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Faker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FakerRepository extends JpaRepository<Faker, Long> {

}
