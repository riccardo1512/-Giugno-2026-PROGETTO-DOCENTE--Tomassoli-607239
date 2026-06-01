package it.uniroma3.siw.calcio.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.calcio.model.Team;
import it.uniroma3.siw.calcio.service.TeamService;

@RestController
@RequestMapping("/rest/teams")
public class RestTeamController {
    private final TeamService teamService;

    public RestTeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> list() {
        return teamService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Team> show(@PathVariable Long id) {
        return teamService.findById(id);
    }
}
