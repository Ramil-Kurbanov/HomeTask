package com.example.HomeTask.configuration;

import com.example.HomeTask.repository.RoomRepository;
import com.example.HomeTask.service.Butler;
import com.example.HomeTask.service.ButlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ButlerConfig {
    @Bean
    public Butler butlerBean(RoomRepository roomRepository){
        return new ButlerImpl(roomRepository);
    }
}
