package site.itxia.apiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.itxia.apiservice.dto.ResultWrapper;
import site.itxia.apiservice.service.TagService;
import site.itxia.apiservice.vo.TagAddVo;

import javax.validation.Valid;

@RestController
@RequestMapping("/tag")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("")
    public ResultWrapper add(@Valid @RequestBody TagAddVo tagAddVo,
                             @RequestHeader int memberID) {
        return ResultWrapper.wrapSuccess(tagService.addTag(tagAddVo, memberID));
    }

    @GetMapping("")
    public ResultWrapper getTags(@RequestParam(required = false) Integer tagID) {
        if (tagID != null) {
            //获取单个标签.
            return ResultWrapper.wrapSuccess(tagService.getTag(tagID));
        } else {
            //获取所有标签.
            return ResultWrapper.wrapSuccess(tagService.getAllTag());
        }
    }

}
