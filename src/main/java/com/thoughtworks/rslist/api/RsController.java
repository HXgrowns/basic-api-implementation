package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.exception.CommonError;
import com.thoughtworks.rslist.exception.InvalidIndexException;
import com.thoughtworks.rslist.exception.InvalidParamException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.service.RsEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rs")
@ControllerAdvice
public class RsController {

    private List<RsEvent> rsEventList;

    @Autowired
    private RsEventRepository rsEventRepository;

    @Autowired
    private RsEventService rsEventService;

    public RsController() {
        this.rsEventList = new ArrayList<>(Arrays.asList(new RsEvent("第一条事件", "one",
                        new User(
                                "huxiao",
                                19,
                                "male",
                                "a@thoughtworks.com",
                                "08888888888")),
                new RsEvent("第二条事件", "two"),
                new RsEvent("第三条事件", "three")));
    }

    @GetMapping("/{index}")
    @JsonView(RsEvent.PrivateView.class)
    public ResponseEntity<RsEvent> getRsEventById(@PathVariable int index) throws InvalidIndexException {
        if (index > rsEventList.size() - 1 || index < 0) {
            throw new InvalidIndexException("invalid index");
        }
        return ResponseEntity.ok(rsEventList.get(index));
    }

    @GetMapping(value = "/list")
    @JsonView(RsEvent.PublicView.class)
    public ResponseEntity<List<RsEvent>> GetRsEventListByGivenRange(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) throws InvalidIndexException {
        if (start == null) {
            start = 0;
        }

        if (end == null) {
            end = rsEventList.size();
        }

        if (start < 0 || end > rsEventList.size()) {
            throw new InvalidIndexException("invalid request param");
        }

        return ResponseEntity.ok(rsEventList.subList(start, end));
    }

    @PostMapping
    @JsonView(RsEvent.PrivateView.class)
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent, BindingResult bindingResult) throws InvalidParamException {
        if (rsEvent == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidParamException("invalid param");
        }

        User user = rsEvent.getUser();
        if (user != null && (user.getPhone() == null || user.getUserName() == null || user.getEmail() == null)) {
            throw new InvalidParamException("invalid param");
        }

        for (RsEvent event : rsEventList) {
            if (event.getUser() == null || rsEvent.getUser() == null || rsEvent.getUser().getUserName() == null) {
                continue;
            }

            if (rsEvent.getUser().getUserName().equals(event.getUser().getUserName())) {
                rsEvent.setUser(event.getUser());
                break;
            }
        }
        rsEventList.add(rsEvent);
        return ResponseEntity.status(HttpStatus.CREATED).header("index", rsEventList.size() - 1 + "").build();
    }

    @PutMapping(value = "/{index}")
    public ResponseEntity<Object> updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
        RsEvent selectedRsEvent = rsEventList.get(index);
        if (selectedRsEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        selectedRsEvent.setKeyword(rsEvent.getKeyword());
        selectedRsEvent.setEventName(rsEvent.getEventName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rs/listall")
    public ResponseEntity<List<RsEvent>> GetRsEventList() {
        return ResponseEntity.ok(rsEventList);
    }

    @DeleteMapping("/{index}")
    public ResponseEntity<Object> deleteRsEvent(@PathVariable int index) {
        RsEvent selectedRsEvent = rsEventList.get(index);
        if (selectedRsEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        rsEventList.remove(index);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/jpa")
    public ResponseEntity add(@RequestBody RsEventEntity rsEventEntity) {
        RsEventEntity rs = rsEventService.save(rsEventEntity);
        return ResponseEntity.status(HttpStatus.CREATED).header("index", rs.getId() + "").build();
    }

    @PatchMapping(value = "/jpa/{index}")
    public ResponseEntity<Object> update(@PathVariable int index, @RequestBody RsEventEntity inputRsEventEntity) {
        RsEventEntity rs = rsEventService.update(index, inputRsEventEntity);
        return ResponseEntity.status(HttpStatus.CREATED).header("index", rs.getId() + "").build();
    }

    @PostMapping(value = "/vote/jpa/{index}")
    public ResponseEntity<Object> vote(@PathVariable int index,
                                       @RequestParam int userId,
                                       @RequestParam int voteNum) {
        rsEventService.vote(index, userId, voteNum);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exceptionHandler(RuntimeException e) {
        CommonError commonError = new CommonError();
        commonError.setError(e.getMessage());

        return ResponseEntity.badRequest().body(commonError);
    }
}
