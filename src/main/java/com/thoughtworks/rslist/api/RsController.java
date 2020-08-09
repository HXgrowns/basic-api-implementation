package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.response.RsEventResponse;
import com.thoughtworks.rslist.service.RsEventService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@ControllerAdvice
public class RsController {

    private final RsEventService rsEventService;

    public RsController(RsEventService rsEventService) {
        this.rsEventService = rsEventService;
    }

    @PostMapping("/rs")
    public ResponseEntity add(@RequestBody RsEvent rsEvent) {
        RsEventEntity rs = rsEventService.save(rsEvent);
        return ResponseEntity.status(HttpStatus.CREATED).header("index", rs.getId() + "").build();
    }

    @GetMapping("/rs/{id}")
    public ResponseEntity findRsEventById(@PathVariable int id) throws InvalidIndexException {
        return ResponseEntity.ok(rsEventService.findById(id));
    }

    @GetMapping(value = "/rsEvents")
    public ResponseEntity<Page<RsEventResponse>> findListByPage(@RequestParam(required = false, defaultValue = "10") Integer size, @RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok(rsEventService.findListByPage(size, page));
    }

    @PatchMapping(value = "/rs/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody RsEventEntity inputRsEventEntity) {
        RsEventEntity rs = rsEventService.update(id, inputRsEventEntity);
        return ResponseEntity.status(HttpStatus.CREATED).header("index", rs.getId() + "").build();
    }

    @DeleteMapping("/rs/{id}")
    public ResponseEntity deleteById(@PathVariable Integer id) {
        rsEventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/rs/vote/{id}")
    public ResponseEntity<Object> vote(@PathVariable int id,
                                       @RequestParam int userId,
                                       @RequestParam int voteNum) {
        rsEventService.vote(id, userId, voteNum);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exceptionHandler(RuntimeException e) {
        CommonError commonError = new CommonError();
        commonError.setError(e.getMessage());

        return ResponseEntity.badRequest().body(commonError);
    }
}
