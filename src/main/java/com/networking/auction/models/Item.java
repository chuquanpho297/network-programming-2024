package com.networking.auction.models;

import java.time.LocalDateTime;
import java.util.Optional;

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
public class Item {
    private int itemId;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float currentPrice;
    private ItemStateEnum state;
    private float buyNowPrice;
    private int ownerId;
    private Optional<Integer> roomId;
    private String ownerName;
    private Optional<String> roomName;

    public enum ItemStateEnum {
        CREATED("created"),
        WAITING("waiting"),
        ACTIVE("active"),
        SOLD("sold");

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

        if (parts.length != 11) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return Item.builder()
                .itemId(Integer.parseInt(parts[0]))
                .name(parts[1].replace("%20", " "))
                .startTime(LocalDateTime.parse(parts[2]))
                .endTime(LocalDateTime.parse(parts[3]))
                .currentPrice(Float.parseFloat(parts[4]))
                .state(ItemStateEnum.valueOf(parts[5].toUpperCase()))
                .buyNowPrice(Float.parseFloat(parts[6]))
                .ownerId(Integer.parseInt(parts[7]))
                .roomId(parts[8].equals("NULL") ? Optional.empty() : Optional.of(Integer.parseInt(parts[8])))
                .roomName(parts[9].equals("NULL") ? Optional.empty() : Optional.of(parts[9].replace("%20", " ")))
                .ownerName(parts[10].replace("%20", " "))
                .build();
    }
}