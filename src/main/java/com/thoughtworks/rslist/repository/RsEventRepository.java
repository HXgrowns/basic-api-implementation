package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RsEventRepository extends CrudRepository<RsEventEntity, Integer> {
    @Modifying
    @Query(value = "delete from rs_event where user_id=?1", nativeQuery = true)
    void deleteByUserId(Integer id);

    @Modifying
    @Query(value = "update rs_event set vote_num = vote_num + ?1 where id=?2", nativeQuery = true)
    void updateVoteNum(int voteNum, int id);
}
