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

    public static Room parseString(String string) {
        String[] parts = string.split(" ");

        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return Room.builder()
                .roomId(Integer.parseInt(parts[0]))
                .roomName(parts[1])
                .ownerId(Integer.parseInt(parts[2]))
                .startTime(LocalDateTime.parse(parts[3]))
                .endTime(LocalDateTime.parse(parts[4]))
                .build();
    }
}
