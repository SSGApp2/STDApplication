package com.soft.app.repository;

import com.soft.app.entity.app.AppOuAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppOuAuthRepository extends JpaRepository<AppOuAuth,Long>{

    AppOuAuth findByOuCode(String ouCode);
}
