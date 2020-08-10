package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Modifying
    @Query(value = "update user set vote_num=vote_num-?1 where id=?2", nativeQuery = true)
    void updateVoteNum(int voteNum, int id);
}
