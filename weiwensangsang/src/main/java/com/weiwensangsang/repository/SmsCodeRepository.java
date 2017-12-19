package com.weiwensangsang.repository;

import com.weiwensangsang.domain.SmsCode;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SmsCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SmsCodeRepository extends JpaRepository<SmsCode, Long> {

}
