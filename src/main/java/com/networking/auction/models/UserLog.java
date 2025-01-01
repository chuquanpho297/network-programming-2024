package com.networking.auction.models;

import java.time.LocalDateTime;

import com.networking.auction.models.RoomLog.RoomLogStateEnum;

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
public class UserLog {

    private int logId;
    private int userId;
    private int itemId;
    private int roomId;
    private String userName;
    private double bidPrice;
    private LocalDateTime time;
    private String status;

    public static UserLog parseString(String string) {
        String[] parts = string.split(" ");

        if (parts.length != 8) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return UserLog.builder()
                .logId(Integer.parseInt(parts[0]))
                .userId(Integer.parseInt(parts[1]))
                .itemId(Integer.parseInt(parts[2]))
                .roomId(Integer.parseInt(parts[3]))
                .userName(parts[4].replace("%20", " "))
                .bidPrice(Double.parseDouble(parts[5]))
                .time(LocalDateTime.parse(parts[6]))
                .status(parts[7])
                .build();
    }
}