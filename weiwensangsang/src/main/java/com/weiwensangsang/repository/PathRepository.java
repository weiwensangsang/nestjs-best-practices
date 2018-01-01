package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.Location;
import com.weiwensangsang.domain.bike.Path;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the Path entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PathRepository extends JpaRepository<Path, Long> {
    Optional<Path> findOneByFromWhereAndToWhere(Location from, Location to);
    List<Path> findOneByFromWhere(Location from);
    List<Path> findOneByToWhere(Location to);

    @Query("select path from Path path where path.toWhere =?1 or path.fromWhere =?1")
    List<Path> queryByLocation(Location to);
}
