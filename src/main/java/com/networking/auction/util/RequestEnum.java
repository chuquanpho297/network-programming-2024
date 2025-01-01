package com.networking.auction.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestEnum {

    LOGIN_REQ(1),
    LOGOUT_REQ(2),
    REGISTER_REQ(3),
    VIEW_ROOMS_REQ(4),
    CREATE_ROOM_REQ(5),
    VIEW_ROOMS_OWNED_REQ(6),
    VIEW_ROOM_LOG_REQ(7),
    PLACE_ITEM_IN_ROOM_REQ(8),
    ACCEPT_REJECT_ITEM_REQ(9),
    VIEW_ITEMS_IN_ROOM_REQ(10),
    DELETE_ITEM_IN_ROOM_REQ(11),
    JOIN_ROOM_REQ(12),
    LEAVE_ROOM_REQ(13),
    VIEW_ALL_ITEMS_REQ(14),
    SEARCH_ITEM_REQ(15),
    CREATE_ITEM_REQ(16),
    PLACE_BID_REQ(17),
    BUY_NOW_REQ(18),
    DELETE_ITEM_REQ(19),
    UPDATE_ITEM_REQ(20),
    VIEW_USER_LOG_REQ(21),
    VIEW_OWNED_ITEMS_REQ(22);

    private final int request;

}