package com.networking.auction.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class Room {
    private int roomId;
    private String roomName;
    private int ownerId;
    private int totalItems;
    private int totalParticipants;
    private String ownerName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static Room parseString(String string) {
        String[] parts = string.split(" ");

        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid room string");
        }

        return Room.builder()
                .roomId(Integer.parseInt(parts[0]))
                .roomName(parts[1].replace("%20", " "))
                .ownerId(Integer.parseInt(parts[2]))
                .ownerName(parts[3].replace("%20", " "))
                .totalItems(Integer.parseInt(parts[4]))
                .totalParticipants(Integer.parseInt(parts[5]))
                .startTime(LocalDateTime.parse(parts[6]))
                .endTime(LocalDateTime.parse(parts[7]))
                .build();
    }
}
