package com.amit.devtracker.repositories;

import com.amit.devtracker.domain.entities.Roadmap;
import com.amit.devtracker.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoadmapRepository extends JpaRepository<Roadmap, UUID> {

    List<Roadmap> findByUserId(UUID userId);
    boolean existsByUserIdAndTitleIgnoreCase(UUID userId, String title);
}


