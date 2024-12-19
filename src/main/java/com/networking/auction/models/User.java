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
}
