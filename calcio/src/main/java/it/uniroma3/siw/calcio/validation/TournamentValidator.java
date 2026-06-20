package it.uniroma3.siw.calcio.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.calcio.model.Tournament;
import it.uniroma3.siw.calcio.repository.TournamentRepository;

@Component
public class TournamentValidator implements Validator {

    private final TournamentRepository tournamentRepository;

    public TournamentValidator(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tournament.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Tournament tournament = (Tournament) target;

        if (tournament.getName() != null && tournament.getYear() > 0) {
            boolean exists;
            if (tournament.getId() == null) {
                exists = tournamentRepository.existsByNameAndYear(tournament.getName(), tournament.getYear());
            } else {
                exists = tournamentRepository.existsByNameAndYearAndIdNot(tournament.getName(), tournament.getYear(), tournament.getId());
            }
            if (exists) {
                errors.rejectValue("year", "duplicate", "Esiste già un torneo con questo nome e in questo anno");
            }
        }
    }
}
