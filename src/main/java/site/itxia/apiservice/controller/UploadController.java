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
        int id = uploadService.uploadFile(remoteFile, memberID);
        return ResultWrapper.wrapSuccess(uploadService.getUploadFileInfo(id));
    }

    @GetMapping("/{id:\\d+}")
    public byte[] getFile(@PathVariable int id, HttpServletResponse response) {
        response.setHeader("content-disposition", "attachment;filename=" + uploadService.getUploadFileName(id));
        return uploadService.getFile(id);
    }

    @GetMapping("/{id:\\d+}/info")
    public ResultWrapper getFileInfo(@PathVariable int id) {
        var dto = uploadService.getUploadFileInfo(id);
        if (dto == null) {
            return ResultWrapper.wrap(ErrorCode.FILE_NOT_FOUND);
        }
        return ResultWrapper.wrapSuccess(dto);
    }

}
