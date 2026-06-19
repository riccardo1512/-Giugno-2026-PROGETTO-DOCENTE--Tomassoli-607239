package it.uniroma3.siw.calcio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.calcio.model.Referee;
import it.uniroma3.siw.calcio.repository.RefereeRepository;

@Service
public class RefereeService {
    private final RefereeRepository refereeRepository;

    public RefereeService(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    @Transactional
    public Referee save(Referee referee) {
        return refereeRepository.save(referee);
    }

    @Transactional(readOnly = true)
    public Optional<Referee> findById(Long id) {
        return refereeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Referee> findAll() {
        return (List<Referee>) refereeRepository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        refereeRepository.deleteById(id);
    }
}
