package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.entity.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RsController {
    private List<String> rsList = Arrays.asList("第一条事件", "第二条事件", "第三条事件");
    private List<RsEvent> rsEventList = new ArrayList<>(Arrays.asList(new RsEvent("第一条事件", "one"),
            new RsEvent("第二条事件", "two"),
            new RsEvent("第三条事件", "three")));

    @GetMapping("/rs")
    public String getRsEventById(@RequestParam int index) {
        return rsList.get(index - 1);
    }

    @GetMapping(value = "/rs/list")
    public String GetRsEventListByGivenRange(@RequestParam(required = false, defaultValue = "1") Integer start, @RequestParam(required = false) Integer end) {
        if (end == null) {
            end = rsList.size();
        }
        return rsList.subList(start - 1, end).toString();
    }

    @PostMapping("/rs")
    public boolean addRsEvent(@RequestBody RsEvent rsEvent) {
        if (rsEvent == null) {
            return false;
        }
        rsEventList.add(rsEvent);
        return true;
    }

    @PutMapping(value = "/rs")
    public boolean updateRsEvent(@RequestBody RsEvent rsEvent) {
        if (rsEvent == null) {
            return false;
        }
        rsEventList.forEach(o -> {
            if (o.getName().equals(rsEvent.getName())) {
                o.setKeyword(rsEvent.getKeyword());
            }
        });
        return true;
    }

    @GetMapping("/rs/listall")
    public List<RsEvent> GetRsEventList() {
        return rsEventList;
    }

    @DeleteMapping("/rs")
    public boolean deleteRsEvent(@RequestBody RsEvent rsEvent) {
        if (rsEvent == null) {
            return false;
        }
        rsEventList = rsEventList.stream().filter(o -> !o.getName().equals(rsEvent.getName())).collect(Collectors.toList());
        return true;
    }
}
