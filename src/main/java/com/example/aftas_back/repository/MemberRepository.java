package com.example.aftas_back.repository;

import com.example.aftas_back.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member getMemberById(Long id);
    @Query("SELECT m FROM Member m WHERE (:id IS NULL OR m.id = :id) " +
            "AND (:name IS NULL OR lower(m.name) LIKE %:name%) " +
            "AND (:familyName IS NULL OR lower(m.familyName) LIKE %:familyName%)")
    List<Member> searchMembers(@Param("id") Long id, @Param("name") String name, @Param("familyName") String familyName);

}