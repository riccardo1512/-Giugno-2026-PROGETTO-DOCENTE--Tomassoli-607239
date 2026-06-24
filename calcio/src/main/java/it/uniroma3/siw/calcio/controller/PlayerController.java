package it.uniroma3.siw.calcio.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.calcio.exception.DuplicatePlayerException;
import it.uniroma3.siw.calcio.model.Player;
import it.uniroma3.siw.calcio.service.PlayerService;
import it.uniroma3.siw.calcio.service.TeamService;
import it.uniroma3.siw.calcio.validation.PlayerValidator;
import jakarta.validation.Valid;

@Controller
public class PlayerController {
	private final PlayerService playerService;
	private final TeamService teamService;
	private final PlayerValidator playerValidator;

	public PlayerController(PlayerService playerService, TeamService teamService, PlayerValidator playerValidator) {
		this.playerService = playerService;
		this.teamService = teamService;
		this.playerValidator = playerValidator;
	}

	@GetMapping("/players")
	public String list(Model model) {
		model.addAttribute("players", playerService.findAllWithTeam());
		return "players/listPlayer";
	}

	@GetMapping("/players/react")
	public String listReact(Model model) {
		List<Map<String, Object>> playerDTOs = new java.util.ArrayList<>();
		for (Player p : playerService.findAll()) {
			Map<String, Object> dto = new java.util.HashMap<>();
			dto.put("id", p.getId());
			dto.put("name", p.getName());
			dto.put("surname", p.getSurname());
			dto.put("role", p.getRole());
			if (p.getDateOfBirth() != null) {
				dto.put("dateOfBirth", p.getDateOfBirth().toString());
			}
			dto.put("height", p.getHeight());
			if (p.getTeam() != null) {
				Map<String, Object> teamDto = new java.util.HashMap<>();
				teamDto.put("name", p.getTeam().getName());
				dto.put("team", teamDto);
			}
			playerDTOs.add(dto);
		}
		model.addAttribute("players", playerDTOs);
		return "players/reactPlayers";
	}

	@GetMapping("/admin/players/new")
	public String createForm(Model model) {
		model.addAttribute("player", new Player());
		model.addAttribute("teams", teamService.findAll());
		return "admin/players/form";
	}

	@PostMapping("/admin/players")
	public String save(@Valid @ModelAttribute("player") Player player,
			BindingResult bindingResult, Model model) {

		playerValidator.validate(player, bindingResult);

		if (bindingResult.hasErrors()) {
			model.addAttribute("teams", teamService.findAll());
			return "admin/players/form";
		}
		try {
			playerService.save(player);
			return "redirect:/players";
		} catch (DuplicatePlayerException e) {
			bindingResult.reject("player.duplicate", "Esiste già un giocatore con questo nome e data di nascita");
			return "admin/players/form";
		}
	}

	@GetMapping("/admin/players/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		Optional<Player> optional = playerService.findById(id);
		if (optional.isPresent()) {
			Player p = optional.get();
			model.addAttribute("player", p);
			model.addAttribute("teams", teamService.findAll());
		} else {
			return "redirect:/players";
		}
		return "admin/players/form";
	}

	@PostMapping("/admin/players/{id}/delete")
	public String delete(@PathVariable Long id) {
		playerService.deleteById(id);
		return "redirect:/players";
	}

	@GetMapping("/players/{id}")
	public String show(@PathVariable Long id, Model model) {
		Optional<Player> optional = playerService.findById(id);

		if (optional.isPresent()) {
			Player p = optional.get();
			model.addAttribute("player", p);
		} else {
			return "redirect:/players";
		}
		return "players/showPlayer";
	}
}
