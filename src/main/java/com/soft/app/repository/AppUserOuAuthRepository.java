package com.soft.app.repository;

import com.soft.app.entity.app.AppUser;
import com.soft.app.entity.app.AppUserOuAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserOuAuthRepository extends JpaRepository<AppUserOuAuth,Long> {
    List<AppUserOuAuth> findByAppUser(AppUser appUser);
}
