package site.itxia.apiservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.itxia.apiservice.data.entity.Upload;
import site.itxia.apiservice.data.repository.UploadRepository;
import site.itxia.apiservice.dto.UploadDto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author zhenxi
 */
@Service
public class UploadService {

    @Autowired
    private UploadRepository uploadRepository;

    public Integer uploadFile(MultipartFile multipartFile, Integer memberID) {
        if (memberID == null) {
            memberID = 0;
        }
        try {
            var bytes = multipartFile.getBytes();
            var sha256sum = DigestUtils.sha256Hex(bytes);

            //写入本地目录
            var localFile = new File(sha256sum);
            var fileOutputStream = new FileOutputStream(localFile);
            fileOutputStream.write(bytes);
            fileOutputStream.close();

            //记录到数据库中
            var entity = Upload.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .sha256sum(sha256sum)
                    .size(multipartFile.getSize())
                    .uploadBy(memberID)
                    .delete(false)
                    .build();
            entity = uploadRepository.save(entity);
            return entity.getId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getFile(int fileID) {
        var optional = uploadRepository.findById(fileID);
        if (optional.isEmpty()) {
            return null;
        }
        var entity = optional.get();
        try (var fileInputStream = new FileInputStream(new File(entity.getSha256sum()))) {
            return fileInputStream.readAllBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //TODO 处理错误
        return null;
    }

    /**
     * TODO
     * 获取上传文件信息.
     */
    public UploadDto getUploadInfo() {
        return null;
    }

}
