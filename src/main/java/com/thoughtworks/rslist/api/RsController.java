package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEvent;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rs")
public class RsController {

    private List<RsEvent> rsEventList;

    public RsController() {
        this.rsEventList = new ArrayList<>(Arrays.asList(new RsEvent("第一条事件", "one"),
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
    public void addRsEvent(@RequestBody RsEvent rsEvent) {
        if (rsEvent == null) {
            return;
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
        selectedRsEvent.setName(rsEvent.getName());
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
