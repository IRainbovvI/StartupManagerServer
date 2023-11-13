package org.dev.repository;

import org.dev.model.Startup;
import org.dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StartupRepository extends JpaRepository<Startup, Long> {
    List<Startup> findByAuthor(User user);
    List<Startup> findByUser(User user);
}
