package com.networking.auction.protocol.response.room;

import java.util.ArrayList;
import java.util.List;

import com.networking.auction.models.UserLog;
import com.networking.auction.protocol.response.ListResponse;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetUserLogResponse extends ListResponse<UserLog> {
    public static GetUserLogResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid response string");
        }

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        int roomNumber = 0;
        List<UserLog> rooms = new ArrayList<UserLog>();

        while (!parts[roomNumber + 2].equals(ResponseUtil.END_TAG)) {
            UserLog log = UserLog.parseString(parts[roomNumber + 2]);
            rooms.add(log);
            roomNumber++;
        }

        if (respondCode != ResponseEnum.VIEW_USER_LOG_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return GetUserLogResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .lists(rooms)
                .build();
    }
}
