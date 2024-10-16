package com.my.articles.controller;

import com.my.articles.dto.ArticleDTO;
import com.my.articles.dto.CommentDTO;
import com.my.articles.entity.Article;
import com.my.articles.entity.Comment;
import com.my.articles.service.ArticleService;
import com.my.articles.service.QueryService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    QueryService queryService;

    @GetMapping("")
    public String showAllArticles(Model model) {
        List<ArticleDTO> dtoList = articleService.getAllArticle();
        model.addAttribute("articles", dtoList);
//        model.addAttribute("articles", queryService.findAllArticles());
        return "/articles/show_all";
    }

    @GetMapping("new")
    public String newArticleForm(Model model) {
        model.addAttribute("dto", new ArticleDTO());
//        model.addAttribute("article", new Article());
        return "/articles/new";
    }

    @PostMapping("create")
    public String createArticle(@ModelAttribute("article")Article article, ArticleDTO dto) {
        articleService.insertArticle(dto);

        log.info(article.toString());
//        queryService.saveArticle(article);
        return "redirect:/articles";
    }

    @GetMapping("{id}")
    public String showOneArticle(@PathVariable("id")Long id, Model model) {

        ArticleDTO dto = articleService.getOneArticle(id);
        model.addAttribute("dto", dto);
//        model.addAttribute("comments", dto.getCommentList());

//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setArticle(ArticleDTO.fromDTO(dto));
        model.addAttribute("commentDto", new CommentDTO());

        log.info("================== myId = " + id + " ==================");
//        model.addAttribute("article", queryService.findById(id));
        return "/articles/show";
    }

    @PostMapping("{id}")
    public String createComment(@PathVariable("id")Long id, CommentDTO commentDTO) {
        ArticleDTO dto = articleService.getOneArticle(id);
        articleService.insertComment(id);
        return "redirect:/articles";
    }

    @GetMapping("{id}/update")
    public String viewUpdateArticle(Model model, @PathVariable("id")Long id) {
        model.addAttribute("dto", articleService.getOneArticle(id));
        return "/articles/update";
    }

    @PostMapping("update")
    public String updateArticle(ArticleDTO dto) {
//        String url="redirect:/articles/"+dto.getId();
        System.out.println(dto);
        articleService.updateArticle(dto);
        return "redirect:/articles";
    }

    @GetMapping("{id}/delete")
    public String deleteArticle(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        articleService.deleteArticle(id);

//        log.info("================== deleteId = " + id + " ==================");
//        queryService.deleteById(id);
        redirectAttributes.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        return "redirect:/articles";
    }
}
