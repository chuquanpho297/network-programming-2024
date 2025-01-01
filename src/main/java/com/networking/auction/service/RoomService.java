package com.networking.auction.service;

import java.time.LocalDateTime;
import java.util.Objects;

import com.networking.auction.protocol.request.room.CreateRoomRequest;
import com.networking.auction.protocol.request.room.GetAllOwnedRoomRequest;
import com.networking.auction.protocol.request.room.GetAllRoomRequest;
import com.networking.auction.protocol.request.room.JoinRoomRequest;
import com.networking.auction.protocol.response.room.CreateRoomResponse;
import com.networking.auction.protocol.response.room.GetAllOwnedRoomResponse;
import com.networking.auction.protocol.response.room.GetAllRoomResponse;
import com.networking.auction.protocol.response.room.JoinRoomResponse;
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
}
