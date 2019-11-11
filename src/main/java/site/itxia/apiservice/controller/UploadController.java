package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.service.UploadService;

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

    @GetMapping("")
    public byte[] getFile(@RequestParam int id) {
        return uploadService.getFile(id);
    }

}
