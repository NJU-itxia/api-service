package site.itxia.apiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.itxia.apiservice.data.entity.Tag;
import site.itxia.apiservice.data.repository.OrderTagRepository;
import site.itxia.apiservice.data.repository.TagRepository;
import site.itxia.apiservice.dto.TagDto;
import site.itxia.apiservice.util.DateUtil;
import site.itxia.apiservice.vo.TagAddVo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenxi
 */
@Service
public class TagService {

    private TagRepository tagRepository;

    private OrderTagRepository orderTagRepository;

    private MemberService memberService;

    @Autowired
    public TagService(TagRepository tagRepository, OrderTagRepository orderTagRepository, MemberService memberService) {
        this.tagRepository = tagRepository;
        this.orderTagRepository = orderTagRepository;
        this.memberService = memberService;
    }

    public TagDto addTag(TagAddVo tagAddVo, int memberID) {
        var tagID = addNewTag(tagAddVo.getTagName(), memberID);
        return getTag(tagID);
    }

    /**
     * @param tagID 标签ID.
     * @return 标签dto.
     */
    public TagDto getTag(int tagID) {
        var tagOptional = tagRepository.findById(tagID);
        if (tagOptional.isEmpty()) {
            return null;
        } else {
            var tag = tagOptional.get();
            return tagToDto(tag);
        }
    }

    /**
     * @return 标签dto列表.
     */
    public List<TagDto> getAllTag() {
        var tagList = tagRepository.findAll();
        var resultList = new ArrayList<TagDto>();
        for (var tag : tagList) {
            resultList.add(tagToDto(tag));
        }
        return resultList;
    }

    /**
     * 将标签转换成标签dto.
     *
     * @param tag 标签entity.
     * @return 标签dto.
     */
    private TagDto tagToDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .useCount(countTagUsage(tag.getId()))
                .addTime(tag.getAddTime())
                .addByMemberID(tag.getAddByMemberID())
                .addByMemberName(memberService.getMemberNameByID(tag.getAddByMemberID()))
                .build();
    }

    /**
     * 添加标签.
     *
     * @param tagName  标签名.
     * @param memberID 添加标签的成员ID.
     * @return 新标签的ID.
     */
    private int addNewTag(String tagName, int memberID) {
        var tag = Tag.builder()
                .tagName(tagName)
                .addByMemberID(memberID)
                .addTime(DateUtil.getCurrentUnixTime())
                .delete(false)
                .build();
        tag = tagRepository.save(tag);
        return tag.getId();
    }

    /**
     * TODO
     * 计算标签被引用次数.
     *
     * @param tagID 标签ID.
     * @return 被引用次数.
     */
    private int countTagUsage(int tagID) {
        return 0;
    }

}
