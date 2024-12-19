package com.networking.meetingclient.protocol.response;

import java.util.ArrayList;
import java.util.List;

import com.networking.meetingclient.models.Room;
import com.networking.meetingclient.util.ResponseEnum;
import com.networking.meetingclient.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetAllRoomResponse extends ListResponse<Room> {

    public static GetAllRoomResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        int roomNumber = 0;
        List<Room> rooms = new ArrayList<Room>();

        while (!parts[roomNumber + 2].equals(ResponseUtil.END_TAG)) {
            String[] roomParts = parts[roomNumber + 2].split(" ");

            Room room = Room.builder()
                    .roomId(roomParts[0])
                    .roomName(roomParts[1])
                    .ownerId(roomParts[2])
                    .build();
            rooms.add(room);
            roomNumber++;
        }

        if (respondCode != ResponseEnum.VIEW_ROOMS_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return GetAllRoomResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .lists(rooms)
                .build();
    }
}