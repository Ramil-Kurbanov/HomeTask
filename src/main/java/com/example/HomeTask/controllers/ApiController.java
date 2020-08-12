package com.example.HomeTask.controllers;

import com.example.HomeTask.entity.Room;
import com.example.HomeTask.service.Butler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/check")
@RequiredArgsConstructor
public class ApiController extends Date {
    private final Butler butler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity check(@RequestParam String roomId, boolean entrance, String keyId) {
        log.info("Получение аргуметов: номер комнаты {}, войти {}, ключь {}", roomId, entrance, keyId);
        ResponseEntity responseEntity = butler.openTheDoor(Room.builder()
                .keyId(keyId)
                .entrance(entrance)
                .roomId(roomId)
                .time(new Date().getTime())
                .build());
         return responseEntity;
    }

}
