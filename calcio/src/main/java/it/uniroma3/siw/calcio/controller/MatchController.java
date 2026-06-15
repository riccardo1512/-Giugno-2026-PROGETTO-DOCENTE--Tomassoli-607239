package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.service.MatchService;
import jakarta.validation.Valid;

@Controller
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches")
    public String list(Model model) {
        model.addAttribute("matches", matchService.findAll());
        return "matches/list";
    }

    @GetMapping("/matches/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("match", matchService.findById(id).orElse(null));
        return "matches/show";
    }

    @GetMapping("/admin/matches/new")
    public String createForm(Model model) {
        model.addAttribute("match", new Match());
        return "admin/matches/form";
    }

    @PostMapping("/admin/matches")
    public String save(@Valid @ModelAttribute("match") Match match, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/matches/form";
        }
        matchService.save(match);
        return "redirect:/matches";
    }

    @GetMapping("/admin/matches/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("match", matchService.findById(id).orElse(null));
        return "admin/matches/form";
    }

    @PostMapping("/admin/matches/{id}/delete")
    public String delete(@PathVariable Long id) {
        matchService.deleteById(id);
        return "redirect:/matches";
    }
}
