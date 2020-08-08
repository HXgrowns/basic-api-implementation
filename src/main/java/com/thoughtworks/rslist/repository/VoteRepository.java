package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {
    List<VoteEntity> findByVoteTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
