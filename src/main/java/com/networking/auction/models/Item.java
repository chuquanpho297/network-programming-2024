package com.networking.auction.models;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Builder
public class Item {
    private int itemId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float currentPrice;
    private ItemStateEnum state;
    private float buyNowPrice;
    private float bidIncrement;
    private int ownerId;
    private int roomId;
    private String ownerName;
    private String roomName;

    public enum ItemStateEnum {
        UNSOLD("unsold"),
        LISTED("listed"),
        ACTIVE("active"),
        SOLD("unsold");

        private final String state;

        ItemStateEnum(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return this.state;
        }
    }

    public static Item parseString(String string) {
        String[] parts = string.split(" ");

        if (parts.length != 12) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return Item.builder()
                .itemId(Integer.parseInt(parts[0]))
                .name(parts[1].replace("%20", " "))
                .startTime(LocalDateTime.parse(parts[2]))
                .endTime(LocalDateTime.parse(parts[3]))
                .currentPrice(Float.parseFloat(parts[4]))
                .state(ItemStateEnum.valueOf(parts[5]))
                .buyNowPrice(Float.parseFloat(parts[6]))
                .ownerId(Integer.parseInt(parts[8]))
                .roomId(Integer.parseInt(parts[9]))
                .ownerName(parts[10].replace("%20", " "))
                .bidIncrement(Float.parseFloat(parts[7]))
                .roomName(parts[11].replace("%20", " "))
                .build();
    }
}