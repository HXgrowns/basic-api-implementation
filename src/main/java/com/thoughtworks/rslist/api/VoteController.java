package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class VoteController {
    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/votes")
    public ResponseEntity<List<VoteEntity>> findByVoteTime(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        return ResponseEntity.ok(voteService.findByVoteTime(startTime, endTime));
    }
}
