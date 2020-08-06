package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.InvalidUserException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
