package it.uniroma3.siw.calcio.exception;

public class DuplicateTeamException extends RuntimeException {

    public DuplicateTeamException(String name, Integer yearOfFoundation) {
        super("La squadra '" + name + "' (" + yearOfFoundation + ") è già presente nel sistema");
    }
}