package site.itxia.apiservice.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.itxia.apiservice.data.entity.OrderUpload;
import site.itxia.apiservice.data.entity.Upload;
import site.itxia.apiservice.data.repository.OrderUploadRepository;
import site.itxia.apiservice.data.repository.UploadRepository;
import site.itxia.apiservice.dto.UploadDto;
import site.itxia.apiservice.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenxi
 */
@Service
public class UploadService {

    private UploadRepository uploadRepository;
    private OrderUploadRepository orderUploadRepository;
    private MemberService memberService;

    @Autowired
    public UploadService(UploadRepository uploadRepository, OrderUploadRepository orderUploadRepository, MemberService memberService) {
        this.uploadRepository = uploadRepository;
        this.orderUploadRepository = orderUploadRepository;
        this.memberService = memberService;
    }

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
                    .time(DateUtil.getCurrentUnixTime())
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
     * 获取上传文件信息.
     */
    public UploadDto getUploadFileInfo(int id) {
        var optional = uploadRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        var entity = optional.get();
        return UploadDto.builder()
                .id(entity.getId())
                .fileName(entity.getFileName())
                .sha256sum(entity.getSha256sum())
                .uploadByMemberID(entity.getUploadBy())
                .uploadByMemberName(memberService.getMemberNameByID(entity.getUploadBy()))
                .size(entity.getSize())
                .time(entity.getTime())
                .build();
    }

    /**
     * 将附件添加到预约单.
     *
     * @param orderID      预约单ID.
     * @param uploadIDList 附件ID列表.
     */
    public void attachUploadsToOrder(int orderID, List<Integer> uploadIDList) {
        for (int uploadID : uploadIDList) {
            var entity = OrderUpload.builder()
                    .orderID(orderID)
                    .uploadID(uploadID)
                    .delete(false)
                    .build();
            orderUploadRepository.save(entity);
        }
    }

    public List<UploadDto> getUploadDtosByOrderID(int orderID) {
        var list = new ArrayList<UploadDto>();
        for (var entity : orderUploadRepository.findByOrderID(orderID)) {
            list.add(getUploadFileInfo(entity.getUploadID()));
        }
        return list;
    }
}
