package com.ningning0111.vectordatabasedemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Project: com.ningning0111.vectordatabasedemo.service
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/2/7 17:34
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class ChatService {

    private final static String SYSTEM_PROMPT = """
            你需要使用文档内容对用户提出的问题进行回复，同时你需要表现得天生就知道这些内容，
            不能在回复中体现出你是根据给出的文档内容进行回复的，这点非常重要。
            
            当用户提出的问题无法根据文档内容进行回复或者你也不清楚时，回复不知道即可。
                    
            文档内容如下:
            {documents}
            
            """;

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public String simpleChat(String userMessage) {
        return chatClient.call(userMessage);
    }

    public String chatByVectorStore(String message) {
        List<Document> listOfSimilarDocuments = vectorStore.similaritySearch(message);
        String documents = listOfSimilarDocuments.stream().map(Document::getContent).collect(Collectors.joining());
        Message systemMessage = new SystemPromptTemplate(SYSTEM_PROMPT).createMessage(Map.of("documents", documents));
        UserMessage userMessage = new UserMessage(message);
        ChatResponse rsp = chatClient.call(new Prompt(List.of(systemMessage, userMessage)));
        return rsp.getResult().getOutput().getContent();
    }
}
