package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEvent;
import com.thoughtworks.rslist.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rs")
public class RsController {

    private List<RsEvent> rsEventList;

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
    public ResponseEntity<RsEvent> getRsEventById(@PathVariable int index) {
        return ResponseEntity.ok(rsEventList.get(index));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<RsEvent>> GetRsEventListByGivenRange(@RequestParam(required = false, defaultValue = "0") Integer start, @RequestParam(required = false) Integer end) {
        if (end == null) {
            end = rsEventList.size();
        }
        return ResponseEntity.ok(rsEventList.subList(start, end));
    }

    @PostMapping
    public ResponseEntity addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        if (rsEvent == null) {
            return ResponseEntity.badRequest().build();
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
}
