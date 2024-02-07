package com.ningning0111.vectordatabasedemo;

import com.ningning0111.vectordatabasedemo.service.ChatService;
import com.ningning0111.vectordatabasedemo.service.PdfStoreService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;

@SpringBootTest
class VectorDatabaseDemoApplicationTests {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private TokenTextSplitter tokenTextSplitter;

	@Autowired
	private VectorStore vectorStore;

	@Autowired
	private ChatService chatService;

	@Autowired
	private PdfStoreService pdfStoreService;



	@Test
	public void savePdfToVectorDatabase(){
		System.out.println("加载本地资源....");
		var pdfSource = resourceLoader.getResource("file:" + "doc/合工大24年软工实训_课题介绍及分组选题.pdf");
		var config = PdfDocumentReaderConfig.builder()
				.withPageExtractedTextFormatter(
						new ExtractedTextFormatter
								.Builder()
								.withNumberOfBottomTextLinesToDelete(3)
								.withNumberOfTopPagesToSkipBeforeDelete(1)
								.build()
				)
                .withPagesPerDocument(1)
                .build();
		var pagePdfDocumentReader = new PagePdfDocumentReader(pdfSource, config);
		vectorStore.accept(tokenTextSplitter.apply(pagePdfDocumentReader.get()));
	}

	@Test
	public void chatTest(){
		System.out.println(chatService.chatByVectorStore("项目中较难的项目有哪些？"));
	}

	@Test
	public void savePdfByPage(){
		String url = "file:doc/合工大24年软工实训_课题介绍及分组选题.pdf";
		pdfStoreService.saveSourceByPage(url);
	}

}
