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
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private LocalTime time;
    //goalsHome
    //goalsAway
    @Enumerated(EnumType.STRING)
    private MatchState state;

    @ManyToOne
    private Tournament tournament;
    
    @ManyToOne
    private Team teamHome;
    @ManyToOne
    private Team teamAway;
    
    @ManyToOne
    private Referee referee;

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", teamHome=" + (teamHome != null ? teamHome.getId() : "null") +
                ", teamAway=" + (teamAway != null ? teamAway.getId() : "null") +
                ", state=" + state +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public MatchState getState() {
        return state;
    }

    public void setState(MatchState state) {
        this.state = state;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(Team teamHome) {
        this.teamHome = teamHome;
    }

    public Team getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(Team teamAway) {
        this.teamAway = teamAway;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((tournament == null) ? 0 : tournament.hashCode());
        result = prime * result + ((teamHome == null) ? 0 : teamHome.hashCode());
        result = prime * result + ((teamAway == null) ? 0 : teamAway.hashCode());
        result = prime * result + ((referee == null) ? 0 : referee.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Match other = (Match) obj;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (state != other.state)
            return false;
        if (tournament == null) {
            if (other.tournament != null)
                return false;
        } else if (!tournament.equals(other.tournament))
            return false;
        if (teamHome == null) {
            if (other.teamHome != null)
                return false;
        } else if (!teamHome.equals(other.teamHome))
            return false;
        if (teamAway == null) {
            if (other.teamAway != null)
                return false;
        } else if (!teamAway.equals(other.teamAway))
            return false;
        if (referee == null) {
            if (other.referee != null)
                return false;
        } else if (!referee.equals(other.referee))
            return false;
        return true;
    }
    
}
