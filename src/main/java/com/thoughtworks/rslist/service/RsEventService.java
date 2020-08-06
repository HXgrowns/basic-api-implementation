package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RsEventService {
    @Autowired
    private RsEventRepository rsEventRepository;

    @Transactional
    public RsEventEntity save(RsEventEntity rsEventEntity) {
        return rsEventRepository.save(rsEventEntity);
    }
}
