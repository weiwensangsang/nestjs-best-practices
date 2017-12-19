package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.LogHistory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LogHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogHistoryRepository extends JpaRepository<LogHistory, Long> {

}
