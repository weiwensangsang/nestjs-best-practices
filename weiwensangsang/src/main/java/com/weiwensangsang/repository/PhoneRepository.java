package com.weiwensangsang.repository;

import com.weiwensangsang.domain.Phone;
import com.weiwensangsang.domain.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("select phone from Phone phone where phone.type is null")
    List<Phone> findAllNotLocation();

}
