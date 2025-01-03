package com.networking.auction.service;

import java.time.LocalDateTime;
import java.util.Objects;

import com.networking.auction.protocol.request.item.GetAllItemInRoomRequest;
import com.networking.auction.protocol.request.room.AcceptRejectItemRequest;
import com.networking.auction.protocol.request.room.CreateRoomRequest;
import com.networking.auction.protocol.request.room.DeleteItemInRoomRequest;
import com.networking.auction.protocol.request.room.GetAllOwnedRoomRequest;
import com.networking.auction.protocol.request.room.GetAllRoomRequest;
import com.networking.auction.protocol.request.room.GetRoomLogRequest;
import com.networking.auction.protocol.request.room.GetUserLogRequest;
import com.networking.auction.protocol.request.room.JoinRoomRequest;
import com.networking.auction.protocol.request.room.LeaveRoomRequest;
import com.networking.auction.protocol.request.room.PlaceItemInRoomRequest;
import com.networking.auction.protocol.response.item.GetAllItemInRoomResponse;
import com.networking.auction.protocol.response.room.AcceptRejectItemResponse;
import com.networking.auction.protocol.response.room.CreateRoomResponse;
import com.networking.auction.protocol.response.room.DeleteItemInRoomResponse;
import com.networking.auction.protocol.response.room.GetAllOwnedRoomResponse;
import com.networking.auction.protocol.response.room.GetAllRoomResponse;
import com.networking.auction.protocol.response.room.GetRoomLogResponse;
import com.networking.auction.protocol.response.room.GetUserLogResponse;
import com.networking.auction.protocol.response.room.JoinRoomResponse;
import com.networking.auction.protocol.response.room.LeaveRoomResponse;
import com.networking.auction.protocol.response.room.PlaceItemInRoomResponse;
import com.networking.auction.socket.TCPClient;

public class RoomService {
    private static RoomService instance;

    public static RoomService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new RoomService();
        }
        return instance;
    }

    public GetAllRoomResponse getAllRooms() {
        try {
            GetAllRoomRequest request = new GetAllRoomRequest();
            String rawResponse = TCPClient.fetchServer(request.toString());
            GetAllRoomResponse response = GetAllRoomResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetAllOwnedRoomResponse getAllOwnedRooms() {
        try {
            GetAllOwnedRoomRequest request = new GetAllOwnedRoomRequest();
            String rawResponse = TCPClient.fetchServer(request.toString());
            GetAllOwnedRoomResponse response = GetAllOwnedRoomResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreateRoomResponse createRoom(String roomName, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            CreateRoomRequest request = CreateRoomRequest.builder()
                    .roomName(roomName)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            String receiveMess = TCPClient.fetchServer(request.toString());
            CreateRoomResponse response = CreateRoomResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JoinRoomResponse joinRoom(int roomId) {
        try {
            JoinRoomRequest request = JoinRoomRequest.builder()
                    .roomId(roomId)
                    .build();
            String receiveMess = TCPClient.fetchServer(request.toString());
            JoinRoomResponse response = JoinRoomResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GetAllItemInRoomResponse getAllItemInRoomResponse(int roomId) {
        try {
            GetAllItemInRoomRequest request = GetAllItemInRoomRequest.builder()
                    .roomId(roomId)
                    .build();
            String rawResponse = TCPClient.fetchServer(request.toString());
            GetAllItemInRoomResponse response = GetAllItemInRoomResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PlaceItemInRoomResponse placeItemInRoom(int roomId, int itemId) {
        try {
            PlaceItemInRoomRequest request = PlaceItemInRoomRequest.builder()
                    .roomId(roomId)
                    .itemId(itemId)
                    .build();
            String receiveMess = TCPClient.fetchServer(request.toString());
            PlaceItemInRoomResponse response = PlaceItemInRoomResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public AcceptRejectItemResponse acceptRejectItem(int roomId, int itemId, boolean isAccept) {
        try {
            AcceptRejectItemRequest request = AcceptRejectItemRequest.builder()
                    .roomId(roomId)
                    .itemId(itemId)
                    .confirmCode(isAccept ? 1 : 0)
                    .build();
            String receiveMess = TCPClient.fetchServer(request.toString());
            AcceptRejectItemResponse response = AcceptRejectItemResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GetRoomLogResponse getRoomLog(int roomId) {
        try {
            GetRoomLogRequest request = GetRoomLogRequest.builder()
                    .roomId(roomId)
                    .build();
            String rawResponse = TCPClient.fetchServer(request.toString());
            GetRoomLogResponse response = GetRoomLogResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public LeaveRoomResponse leaveRoom(int roomId) {
        try {
            LeaveRoomRequest request = LeaveRoomRequest.builder()
                    .roomId(roomId)
                    .build();
            String rawResponse = TCPClient.fetchServer(request.toString());
            LeaveRoomResponse response = LeaveRoomResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public DeleteItemInRoomResponse deleteItemInRoom(int roomId, int itemId) {
        try {
            DeleteItemInRoomRequest request = DeleteItemInRoomRequest.builder()
                    .roomId(roomId)
                    .itemId(itemId)
                    .build();
            String rawResponse = TCPClient.fetchServer(request.toString());
            DeleteItemInRoomResponse response = DeleteItemInRoomResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public GetUserLogResponse getUserLog(int roomId, int itemId) {
        try {
            GetUserLogRequest request = GetUserLogRequest.builder()
                    .roomId(roomId)
                    .itemId(itemId)
                    .build();
            String rawResponse = TCPClient.fetchServer(request.toString());
            GetUserLogResponse response = GetUserLogResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
