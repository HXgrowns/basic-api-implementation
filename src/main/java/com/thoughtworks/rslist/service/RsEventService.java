package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidUserException;
import com.thoughtworks.rslist.exception.InvalidRsEventException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RsEventService {
    @Autowired
    private RsEventRepository rsEventRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public RsEventEntity save(RsEvent rsEvent) {
        RsEventEntity rsEventEntity = rsEvent.build();
        if (rsEventEntity.getUser() == null || rsEventEntity.getUser().getId() == null
                || !userRepository.findById(rsEventEntity.getUser().getId()).isPresent()) {
            throw new InvalidUserException("user is not exists");
        }
        return rsEventRepository.save(rsEventEntity);
    }

    @Transactional
    public void deleteByUserId(Integer id) {
        rsEventRepository.deleteByUserId(id);
    }

    @Transactional
    public RsEventEntity update(int id, RsEventEntity inputRsEventEntity) {
        int userId = Optional.ofNullable(inputRsEventEntity)
                .map(o -> o.getUser().getId())
                .orElseThrow(() -> new InvalidRsEventException("user is null"));

        RsEventEntity findedRsEventEntity = rsEventRepository.findById(id).orElseThrow(() -> new InvalidRsEventException("rsEvent is not exists"));

        if (findedRsEventEntity.getUser() == null || findedRsEventEntity.getUser().getId() != userId) {
            throw new InvalidRsEventException("userId is required");
        }

        findedRsEventEntity.setKeyword(inputRsEventEntity.getKeyword() != null ? inputRsEventEntity.getKeyword() : findedRsEventEntity.getKeyword());
        findedRsEventEntity.setEventName(inputRsEventEntity.getEventName() != null ? inputRsEventEntity.getEventName() : findedRsEventEntity.getEventName());
        return rsEventRepository.save(findedRsEventEntity);
    }

    @Transactional
    public void vote(int id, int userId, int voteNum) {
        if (!rsEventRepository.existsById(id)) {
            throw new InvalidRsEventException("reEvent is not exists");
        }

        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new InvalidRsEventException("user is not exists"));

        System.out.println(userEntity.getVote());
        if (userEntity.getVote() < voteNum) {
            throw new InvalidRsEventException("user total voteNum < voteNum");
        }

        VoteEntity.builder()
                .user(userEntity)
                .rsEventEntity(RsEventEntity.builder().id(id).build())
                .voteNum(voteNum)
                .voteTime(LocalDateTime.now())
                .build();

        rsEventRepository.updateVoteNum(voteNum, id);
        userRepository.updateVoteNum(voteNum, userId);
    }

    public RsEventEntity findById(int id) {
        return rsEventRepository.findById(id).orElseThrow(() -> new InvalidRsEventException("rsEvent is not exists"));
    }

    public Page<RsEventEntity> findListByPage(Integer size, Integer page) {
        return rsEventRepository.findAll(PageRequest.of(page, size));
    }

    public void deleteById(Integer id) {
        if (!rsEventRepository.findById(id).isPresent()) {
            throw new InvalidIndexException("rsEvent is not exists");
        }
        rsEventRepository.deleteById(id);
    }
}
