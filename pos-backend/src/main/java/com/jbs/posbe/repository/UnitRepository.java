package com.jbs.posbe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbs.posbe.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Optional<Unit> findByUname(String uname);

    Optional<Unit> findByUabbr(String uabbr);
}