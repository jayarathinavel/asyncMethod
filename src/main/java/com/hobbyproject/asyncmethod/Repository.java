package com.hobbyproject.asyncmethod;

import org.springframework.data.jpa.repository.JpaRepository;
@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<Entity, String> {
}
