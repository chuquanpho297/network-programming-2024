package com.networking.auction.protocol.request.item;

import java.time.LocalDateTime;

import com.networking.auction.StateManager;
import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateItemRequest extends Request {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float buyNowPrice;
    private float bidIncrement;
    private int ownerId;

    @Builder
    public CreateItemRequest(String name, LocalDateTime startTime, LocalDateTime endTime, float currentPrice,
            ItemStateEnum state, float buyNowPrice, float bidIncrement) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.buyNowPrice = buyNowPrice;
        this.bidIncrement = bidIncrement;
        this.ownerId = StateManager.getInstance().getUserId().orElse(-1);
    }

    @Override
    public int getRequestType() {
        return RequestEnum.CREATE_ITEM_REQ.getRequest();
    }

}