package com.example.aftas_back.repository;

import com.example.aftas_back.domain.Fish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishRepository extends JpaRepository<Fish, Long> {
    Fish findByName(String name);

}
