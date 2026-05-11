package it.uniroma3.siw.calcio.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String role;
    private int height; // in cm

    // Relazioni (minime):
    // • ogni giocatore appartiene a una sola squadra
}
