package com.hobbyproject.asyncmethod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressRepository extends JpaRepository<ProgressEntity, String> {
}
