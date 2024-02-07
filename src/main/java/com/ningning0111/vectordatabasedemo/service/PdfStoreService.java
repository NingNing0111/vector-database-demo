package com.ningning0111.vectordatabasedemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @Project: com.ningning0111.vectordatabasedemo.service
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/2/7 16:48
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class PdfStoreService {
    private final DefaultResourceLoader resourceLoader;
    private final VectorStore vectorStore;
    private final TokenTextSplitter tokenTextSplitter;
    public void saveSourceByPage(String url){
        Resource resource = resourceLoader.getResource(url);
        PdfDocumentReaderConfig loadConfig = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(
                        new ExtractedTextFormatter
                                .Builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build()
                )
                .withPagesPerDocument(1)
                .build();
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(resource, loadConfig);
        vectorStore.accept(tokenTextSplitter.apply(pagePdfDocumentReader.get()));
    }

    public void saveSourceByParagraph(String url){
        Resource resource = resourceLoader.getResource(url);
        ParagraphPdfDocumentReader pdfReader = new ParagraphPdfDocumentReader(
                resource,
                PdfDocumentReaderConfig.builder().build()
        );
        vectorStore.accept(tokenTextSplitter.apply(pdfReader.get()));
    }

    public void saveSource(Resource resource){
        PdfDocumentReaderConfig loadConfig = PdfDocumentReaderConfig.builder()
                .withPageExtractedTextFormatter(
                        new ExtractedTextFormatter
                                .Builder()
                                .withNumberOfBottomTextLinesToDelete(3)
                                .withNumberOfTopPagesToSkipBeforeDelete(1)
                                .build()
                )
                .withPagesPerDocument(1)
                .build();
        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(resource, loadConfig);
        vectorStore.accept(tokenTextSplitter.apply(pagePdfDocumentReader.get()));
    }
}
