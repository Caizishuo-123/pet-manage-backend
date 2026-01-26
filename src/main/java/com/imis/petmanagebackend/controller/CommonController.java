package com.imis.petmanagebackend.controller;

import com.imis.petmanagebackend.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common") // 对应前端的上传接口路径前缀
@Slf4j
public class CommonController {

    @Value("${file.upload-path}")
    private String uploadPath; // 从配置文件读取上传根目录

    /**
     * 通用上传接口
     * 
     * @param file 前端传来的文件对象
     * @param type 上传类型 (head: 头像, pet: 宠物图片)，默认存 pet
     * @return 文件的访问URL
     */
    @PostMapping("/upload")
    public Result<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        if (file.isEmpty()) {
            return Result.fail("上传文件不能为空");
        }

        try {
            // 1. 获取原始文件名和后缀
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 2. 生成新文件名 (UUID防止冲突)
            String newFileName = UUID.randomUUID().toString() + suffix;

            // 3. 决定保存的子目录
            String subDir;
            if ("head".equals(type)) {
                subDir = "/img/head/";
            } else {
                // 默认都存到 pet 目录，或者您可以加更多类型
                subDir = "/img/pet/";
            }

            File destDir = new File(uploadPath + subDir);

            // 如果目录不存在，自动创建
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // 4. 保存文件到磁盘
            File destFile = new File(destDir, newFileName);
            file.transferTo(destFile);

            // 5. 返回访问路径 (Web映射路径)
            // 返回格式: /img/pet/uuid.jpg
            String accessUrl = subDir + newFileName;

            log.info("文件上传成功，保存路径: {}, 访问URL: {}", destFile.getAbsolutePath(), accessUrl);

            return Result.success(accessUrl);

        } catch (IOException e) {
            log.error("文件上传失败", e);
            return Result.fail("文件上传失败: " + e.getMessage());
        }
    }
}