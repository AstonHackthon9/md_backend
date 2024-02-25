package com.example.md_backend.websocket;

import com.example.md_backend.entity.Room;
import com.example.md_backend.entity.User;
import com.example.md_backend.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    RoomRepository repository;


    private Map<String, Map<UUID, WebSocketSession>> rooms = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        String roomId = null;
        // Iterate over rooms to find the room containing the session
        for (Map.Entry<String, Map<UUID, WebSocketSession>> entry : rooms.entrySet()) {
            if (entry.getValue().containsValue(session)) {
                roomId = entry.getKey();
                break;
            }
        }

        if (roomId != null) {
            Map<UUID, WebSocketSession> roomSessions = rooms.get(roomId);
            if (roomSessions != null) {
                for (WebSocketSession roomSession : roomSessions.values()) {
                    if (roomSession.isOpen() && !session.getId().equals(roomSession.getId())) {
                        System.out.println(message);
                        roomSession.sendMessage(message);
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getRemoteAddress());

        String[] uriSegments = session.getUri().getPath().split("/");
        String roomId = uriSegments[uriSegments.length - 2];
        String userId = uriSegments[uriSegments.length - 1];

        Room room = repository.findByRoomID(UUID.fromString(roomId));

        if (room != null) {
            boolean userFound = false;
            List<User> users = room.getUsers();
            for (User user : users) {
                if (user.getUserId().equals(UUID.fromString(userId))) {
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                rooms.computeIfAbsent(roomId, k -> new HashMap<>()).put(UUID.fromString(userId), session);
            } else {
                System.out.println("User is not part of the room.");
            }
        } else {
            System.out.println("Room does not exist.");
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String[] uriSegments = session.getUri().getPath().split("/");
        String roomId = uriSegments[uriSegments.length - 2]; // Last segment is the room ID

        // Check if the room exists in the map before removing the session
        Map<UUID, WebSocketSession> roomSessions = rooms.get(roomId);
        if (roomSessions != null) {
            roomSessions.remove(session);
        }
    }


}
