package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

public class VoteService {
    private final VoteRepository voteRepository;

    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public List<VoteEntity> findByVoteTime(LocalDateTime startTime, LocalDateTime endTime) {
        return voteRepository.findByVoteTimeBetween(startTime, endTime);
    }

}
