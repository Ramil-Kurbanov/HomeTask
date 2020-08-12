package com.example.HomeTask.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ROOM")
public class Room {
    @Id
    private String roomId;
    private String keyId;
    private boolean entrance;
    private long time;
}
