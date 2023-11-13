package org.dev.repository;

import org.dev.model.StartupUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StartupUserRepository extends JpaRepository<StartupUser, Long> {

}
