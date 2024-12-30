package com.networking.auction.protocol.request.item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchItemRequest extends Request {
    private Optional<String> itemName;
    private Optional<LocalDateTime> startTime;
    private Optional<LocalDateTime> endTime;
    private Optional<Integer> roomId;
    private Optional<Integer> userId;
    private Optional<List<ItemStateEnum>> states;

    @Builder
    public SearchItemRequest(Optional<LocalDateTime> startTime, Optional<LocalDateTime> endTime,
            Optional<Integer> roomId, Optional<Integer> userId, Optional<String> itemName,
            Optional<List<ItemStateEnum>> states) {
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomId = roomId;
        this.itemName = itemName;
        this.states = states;
    }

    @Override
    public int getRequestType() {
        return RequestEnum.SEARCH_ITEM_REQ.getRequest();
    }
}
