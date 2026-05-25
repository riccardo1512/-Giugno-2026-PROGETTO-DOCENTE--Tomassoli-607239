package it.uniroma3.siw.calcio.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

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

    @OneToOne
    private Tournament tournament;
    
    @ManyToOne
    private Team teamHome;
    @ManyToOne
    private Team teamAway;
    
    @ManyToOne
    private Referee referee;
    
}
