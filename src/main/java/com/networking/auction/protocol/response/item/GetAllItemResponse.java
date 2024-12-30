package com.networking.auction.protocol.response.item;

import java.util.ArrayList;
import java.util.List;

import com.networking.auction.models.Item;
import com.networking.auction.protocol.response.ListResponse;
import com.networking.auction.util.ResponseEnum;
import com.networking.auction.util.ResponseUtil;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GetAllItemResponse extends ListResponse<Item> {
    public static GetAllItemResponse parseResponse(String response) {
        String[] parts = ResponseUtil.separateResponse(response);

        int respondCode = Integer.parseInt(parts[0]);
        int statusCode = Integer.parseInt(parts[1]);

        int itemNumber = 0;
        List<Item> items = new ArrayList<Item>();

        while (!parts[itemNumber + 2].equals(ResponseUtil.END_TAG)) {
            Item room = Item.parseString(parts[itemNumber + 2]);
            items.add(room);
            itemNumber++;
        }

        if (respondCode != ResponseEnum.VIEW_ALL_ITEMS_RES.getResponse()) {
            throw new IllegalArgumentException("Invalid response code");
        }

        return GetAllItemResponse.builder()
                .respondCode(respondCode)
                .statusCode(statusCode)
                .lists(items)
                .build();
    }
}
