package com.networking.auction.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.networking.auction.StateManager;
import com.networking.auction.models.Item.ItemStateEnum;
import com.networking.auction.protocol.request.item.CreateItemRequest;
import com.networking.auction.protocol.request.item.GetAllItemInRoomRequest;
import com.networking.auction.protocol.request.item.GetAllItemRequest;
import com.networking.auction.protocol.request.item.GetAllOwnedItemRequest;
import com.networking.auction.protocol.request.item.SearchItemRequest;
import com.networking.auction.protocol.response.item.CreateItemResponse;
import com.networking.auction.protocol.response.item.GetAllItemInRoomResponse;
import com.networking.auction.protocol.response.item.GetAllItemResponse;
import com.networking.auction.protocol.response.item.GetAllOwnedItemResponse;
import com.networking.auction.protocol.response.item.SearchItemResponse;

public class ItemService {
    private static ItemService instance;

    public static ItemService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ItemService();
        }
        return instance;
    }

    public GetAllItemResponse getAllItem() {
        try {
            GetAllItemRequest request = new GetAllItemRequest();
            String rawResponse = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            GetAllItemResponse response = GetAllItemResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetAllOwnedItemResponse getAllOwnedItems() {
        try {
            GetAllOwnedItemRequest request = new GetAllOwnedItemRequest();
            String rawResponse = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            GetAllOwnedItemResponse response = GetAllOwnedItemResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetAllItemInRoomResponse getAllItemInRoom(int roomId) {
        try {
            GetAllItemInRoomRequest request = GetAllItemInRoomRequest.builder()
                    .roomId(roomId)
                    .build();
            String receiveMess = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            GetAllItemInRoomResponse response = GetAllItemInRoomResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SearchItemResponse searchItem(Optional<String> itemName, Optional<LocalDateTime> startTime,
            Optional<LocalDateTime> endTime,
            Optional<Integer> roomId, Optional<Integer> userId, Optional<List<ItemStateEnum>> states) {
        try {
            SearchItemRequest request = SearchItemRequest.builder()
                    .itemName(itemName)
                    .startTime(startTime)
                    .endTime(endTime)
                    .roomId(roomId)
                    .userId(userId)
                    .states(states)
                    .build();
            String receiveMess = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            SearchItemResponse response = SearchItemResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreateItemResponse createItem(String name,
            float buyNowPrice, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            CreateItemRequest request = CreateItemRequest.builder()
                    .name(name)
                    .buyNowPrice(buyNowPrice)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            String receiveMess = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            CreateItemResponse response = CreateItemResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
