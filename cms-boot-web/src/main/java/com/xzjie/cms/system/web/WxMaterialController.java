package com.xzjie.cms.system.web;

import com.xzjie.cms.core.utils.MapUtils;
import com.xzjie.cms.dto.WxArticleRequest;
import com.xzjie.cms.dto.WxArticleTemplateRequest;
import com.xzjie.cms.model.WxArticle;
import com.xzjie.cms.model.WxArticleTemplate;
import com.xzjie.cms.service.WxArticleTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wx-material")
public class WxMaterialController {
    @Autowired
    private WxArticleTemplateService articleTemplateService;

    @GetMapping("/article/{newsId}")
    public Map<String, Object> article(@PathVariable String newsId) {
        List<WxArticle> list = articleTemplateService.getArticle(newsId);
        return MapUtils.success(list);
    }

    @PostMapping("/article/create")
    public Map<String, Object> createArticle(@RequestBody WxArticleRequest articleRequest) {
        articleTemplateService.saveArticle(articleRequest.getId() + "", articleRequest.getList(), articleRequest.getDeleteArticleIds());
        return MapUtils.success();
    }

    @PostMapping("/article/publish")
    public Map<String, Object> publishArticle(@RequestBody WxArticleRequest articleRequest) {
        articleTemplateService.updateArticle(articleRequest.getId() + "", articleRequest.getList(), articleRequest.getDeleteArticleIds());
        return MapUtils.success();
    }

    @GetMapping("/article/template")
    public Map<String, Object> articleTemplate(@Validated WxArticleTemplateRequest articleTemplateRequest) {
        Page<WxArticleTemplate> articleTemplatePage = articleTemplateService.getArticleTemplate(articleTemplateRequest.getPage(), articleTemplateRequest.getSize(), articleTemplateRequest.toArticleTemplate());

        return MapUtils.success(articleTemplatePage.getContent(), articleTemplatePage.getTotalElements());
    }

    @GetMapping("/article/template/data")
    public Map<String, Object> getArticleTemplateData(@Validated WxArticleTemplateRequest articleTemplateRequest) {
        if (articleTemplateRequest.getPublish() == null) {
            articleTemplateRequest.setPublish(true);
        }
        List<WxArticleTemplate> articleTemplates = articleTemplateService.getArticleTemplate(articleTemplateRequest.toArticleTemplate());

        return MapUtils.success(articleTemplates);
    }

    @PostMapping("/article/template")
    public Map<String, Object> createArticleTemplate(@Validated @RequestBody WxArticleTemplateRequest articleTemplateRequest) {
        articleTemplateService.save(articleTemplateRequest.toArticleTemplate());

        return MapUtils.success();
    }

    @PutMapping("/article/template/{id}")
    public Map<String, Object> updateArticleTemplate(@PathVariable Long id, @Validated @RequestBody WxArticleTemplateRequest articleTemplate) {
        articleTemplate.setId(id);
        articleTemplateService.update(articleTemplate.toArticleTemplate());

        return MapUtils.success();
    }

    @PostMapping("/article/preview/send")
    public Map<String, Object> sendPreviewArticleTemplate(@Validated @RequestBody WxArticleTemplateRequest articleTemplate) {
        articleTemplateService.sendPreviewArticleTemplate(articleTemplate.toArticleTemplate(), articleTemplate.getPreviewFansIds());

        return MapUtils.success();
    }

    @PostMapping("/article/tag/send")
    public Map<String, Object> sendTagArticleTemplate(@Validated @RequestBody WxArticleTemplateRequest articleTemplate) {
        articleTemplateService.sendTagArticleTemplate(articleTemplate.toArticleTemplate(), articleTemplate.getTagId());

        return MapUtils.success();
    }

    @PostMapping("/article/fans/send")
    public Map<String, Object> sendFansArticleTemplate(@Validated @RequestBody WxArticleTemplateRequest articleTemplate) {
        articleTemplateService.sendFansArticleTemplate(articleTemplate.toArticleTemplate(), articleTemplate.getFansIds());

        return MapUtils.success();
    }
}

