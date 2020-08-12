package com.example.HomeTask.service;

import com.example.HomeTask.entity.Room;
import com.example.HomeTask.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.HomeTask.service.StatusRoom.*;

@Slf4j
public class ButlerImpl implements Butler {
    private final RoomRepository roomRepository;
    private boolean keyUse;
    private Room roomFromDB;
    private ResponseEntity responseEntity;

    public ButlerImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public ResponseEntity openTheDoor(Room room) {
        boolean exists = roomRepository.existsById(room.getRoomId());
        Iterable<Room> roomsDB = roomRepository.findAll();
        responseEntity = new ResponseEntity(KEY_OR_ROOM_IS_NOT_CORRECT, HttpStatus.INTERNAL_SERVER_ERROR);
        if (Integer.parseInt(room.getRoomId()) < 6 && Integer.parseInt(room.getKeyId()) < 1001) {
            if (!exists) {
                log.info(CHECKING_THE_KEY);
                roomsDB.forEach(element -> statusKey(element, room));
            }
            if (!exists && !keyUse && room.isEntrance() && allowedRooms(room)) {
                log.info(KEY_IS_NOT_USED_ENTRANCE);
                roomRepository.save(room);
                responseEntity = new ResponseEntity(ENTERED_THE_ROOM, HttpStatus.OK);
            }
            if (!exists && !keyUse && !room.isEntrance() && allowedRooms(room)) {
                log.info(KEY_IS_NOT_USED_YOU_NEED_TO_LOG_IN);
                roomRepository.save(room);
                responseEntity = new ResponseEntity(NEED_TO_ENTER_THE_ROOM, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!exists && keyUse) {
                log.warn(KEY_IS_USED);
                responseEntity = new ResponseEntity(ENTERED_THE_OTHER_ROOM, HttpStatus.FORBIDDEN);
            }
            if (exists) {
                log.info(GETTING_A_ROOM);
                roomFromDB = roomRepository.findById(room.getRoomId()).get();
            }
            if (exists && !roomFromDB.getKeyId().equals(room.getKeyId()) && roomFromDB.isEntrance()) {
                log.info(BUSY_WITH_SOMEONE);
                responseEntity = new ResponseEntity(BUSY_WITH_SOMEONE, HttpStatus.FORBIDDEN);
            }
            if (exists && roomFromDB.getKeyId().equals(room.getKeyId())) {
                log.info(CHANGE_THE_STATE);
                roomRepository.save(room);
                responseEntity = new ResponseEntity(CHANGE_THE_STATE, HttpStatus.OK);
            }
            if (exists && !roomFromDB.getKeyId().equals(room.getKeyId()) && !roomFromDB.isEntrance() && allowedRooms(room)) {
                log.info(ENTERED_THE_ROOM);
                roomRepository.save(room);
                responseEntity = new ResponseEntity(ENTERED_THE_ROOM, HttpStatus.OK);
            }
        }
        return responseEntity;
    }

    private void statusKey(Room roomDB, Room room) {
        if (roomDB.getKeyId().equals(room.getKeyId()) && roomDB.isEntrance()) {
            keyUse = true;
            return;
        }
        keyUse = false;
    }

    private boolean allowedRooms(Room room) {
        return Integer.parseInt(room.getKeyId()) % Integer.parseInt(room.getRoomId()) == 0;
    }
}
