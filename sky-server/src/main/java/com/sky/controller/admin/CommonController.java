package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "公共通用接口")
public class CommonController {

	@Autowired
	AliOssUtil aliOssUtil;

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	@ApiOperation(value = "文件上传")
	Result<String> upload(MultipartFile file) {
		log.info("文件上传，{}", file);

		try {
			String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			String fileName = UUID.randomUUID() + extName;

			String url = aliOssUtil.upload(file.getBytes(), fileName);

			return Result.success(url);
		} catch (Exception e) {
			log.error("文件上传失败，{}", e);
		}

		return Result.error(MessageConstant.UPLOAD_FAILED);
	}
}
