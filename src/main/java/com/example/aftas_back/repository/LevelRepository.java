package com.example.aftas_back.repository;

import com.example.aftas_back.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {
    @Query("SELECT MAX(l.points) FROM Level l")
    Integer findMaxPoints();
    boolean existsByCode(Integer code);

}
