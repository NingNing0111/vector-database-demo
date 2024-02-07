package com.ningning0111.vectordatabasedemo.controller;

import com.ningning0111.vectordatabasedemo.service.PdfStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


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
        pdfStoreService.saveSource(file);
    }
}
