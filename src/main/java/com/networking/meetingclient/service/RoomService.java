package com.networking.meetingclient.service;

import java.util.Objects;

import com.networking.meetingclient.StateManager;
import com.networking.meetingclient.protocol.request.room.CreateRoomRequest;
import com.networking.meetingclient.protocol.request.room.GetAllOwnedRoomRequest;
import com.networking.meetingclient.protocol.request.room.GetAllRoomRequest;
import com.networking.meetingclient.protocol.response.CreateRoomResponse;
import com.networking.meetingclient.protocol.response.GetAllOwnedRoomResponse;
import com.networking.meetingclient.protocol.response.GetAllRoomResponse;

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
            String rawResponse = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
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
            String rawResponse = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            GetAllOwnedRoomResponse response = GetAllOwnedRoomResponse.parseResponse(rawResponse);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreateRoomResponse createRoom(String roomName) {
        try {
            CreateRoomRequest request = CreateRoomRequest.builder()
                    .roomName(roomName)
                    .build();
            String receiveMess = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            CreateRoomResponse response = CreateRoomResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public CreateRoomResponse createRoom(String roomName, int ownerId) {
        try {
            CreateRoomRequest request = CreateRoomRequest.builder()
                    .roomName(roomName)
                    .build();
            String receiveMess = StateManager.getInstance().getClientSocket().sendAndReceive(request.toString());
            CreateRoomResponse response = CreateRoomResponse.parseResponse(receiveMess);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
