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
public class RoomLog {
    private int logId;
    private int itemId;
    private String itemName;
    private int roomId;
    private RoomLogStateEnum state;
    private float buyNowPrice;
    private LocalDateTime timestamp;

    public enum RoomLogStateEnum {
        PENDING("pending"),
        ACCEPTED("accepted"),
        REJECTED("rejected");

        private final String state;

        RoomLogStateEnum(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return this.state;
        }
    }

    public static RoomLog parseString(String string) {
        String[] parts = string.split(" ");

        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return RoomLog.builder()
                .logId(Integer.parseInt(parts[0]))
                .itemId(Integer.parseInt(parts[1]))
                .itemName(parts[2].replace("%20", " "))
                .roomId(Integer.parseInt(parts[3]))
                .state(RoomLogStateEnum.valueOf(parts[4].toUpperCase()))
                .timestamp(LocalDateTime.parse(parts[6]))
                .buyNowPrice(Float.parseFloat(parts[5]))
                .build();
    }
}