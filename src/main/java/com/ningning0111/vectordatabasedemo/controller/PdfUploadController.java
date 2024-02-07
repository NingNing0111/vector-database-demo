package com.ningning0111.vectordatabasedemo.controller;

import com.ningning0111.vectordatabasedemo.service.PdfStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @Project: com.ningning0111.vectordatabasedemo.controller
 * @Author: pgthinker
 * @GitHub: https://github.com/ningning0111
 * @Date: 2024/2/7 18:22
 * @Description:
 */
@Controller
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
public class PdfUploadController {
    private final PdfStoreService pdfStoreService;

    @PostMapping("/upload")
    public void upload(
            @RequestParam MultipartFile file
    ){
        try {
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件内容类型
            String contentType = file.getContentType();
            // 获取文件字节数组
            byte[] bytes = file.getBytes();
            // 创建一个临时文件
            Path tempFile = Files.createTempFile("temp-", fileName);
            // 将文件字节数组保存到临时文件
            Files.write(tempFile, bytes);
            // 创建一个 FileSystemResource 对象
            Resource fileResource = new FileSystemResource(tempFile.toFile());

            pdfStoreService.saveSource(fileResource);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
