package it.uniroma3.siw.calcio.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.calcio.model.Referee;
import it.uniroma3.siw.calcio.repository.RefereeRepository;

@Component
public class RefereeValidator implements Validator {

    private final RefereeRepository refereeRepository;

    public RefereeValidator(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Referee.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Referee referee = (Referee) target;

        if (referee.getRefereeCode() != null) {
            boolean exists;
            if (referee.getId() == null) {
                exists = refereeRepository.existsByRefereeCode(referee.getRefereeCode());
            } else {
                exists = refereeRepository.existsByRefereeCodeAndIdNot(referee.getRefereeCode(), referee.getId());
            }
            if (exists) {
                errors.rejectValue("refereeCode", "duplicate", "Esiste già un arbitro con questo codice");
            }
        }
    }
}
