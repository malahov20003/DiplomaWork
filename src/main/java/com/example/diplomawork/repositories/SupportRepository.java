package com.example.diplomawork.repositories;

import com.example.diplomawork.entities.SupportRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<SupportRequest, Long> {
    SupportRequest save(SupportRequest request);
}
