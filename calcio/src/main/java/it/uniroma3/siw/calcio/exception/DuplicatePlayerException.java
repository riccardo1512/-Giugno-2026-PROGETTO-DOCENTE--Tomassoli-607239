package it.uniroma3.siw.calcio.exception;

public class DuplicatePlayerException extends RuntimeException {

    public DuplicatePlayerException(String name, String surname) {
        super("Il giocatore '" + name + " " + surname + "' è già presente nel sistema");
    }
}