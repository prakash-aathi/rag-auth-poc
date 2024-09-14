package com.poc.rag_auth;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class RagAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(RagAuthApplication.class, args);
	}

	@Value("${app.resource}")
    private Resource documentResource;

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }


    // @Bean
    // ApplicationRunner applicationRunner(VectorStore vectorStore) {
    //     System.out.println("ApplicationRunner called");
    //     return args -> {
    //         TikaDocumentReader documentReader = new TikaDocumentReader(documentResource);
    //         System.out.println("DocumentReader created" + documentReader.METADATA_SOURCE);
    //         TextSplitter textSplitter = new TokenTextSplitter();
    //         System.out.println("TextSplitter created" + textSplitter.hashCode());
    //         vectorStore.accept(
    //             textSplitter.apply(
    //                 documentReader.get()));
    //     };
    // }

}
