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
import site.itxia.apiservice.exception.UploadFileNotFoundException;
import site.itxia.apiservice.util.DateUtil;
import site.itxia.apiservice.util.FileUtil;
import site.itxia.apiservice.util.ImageUtil;


import java.io.File;
import java.io.FileInputStream;
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

    /**
     * 处理上传文件请求.
     *
     * @param multipartFile rest接口传来的文件
     * @param memberID      上传人成员ID, null表示游客
     * @return 上传文件的dto
     */
    public UploadDto uploadFile(MultipartFile multipartFile, Integer memberID) {
        if (memberID == null) {
            memberID = 0;
        }
        byte[] bytes = null;
        try {
            bytes = multipartFile.getBytes();
        } catch (Exception e) {
            //TODO 处理异常
            return null;
        }
        var sha256sum = DigestUtils.sha256Hex(bytes);

        //写入本地目录
        FileUtil.writeFile(sha256sum, bytes);

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

        //返回dto
        return getUploadFileDto(entity);
    }

    /**
     * 读取完整文件.
     *
     * @param sha256sum 文件sha256sum
     * @return 文件byte[]
     */
    public byte[] getFile(String sha256sum) {
        return readFile(sha256sum, false);
    }

    /**
     * 读取缩略图文件.
     *
     * @param sha256sum 文件sha256sum
     * @return 缩略图文件byte[]
     */
    public byte[] getThumbnailFile(String sha256sum) {
        return readFile(sha256sum, true);
    }


    /**
     * 从数据库中获取entity.
     *
     * @param id 文件id
     * @return entity
     * @throws UploadFileNotFoundException
     */
    private Upload getUploadEntity(int id) {
        var optional = uploadRepository.findById(id);
        if (optional.isEmpty()) {
            throw new UploadFileNotFoundException();
        }
        return optional.get();
    }

    /**
     * 从数据库中获取entity.
     *
     * @param sha256sum 文件sha256sum
     * @return entity
     * @throws UploadFileNotFoundException
     */
    private Upload getUploadEntity(String sha256sum) {
        var optional = uploadRepository.findFirstBySha256sum(sha256sum);
        if (optional.isEmpty()) {
            throw new UploadFileNotFoundException();
        }
        return optional.get();
    }


    /**
     * 读取完整文件.
     *
     * @param sha256sum 文件sha256sum
     * @param resize    是否需要缩略图
     * @return 文件byte[]
     * @throws UploadFileNotFoundException 找不到文件异常
     */
    private byte[] readFile(String sha256sum, boolean resize) {
        var entity = this.getUploadEntity(sha256sum);
        if (entity.isDelete()) {
            //文件已被标记为删除
            throw new UploadFileNotFoundException();
        }
        var fileName = entity.getSha256sum();
        try (var fileInputStream = FileUtil.getFileInputStream(fileName)) {
            if (resize) {
                //生成缩略图并返回
                return ImageUtil.resize(fileInputStream);
            }
            //返回原文件
            return fileInputStream.readAllBytes();
        } catch (Exception e) {
            //TODO log日志
            e.printStackTrace();
        }
        throw new UploadFileNotFoundException();
    }


    /**
     * 获取上传文件的文件名.
     *
     * @param sha256sum 文件sha256sum
     * @return 原始文件名
     */
    public String getUploadFileName(String sha256sum) {
        return this.getUploadEntity(sha256sum).getFileName();
    }

    /**
     * 获取上传文件dto.
     */
    public UploadDto getUploadFileDto(String sha256sum) {
        return getUploadFileDto(this.getUploadEntity(sha256sum));
    }


    /**
     * 获取上传文件dto.
     */
    public UploadDto getUploadFileDto(int id) {
        return getUploadFileDto(this.getUploadEntity(id));
    }

    /**
     * 获取上传文件dto.
     *
     * @param entity Upload entity
     * @return dto
     */
    private UploadDto getUploadFileDto(Upload entity) {
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

    /**
     * 获取预约单对应的所有文件dto.
     *
     * @param orderID 预约单id
     * @return dto列表
     */
    public List<UploadDto> getUploadDtosByOrderID(int orderID) {
        var list = new ArrayList<UploadDto>();
        for (var entity : orderUploadRepository.findByOrderID(orderID)) {
            list.add(getUploadFileDto(entity.getUploadID()));
        }
        return list;
    }
}
