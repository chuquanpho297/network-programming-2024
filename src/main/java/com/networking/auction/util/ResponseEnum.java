package com.networking.auction.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    LOGIN_RES(1),
    LOGOUT_RES(2),
    REGISTER_RES(3),
    VIEW_ROOMS_RES(4),
    CREATE_ROOM_RES(5),
    VIEW_ROOMS_OWNED_RES(6),
    VIEW_ROOM_LOG_RES(7),
    PLACE_ITEM_IN_ROOM_RES(8),
    ACCEPT_REJECT_ITEM_RES(9),
    VIEW_ITEMS_IN_ROOM_RES(10),
    DELETE_ITEM_IN_ROOM_RES(11),
    JOIN_ROOM_RES(12),
    LEAVE_ROOM_RES(13),
    VIEW_ALL_ITEMS_RES(14),
    SEARCH_ITEM_BY_NAME_RES(15),
    SEARCH_ITEM_BY_DURATION_RES(16),
    PLACE_BID_RES(17),
    BUY_NOW_RES(18),
    DELETE_ITEM_FROM_ROOM_RES(19),
    DELETE_ITEM_RES(20),
    UPDATE_ITEM_RES(21),
    VIEW_USER_LOG_RES(22);

    private final int response;
}