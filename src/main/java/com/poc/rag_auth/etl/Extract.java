package com.poc.rag_auth.etl;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/extract")
@Log
@RequiredArgsConstructor
public class Extract {

    private final TransformAndLoad transformAndLoad;

    @PostMapping
    public List<Document> extract(@RequestParam MultipartFile file) throws IOException {

        // file name
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();

        log.info("File name: " + fileName + " Content type: " + contentType + " Size: " + size);

        Resource resource = new ByteArrayResource(file.getBytes());
        TikaDocumentReader documentReader = new TikaDocumentReader(resource);

        List<Document> documents = documentReader.get();

        List<Document> metaDocuments = documents.stream().map(document -> {
            HashedMap<String, Object> metadata = new HashedMap<>();
            metadata.put("fileName", fileName);
            metadata.put("contentType", contentType);
            metadata.put("size", size);
            metadata.put("userName", "Admin");
            metadata.put("userId", "admin");
            metadata.put("fileId", document.getId());
            metadata.put("roleAccess", "admin");

            return new Document(document.getId(), document.getContent(), metadata);
        }).collect(Collectors.toList());

        log.info("Documents extracted: " + metaDocuments.size());

        TextSplitter textSplitter = new TokenTextSplitter();

        String text = transformAndLoad.transform(metaDocuments);
        log.info(text);

        return textSplitter.apply(metaDocuments);
    }


    
}
