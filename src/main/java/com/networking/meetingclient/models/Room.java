package com.networking.meetingclient.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class Room {
    private String roomId;
    private String roomName;
    private String ownerId;
}
