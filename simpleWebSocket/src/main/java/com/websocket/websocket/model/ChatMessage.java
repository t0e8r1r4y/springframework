package com.websocket.websocket.model;


import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}
