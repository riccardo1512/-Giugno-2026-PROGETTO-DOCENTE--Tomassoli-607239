package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Tournament;
import it.uniroma3.siw.calcio.service.TournamentService;
import jakarta.validation.Valid;

@Controller
public class TournamentController {
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/tournaments")
    public String list(Model model) {
        model.addAttribute("tournaments", tournamentService.findAll());
        return "tournaments/list";
    }

    @GetMapping("/tournaments/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("tournament", tournamentService.findById(id).orElse(null));
        return "tournaments/show";
    }

    @GetMapping("/admin/tournaments/new")
    public String createForm(Model model) {
        model.addAttribute("tournament", new Tournament());
        return "admin/tournaments/form";
    }

    @PostMapping("/admin/tournaments")
    public String save(@Valid @ModelAttribute("tournament") Tournament tournament, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/tournaments/form";
        }
        tournamentService.save(tournament);
        return "redirect:/tournaments";
    }

    @GetMapping("/admin/tournaments/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("tournament", tournamentService.findById(id).orElse(null));
        return "admin/tournaments/form";
    }

    @PostMapping("/admin/tournaments/{id}/delete")
    public String delete(@PathVariable Long id) {
        tournamentService.deleteById(id);
        return "redirect:/tournaments";
    }
}
