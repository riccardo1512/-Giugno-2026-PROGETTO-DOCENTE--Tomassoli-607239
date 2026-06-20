package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Referee;
import it.uniroma3.siw.calcio.service.RefereeService;
import it.uniroma3.siw.calcio.validation.RefereeValidator;
import jakarta.validation.Valid;

@Controller
public class RefereeController {
    private final RefereeService refereeService;
    private final RefereeValidator refereeValidator;

    public RefereeController(RefereeService refereeService, RefereeValidator refereeValidator) {
        this.refereeService = refereeService;
        this.refereeValidator = refereeValidator;
    }

    @GetMapping("/referees")
    public String list(Model model) {
        model.addAttribute("referees", refereeService.findAll());
        return "referees/listReferee";
    }

    @GetMapping("/referees/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("referee", refereeService.findById(id).orElse(null));
        return "referees/showReferee";
    }

    @GetMapping("/admin/referees/new")
    public String createForm(Model model) {
        model.addAttribute("referee", new Referee());
        return "admin/referees/form";
    }

    @PostMapping("/admin/referees")
    public String save(@Valid @ModelAttribute("referee") Referee referee, BindingResult bindingResult, Model model) {

        refereeValidator.validate(referee, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/referees/form";
        }
        refereeService.save(referee);
        return "redirect:/referees";
    }

    @GetMapping("/admin/referees/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("referee", refereeService.findById(id).orElse(null));
        return "admin/referees/form";
    }

    @PostMapping("/admin/referees/{id}/delete")
    public String delete(@PathVariable Long id) {
        refereeService.deleteById(id);
        return "redirect:/referees";
    }
}
