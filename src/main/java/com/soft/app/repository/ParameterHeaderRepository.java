package com.soft.app.repository;

import com.soft.app.entity.app.AppParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

public interface AppParameterRepository extends JpaRepository<AppParameter, Long> {

    AppParameter findByCode(String code);
}
