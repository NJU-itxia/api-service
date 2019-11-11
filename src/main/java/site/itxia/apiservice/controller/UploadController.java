package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public int uploadFile(@RequestParam(name = "file") MultipartFile remoteFile,
                          @RequestHeader(required = false) Integer memberID) {
        return uploadService.uploadFile(remoteFile, memberID);
    }

    @GetMapping("")
    public byte[] getFile(@RequestParam int id) {
        return uploadService.getFile(id);
    }

}
