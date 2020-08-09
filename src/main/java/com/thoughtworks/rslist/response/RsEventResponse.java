package com.thoughtworks.rslist.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RsEventResponse {
    private Integer id;
    private String eventName;
    private String keyword;
    private Integer voteNum;
}
