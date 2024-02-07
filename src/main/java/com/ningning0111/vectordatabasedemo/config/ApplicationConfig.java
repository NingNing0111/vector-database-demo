package com.ningning0111.vectordatabasedemo.config;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Project: com.ningning0111.vectordatabasedemo.config
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/2/7 16:42
 * @Description:
 */
@Configuration
public class ApplicationConfig {

    /**
     * 向量数据库进行检索操作
     * @param embeddingClient
     * @param jdbcTemplate
     * @return
     */
    @Bean
    public VectorStore vectorStore(EmbeddingClient embeddingClient, JdbcTemplate jdbcTemplate){
        return new PgVectorStore(jdbcTemplate,embeddingClient);
    }

    /**
     * 文本分割器
     * @return
     */
    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }


}
