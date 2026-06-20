package it.uniroma3.siw.calcio.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.calcio.model.Player;
import it.uniroma3.siw.calcio.repository.PlayerRepository;

@Component
public class PlayerValidator implements Validator {

    private final PlayerRepository playerRepository;

    public PlayerValidator(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Player.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player player = (Player) target;

        if (player.getName() != null && player.getSurname() != null) {
            boolean exists;
            if (player.getId() == null) {
                exists = playerRepository.existsByNameAndSurname(player.getName(), player.getSurname());
            } else {
                exists = playerRepository.existsByNameAndSurnameAndIdNot(player.getName(), player.getSurname(), player.getId());
            }
            if (exists) {
                errors.rejectValue("surname", "duplicate", "Esiste già un giocatore con questo nome e cognome");
            }
        }
        
        if (player.getDateOfBirth() != null) {
            if (player.getDateOfBirth().getYear() < 1900) {
                errors.rejectValue("dateOfBirth", "too_old", "L'anno di nascita non può essere precedente al 1900");
            }
        }
    }
}
