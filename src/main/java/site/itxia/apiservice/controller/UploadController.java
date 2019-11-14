package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.enumable.ErrorCode;
import site.itxia.apiservice.service.UploadService;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zhenxi
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("")
    public ResultWrapper uploadFile(@RequestParam(name = "file") MultipartFile remoteFile,
                                    @RequestHeader(required = false) Integer memberID) {
        var dto = uploadService.uploadFile(remoteFile, memberID);
        return ResultWrapper.wrapSuccess(dto);
    }

    @GetMapping("/{sha256sum:\\w{64}}")
    public byte[] getFile(@PathVariable String sha256sum, HttpServletResponse response) {
        response.setHeader("content-disposition", "attachment;filename=" + uploadService.getUploadFileName(sha256sum));
        return uploadService.getFile(sha256sum);
    }

    @GetMapping("/{sha256sum:\\w{64}}/thumbnail")
    public byte[] getFileThumbnail(@PathVariable String sha256sum, HttpServletResponse response) {
        response.setHeader("content-disposition", "attachment;filename=" + uploadService.getUploadFileName(sha256sum));
        return uploadService.getThumbnailFile(sha256sum);
    }

    @GetMapping("/{sha256sum:\\w{64}}/info")
    public ResultWrapper getFileInfo(@PathVariable String sha256sum) {
        var dto = uploadService.getUploadFileDto(sha256sum);
        if (dto == null) {
            return ResultWrapper.wrap(ErrorCode.FILE_NOT_FOUND);
        }
        return ResultWrapper.wrapSuccess(dto);
    }

}
