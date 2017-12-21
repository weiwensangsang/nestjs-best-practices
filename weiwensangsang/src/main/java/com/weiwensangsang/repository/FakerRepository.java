package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.Faker;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the Faker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FakerRepository extends JpaRepository<Faker, Long> {
    Optional<Faker> findOneByPhone(String phone);
}
