package com.weiwensangsang.repository;

import com.weiwensangsang.domain.Path;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Path entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {

}
