package com.poc.rag_auth.etl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.java.Log;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@Log
public class Chat {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public Chat(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public static record Question(String question) {
    }

    public static record Answer(String answer) {
    }

    private String prompt = """
                    You are a helpful assistant, conversing with a user about the subjects contained in a set of documents.
            Use the information from the DOCUMENTS section to provide accurate answers. If unsure or if the answer
            isn't found in the DOCUMENTS section, simply state that you don't know the answer.

                    QUESTION:
                    {input}

                    DOCUMENTS:
                    {documents}

                    """;

    @PostMapping
    public Answer ask(@RequestBody Question question) {

        List<Document> similarDocuments = vectorStore
                .similaritySearch(
                        SearchRequest.query(question.question())
                                .withTopK(1));

        log.info("Similar documents: " + similarDocuments.size());
        similarDocuments.stream().forEach(document -> 
            log.info("Document: " + document.getId() + " " + document.getContent() + " doc metadata: "
                    + document.getMetadata())
        );

        String context = similarDocuments.stream()
                .map(Document::getContent).collect(Collectors.joining("\n"));

        Map<String, Object> promptsParameters = new HashMap<>();
        promptsParameters.put("input", question);
        promptsParameters.put("documents", context);

        String answer = chatClient
                .prompt()
                .user(userSpec -> userSpec
                        .text(prompt)
                        .params(promptsParameters))
                .call()
                .content();

        return new Answer(answer);
    }

}
