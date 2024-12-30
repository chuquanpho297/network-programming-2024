package com.networking.auction.protocol.request.item;

import com.networking.auction.protocol.request.Request;
import com.networking.auction.util.RequestEnum;

public class GetAllItemRequest extends Request {

    @Override
    public int getRequestType() {
        return RequestEnum.VIEW_ALL_ITEMS_REQ.getRequest();
    }
}
