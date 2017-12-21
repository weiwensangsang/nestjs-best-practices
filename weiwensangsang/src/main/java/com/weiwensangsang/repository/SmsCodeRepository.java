package com.weiwensangsang.repository;

import com.weiwensangsang.domain.bike.Faker;
import com.weiwensangsang.domain.bike.SmsCode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the SmsCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {
    Optional<SmsCode> findOneByPhone(String phone);

}
