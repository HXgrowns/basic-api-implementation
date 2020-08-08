package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    VoteRepository voteRepository;

    public VoteEntity findById(int id) {

        return null;
    }

    public List<VoteEntity> findByVoteTime(LocalDateTime startTime, LocalDateTime endTime) {
        return voteRepository.findByVoteTimeBetween(startTime, endTime);
    }

}
