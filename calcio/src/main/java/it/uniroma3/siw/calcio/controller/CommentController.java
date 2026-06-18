package it.uniroma3.siw.calcio.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Comment;
import it.uniroma3.siw.calcio.model.Credentials;
import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.service.CommentService;
import it.uniroma3.siw.calcio.service.CredentialsService;
import it.uniroma3.siw.calcio.service.MatchService;
import jakarta.validation.Valid;

import java.util.Optional;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final MatchService matchService;
    private final CredentialsService credentialsService;

    public CommentController(CommentService commentService, MatchService matchService, CredentialsService credentialsService) {
        this.commentService = commentService;
        this.matchService = matchService;
        this.credentialsService = credentialsService;
    }

    @PostMapping("/user/matches/{id}/comment")
    public String addComment(@PathVariable Long id, @Valid @ModelAttribute("newComment") Comment comment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/matches/" + id;
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            
            Optional<Match> matchOpt = matchService.findById(id);
            if (matchOpt.isPresent()) {
                comment.setAuthor(credentials.getUser());
                comment.setMatch(matchOpt.get());
                commentService.save(comment);
            }
        }
        
        return "redirect:/matches/" + id;
    }

    @GetMapping("/user/comments/{id}/edit")
    public String editCommentForm(@PathVariable Long id, Model model) {
        Optional<Comment> commentOpt = commentService.findById(id);
        if (commentOpt.isPresent()) {
            Comment comment = commentOpt.get();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
                
                if (comment.getAuthor().getId().equals(credentials.getUser().getId())) {
                    model.addAttribute("comment", comment);
                    return "comments/form";
                }
            }
            return "redirect:/matches/" + comment.getMatch().getId();
        }
        return "redirect:/";
    }

    @PostMapping("/user/comments/{id}/edit")
    public String updateComment(@PathVariable Long id, @Valid @ModelAttribute("comment") Comment updatedComment, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "comments/form";
        }
        
        Optional<Comment> commentOpt = commentService.findById(id);
        if (commentOpt.isPresent()) {
            Comment existingComment = commentOpt.get();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
                
                if (existingComment.getAuthor().getId().equals(credentials.getUser().getId())) {
                    existingComment.setText(updatedComment.getText());
                    commentService.save(existingComment);
                }
            }
            return "redirect:/matches/" + existingComment.getMatch().getId();
        }
        return "redirect:/";
    }
}
