package com.thoughtworks.rslist.domain;

import com.thoughtworks.rslist.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {
    private Integer id;

    @NotBlank(message = "eventName is null")
    private Integer voteNum;
    @NotBlank(message = "keyword is null")
    private LocalDateTime voteTime;
    private UserEntity user;

}
