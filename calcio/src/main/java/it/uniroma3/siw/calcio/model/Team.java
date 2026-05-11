package it.uniroma3.siw.calcio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String name;
    private int yearOfFoundation;
    private String city;

    //Relazioni (minime):
    //• una squadra partecipa a uno o più tornei
    //• una squadra ha più giocatori 
}
