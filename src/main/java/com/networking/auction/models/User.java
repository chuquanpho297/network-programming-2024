package com.networking.auction.models;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer id;
    private String username;

    public static User parseString(String userString) {
        String[] parts = userString.split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid user string");
        }

        return User.builder()
                .id(Integer.parseInt(parts[0]))
                .username(parts[1])
                .build();
    }
}
