package it.uniroma3.siw.calcio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.exception.DuplicatePlayerException;
import it.uniroma3.siw.calcio.model.Player;
import it.uniroma3.siw.calcio.repository.PlayerRepository;

@Service
public class PlayerService {

   private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional(readOnly = true)
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    @Transactional
    public Player save(Player player) throws DuplicatePlayerException {
        boolean duplicate = player.getId() == null
            ? playerRepository.existsByNameAndSurname(player.getName(), player.getSurname())
            : playerRepository.existsByNameAndSurnameAndIdNot(player.getName(), player.getSurname(), player.getId());
        if (duplicate) {
            throw new DuplicatePlayerException(player.getName(), player.getSurname());
        }
        return this.playerRepository.save(player);
    }

    @Transactional
    public void deleteById(Long id) {
        this.playerRepository.deleteById(id);
    }
}
