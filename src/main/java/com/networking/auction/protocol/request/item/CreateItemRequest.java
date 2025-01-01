package com.networking.auction.protocol.request.item;

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
    private float buyNowPrice;
    private int ownerId;

    @Builder
    public CreateItemRequest(String name, float currentPrice,
            ItemStateEnum state, float buyNowPrice) {
        this.name = name;
        this.buyNowPrice = buyNowPrice;
        this.ownerId = StateManager.getInstance().getUserId().orElseThrow();
    }

    @Override
    public int getRequestType() {
        return RequestEnum.CREATE_ITEM_REQ.getRequest();
    }

}
