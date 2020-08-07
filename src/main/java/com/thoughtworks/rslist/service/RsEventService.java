package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidUserException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RsEventService {
    @Autowired
    private RsEventRepository rsEventRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public RsEventEntity save(RsEventEntity rsEventEntity) {
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
    public RsEventEntity update(int index, RsEventEntity inputRsEventEntity) {
        RsEventEntity findedRsEventEntity = rsEventRepository.findById(index).orElse(null);
        Integer inputUserId = inputRsEventEntity.getUser().getId();
        if (findedRsEventEntity == null
                || inputUserId == null
                || !inputUserId.equals(findedRsEventEntity.getUser().getId())) {
            throw new InvalidIndexException("index is not exists");
        }

        findedRsEventEntity.setKeyword(inputRsEventEntity.getKeyword() != null ? inputRsEventEntity.getKeyword() : findedRsEventEntity.getKeyword());
        findedRsEventEntity.setEventName(inputRsEventEntity.getEventName() != null ? inputRsEventEntity.getEventName() : findedRsEventEntity.getEventName());
        return rsEventRepository.save(findedRsEventEntity);
    }
}
