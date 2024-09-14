package com.poc.rag_auth.etl;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transform")
public class TransformAndLoad {

    private final VectorStore vectorStore;

    public TransformAndLoad(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostMapping
    public String transform(List<Document> documents) {
        vectorStore.accept(documents);
        return "Documents transformed and loaded";
    }
    
}
