package it.uniroma3.siw.calcio.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private LocalTime time;
    //goalsHome
    //goalsAway
    private MatchState state;

    /*
    Relazioni (minime):
    • una partita appartiene a un torneo
    • una partita coinvolge due squadre
    • una partita ha un arbitro 
    */
    
}
