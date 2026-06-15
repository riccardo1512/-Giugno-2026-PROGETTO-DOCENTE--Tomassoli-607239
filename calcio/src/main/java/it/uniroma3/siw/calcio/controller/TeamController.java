package it.uniroma3.siw.calcio.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.calcio.exception.DuplicateTeamException;
import it.uniroma3.siw.calcio.model.Team;
import it.uniroma3.siw.calcio.model.Player;
import it.uniroma3.siw.calcio.service.TeamService;
import it.uniroma3.siw.calcio.service.PlayerService;
import jakarta.validation.Valid;

@Controller
public class TeamController {
    private final TeamService teamService;
    private final PlayerService playerService;
    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/teams")
    public String list(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "teams/listTeam";
    }

    @GetMapping("/teams/{id}")
    public String show(@PathVariable Long id, Model model) {
        Optional<Team> optional = teamService.findById(id);
        if (optional.isEmpty()) {
            return "redirect:/teams";
        }
        model.addAttribute("team", optional.get());
        return "teams/showTeam";
    }

    @GetMapping("/admin/teams/new")
    public String createForm(Model model) {
        Team team = new Team();
        team.setPlayers(new ArrayList<>());
        model.addAttribute("team", team);
        model.addAttribute("players", playerService.findAll());
        return "admin/teams/form";
    }

    @PostMapping("/admin/teams")
    public String save(@Valid @ModelAttribute("team") Team team,
            BindingResult bindingResult, Model model,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) Long playerId,
            @RequestParam(required = false) List<Long> playerIds) {

        // Ricostruisce la lista giocatori dagli hidden input
        List<Player> players = new ArrayList<>();
        if (playerIds != null)
            for (Long id : playerIds) {
                Optional<Player> optional = playerService.findById(id);
                if (optional.isPresent()) {
                    players.add(optional.get());
                }
            }
        team.setPlayers(players);
        logger.debug("Size lista giocatori: {}", team.getPlayers().size());

        if ("addPlayer".equals(action)) {
            if (playerId != null && playerId > 0) {
                Optional<Player> player = playerService.findById(playerId);
                if (player.isPresent() && !team.getPlayers().contains(player.get()))
                    team.getPlayers().add(player.get());
            }
            model.addAttribute("players", playerService.findAll());
            return "admin/teams/form";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            return "admin/teams/form";
        }

        try {
            teamService.save(team);
            return "redirect:/teams";
        } catch (DuplicateTeamException e) {
            model.addAttribute("players", playerService.findAll());
            bindingResult.reject("team.duplicate", "Esiste già una squadra con questo nome");
            return "admin/teams/form";
        }
    }

    @GetMapping("/admin/teams/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<Team> optional = teamService.findById(id);
        if (optional.isEmpty()) {
            return "redirect:/teams";
        }
        Team team = optional.get();
        if (team.getPlayers() == null) team.setPlayers(new ArrayList<>());
        model.addAttribute("team", team);
        model.addAttribute("players", playerService.findAll());
        return "admin/teams/form";
    }

    @PostMapping("/admin/teams/{id}/delete")
    public String delete(@PathVariable Long id) {
        teamService.deleteById(id);
        return "redirect:/teams";
    }

}
