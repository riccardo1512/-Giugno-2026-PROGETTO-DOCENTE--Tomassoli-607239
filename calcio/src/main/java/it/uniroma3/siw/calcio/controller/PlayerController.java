package it.uniroma3.siw.calcio.controller;

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
import jakarta.validation.Valid;

@Controller
@RequestMapping("/players")
public class PlayerController {
	private final PlayerService playerService;

	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("players", playerService.findAll());
		return "players/list";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("player", new Player());
		return "players/form";
	}

	@PostMapping
	public String save(@Valid @ModelAttribute("player") Player player,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "players/form";
		}
		try {
			playerService.save(player);
			return "redirect:/players";
		} catch (DuplicatePlayerException e) {
			bindingResult.reject("player.duplicate", "Esiste già un giocatore con questo nome e data di nascita");
			return "players/form";
		}
	}

	@GetMapping("/{id}/edit")
	public String editForm(@PathVariable Long id, Model model) {
		Optional<Player> optional = playerService.findById(id);
		if (optional.isPresent()) {
			Player p = optional.get();
			model.addAttribute("player", p);
		} else {
			return "redirect:/players";
		}
		return "players/form";
	}

	@PostMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		playerService.deleteById(id);
		return "redirect:/players";
	}

	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		Optional<Player> optional = playerService.findById(id);

		if (optional.isPresent()) {
			Player p = optional.get();
			model.addAttribute("player", p);
		} else {
			return "redirect:/players";
		}
		return "players/show";
	}
}
