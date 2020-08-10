package com.thoughtworks.rslist.config;

import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.RsEventService;
import com.thoughtworks.rslist.service.UserService;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Rsconfig {
    @Bean
    public RsEventService rsEventService(RsEventRepository rsEventRepository,
                                         UserRepository userRepository,
                                         VoteRepository voteRepository) {
        return new RsEventService(rsEventRepository, userRepository, voteRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public VoteService voteService(VoteRepository voteRepository) {
        return new VoteService(voteRepository);
    }
}
