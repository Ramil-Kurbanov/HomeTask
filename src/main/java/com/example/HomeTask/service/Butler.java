package com.example.HomeTask.service;

import com.example.HomeTask.entity.Room;
import org.springframework.http.ResponseEntity;

public interface Butler {
    ResponseEntity openTheDoor(Room room);
}
