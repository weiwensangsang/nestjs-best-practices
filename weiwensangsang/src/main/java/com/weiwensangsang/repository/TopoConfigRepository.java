package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.Faker;
import com.weiwensangsang.domain.bike.TopoConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Faker entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopoConfigRepository extends JpaRepository<TopoConfig, Long> {
}
