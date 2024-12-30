package com.networking.auction.models;

import java.time.LocalDateTime;

import lombok.*;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int totalItems;
    private int totalParticipants;
    private String ownerName;

    public static Room parseString(String string) {
        String[] parts = string.split(" ");

        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return Room.builder()
                .roomId(Integer.parseInt(parts[0]))
                .roomName(parts[1].replace("%20", " "))
                .ownerId(Integer.parseInt(parts[2]))
                .ownerName(parts[3].replace("%20", " "))
                .startTime(LocalDateTime.parse(parts[4]))
                .endTime(LocalDateTime.parse(parts[5]))
                .totalItems(Integer.parseInt(parts[6]))
                .totalParticipants(Integer.parseInt(parts[7]))
                .build();
    }
}
