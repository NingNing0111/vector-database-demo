package com.ningning0111.vectordatabasedemo.controller;

import com.ningning0111.vectordatabasedemo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project: com.ningning0111.vectordatabasedemo.controller
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/2/7 16:50
 * @Description:
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/simple")
    public String simpleChat(
            @RequestParam String message
    ){
        return chatService.simpleChat(message);
    }

    @GetMapping("/")
    public String chat(
            @RequestParam String message
    ){
        return chatService.chatByVectorStore(message);
    }
}
