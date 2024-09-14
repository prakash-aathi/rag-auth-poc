package com.poc.rag_auth;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

@RestController
@RequestMapping("/ask")
public class AskController {

//     private final ChatClient aiClient;
//     private final VectorStore vectorStore;

//     @Value("classpath:/rag-prompt-template.st")
//     private Resource ragPromptTemplate;

//     public AskController(ChatClient aiClient, VectorStore vectorStore) {
//         this.aiClient = aiClient;
//         this.vectorStore = vectorStore;
//     }

//     public static record Question(String question) {
//     }
//     public static record Answer(String answer) {
//     }

//     @PostMapping
//     public Answer ask(@RequestBody Question question) {
//         List<Document> similarDocuments = vectorStore
//                 .similaritySearch(SearchRequest.defaults()
//                         .query(question.question()));
//         List<String> contentList = similarDocuments.stream()
//                 .map(Document::getContent)
//                 .toList();

//         String answer = aiClient.prompt()
//                 .user(userSpec -> userSpec
//                         .text(ragPromptTemplate)
//                         .param("input", question.question())
//                         .param("documents", String.join("\n", contentList)))
//                 .call()
//                 .content();

//         return new Answer(answer);
//     }
    
    // private ChatClient chatClient;

    // public AskController(ChatClient.Builder chatClient, VectorStore vectorStore) {
    //     this.chatClient = chatClient
    //             .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()))
    //             .build();
    // }
    
    // @PostMapping
    // public String ask(@RequestBody String question) {
    //     return chatClient.prompt()
    //             .user(question)
    //             .call()
    //             .content();
    // }
}