package it.uniroma3.siw.calcio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.calcio.model.Tournament;
import it.uniroma3.siw.calcio.model.Match;
import it.uniroma3.siw.calcio.service.TournamentService;
import it.uniroma3.siw.calcio.service.TeamService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class TournamentController {
    private final TournamentService tournamentService;
    private final TeamService teamService;

    public TournamentController(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @GetMapping("/tournaments")
    public String list(Model model) {
        model.addAttribute("tournaments", tournamentService.findAll());
        return "tournaments/listTournament";
    }



    @GetMapping("/tournaments/{id}")
    public String show(@PathVariable Long id, Model model) {
        Tournament tournament = tournamentService.findById(id).orElse(null);
        if (tournament != null && tournament.getMatches() != null) {
            
            // Creiamo due liste vuote
            List<Match> playedMatches = new java.util.ArrayList<>();
            List<Match> scheduledMatches = new java.util.ArrayList<>();
            
            // Riempiamo le liste con un normale ciclo for
            for (Match m : tournament.getMatches()) {
                if (m.getState().name().equals("PLAYED")) {
                    playedMatches.add(m);
                } else if (m.getState().name().equals("SCHEDULED")) {
                    scheduledMatches.add(m);
                }
            }
            
            // Creiamo un "comparatore normale" locale per ordinare prima per data, poi per ora
            Comparator<Match> matchComparator = new Comparator<Match>() {
                @Override
                public int compare(Match m1, Match m2) {
                    int dateCmp = m1.getDate().compareTo(m2.getDate());
                    if (dateCmp == 0) {
                        return m1.getTime().compareTo(m2.getTime());
                    }
                    return dateCmp;
                }
            };
            
            // Ordiniamo le liste
            java.util.Collections.sort(playedMatches, matchComparator);
            java.util.Collections.sort(scheduledMatches, matchComparator);
                
            model.addAttribute("playedMatches", playedMatches);
            model.addAttribute("scheduledMatches", scheduledMatches);
        }
        
        model.addAttribute("tournament", tournament);
        model.addAttribute("ranking", tournamentService.getTournamentRanking(id));
        return "tournaments/showTournament";
    }

    @GetMapping("/admin/tournaments/new")
    public String createForm(Model model) {
        model.addAttribute("tournament", new Tournament());
        model.addAttribute("teams", teamService.findAll());
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
        model.addAttribute("teams", teamService.findAll());
        return "admin/tournaments/form";
    }

    @PostMapping("/admin/tournaments/{id}/delete")
    public String delete(@PathVariable Long id) {
        tournamentService.deleteById(id);
        return "redirect:/tournaments";
    }
}
