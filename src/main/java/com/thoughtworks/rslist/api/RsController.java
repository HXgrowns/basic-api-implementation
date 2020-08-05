package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEvent;
import com.thoughtworks.rslist.entity.User;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
    public RsEvent getRsEventById(@PathVariable int index) {
        return rsEventList.get(index);
    }

    @GetMapping(value = "/list")
    public List<RsEvent> GetRsEventListByGivenRange(@RequestParam(required = false, defaultValue = "0") Integer start, @RequestParam(required = false) Integer end) {
        if (end == null) {
            end = rsEventList.size();
        }
        return rsEventList.subList(start, end);
    }

    @PostMapping
    public void addRsEvent(@RequestBody @Valid RsEvent rsEvent) {
        if (rsEvent == null) {
            return;
        }

        for (RsEvent event : rsEventList) {
            if(event.getUser() == null || rsEvent.getUser() == null || rsEvent.getUser().getUserName() == null) {
                continue;
            }

            if(rsEvent.getUser().getUserName().equals(event.getUser().getUserName())) {
                rsEvent.setUser(event.getUser());
                break;
            }
        }

        rsEventList.add(rsEvent);
    }

    @PutMapping(value = "/{index}")
    public void updateRsEvent(@PathVariable int index, @RequestBody RsEvent rsEvent) {
        RsEvent selectedRsEvent = rsEventList.get(index);
        if (selectedRsEvent == null) {
            return;
        }
        selectedRsEvent.setKeyword(rsEvent.getKeyword());
        selectedRsEvent.setEventName(rsEvent.getEventName());
    }

    @GetMapping("/rs/listall")
    public List<RsEvent> GetRsEventList() {
        return rsEventList;
    }

    @DeleteMapping("/{index}")
    public void deleteRsEvent(@PathVariable int index) {
        RsEvent selectedRsEvent = rsEventList.get(index);
        if (selectedRsEvent == null) {
            return;
        }
        rsEventList.remove(index);
    }
}
