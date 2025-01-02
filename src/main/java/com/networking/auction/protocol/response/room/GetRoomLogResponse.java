package com.networking.auction.protocol.response.room;

import java.util.ArrayList;
import java.util.List;

import com.networking.auction.models.RoomLog;
import com.networking.auction.protocol.response.ListResponse;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class GetRoomLogResponse extends ListResponse<RoomLog> {

    public static GetRoomLogResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid response string");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        int roomNumber = 0;
        List<RoomLog> roomLogs = new ArrayList<RoomLog>();

        while (!parts[roomNumber + 2].equals(ResponseUtil.END_TAG)) {
            RoomLog roomLog = RoomLog.parseString(parts[roomNumber + 2]);
            roomLogs.add(roomLog);
            roomNumber++;
        }
        if (respondCode != ResponseEnum.VIEW_ROOM_LOG_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return GetRoomLogResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .lists(roomLogs)
                .build();

    }

}
