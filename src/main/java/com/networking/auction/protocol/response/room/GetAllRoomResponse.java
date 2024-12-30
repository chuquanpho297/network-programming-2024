package com.networking.auction.protocol.response.room;

import java.util.ArrayList;
import java.util.List;

import com.networking.auction.models.Room;
import com.networking.auction.protocol.response.ListResponse;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetAllRoomResponse extends ListResponse<Room> {

    public static GetAllRoomResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid response string");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        int roomNumber = 0;
        List<Room> rooms = new ArrayList<Room>();

        while (!parts[roomNumber + 2].equals(ResponseUtil.END_TAG)) {
            Room room = Room.parseString(parts[roomNumber + 2]);
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