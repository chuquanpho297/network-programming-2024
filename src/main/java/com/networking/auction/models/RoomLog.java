package com.networking.auction.models;

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
    private Integer logId;
    private Integer itemId;
    private Integer roomId;
    private RoomLogStateEnum state;
    private String timestamp;

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
}