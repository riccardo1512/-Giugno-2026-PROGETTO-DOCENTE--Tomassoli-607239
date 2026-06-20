package it.uniroma3.siw.calcio.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import it.uniroma3.siw.calcio.validation.NotFutureYear;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String logo;
    
    @Min(1850)
    @NotFutureYear
    private int yearOfFoundation;

    @NotBlank
    private String city;

    @ManyToMany(mappedBy = "teams")
    private List<Tournament> tournaments;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @OneToMany(mappedBy = "teamHome")
    private List<Match> matchesHome;
    @OneToMany(mappedBy = "teamAway")
    private List<Match> matchesAway;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getYearOfFoundation() {
        return yearOfFoundation;
    }

    public void setYearOfFoundation(int yearOfFoundation) {
        this.yearOfFoundation = yearOfFoundation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Match> getMatchesHome() {
        return matchesHome;
    }

    public void setMatchesHome(List<Match> matchesHome) {
        this.matchesHome = matchesHome;
    }

    public List<Match> getMatchesAway() {
        return matchesAway;
    }

    public void setMatchesAway(List<Match> matchesAway) {
        this.matchesAway = matchesAway;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + yearOfFoundation;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((tournaments == null) ? 0 : tournaments.hashCode());
        result = prime * result + ((players == null) ? 0 : players.hashCode());
        result = prime * result + ((matchesHome == null) ? 0 : matchesHome.hashCode());
        result = prime * result + ((matchesAway == null) ? 0 : matchesAway.hashCode());
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
        Team other = (Team) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (yearOfFoundation != other.yearOfFoundation)
            return false;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (tournaments == null) {
            if (other.tournaments != null)
                return false;
        } else if (!tournaments.equals(other.tournaments))
            return false;
        if (players == null) {
            if (other.players != null)
                return false;
        } else if (!players.equals(other.players))
            return false;
        if (matchesHome == null) {
            if (other.matchesHome != null)
                return false;
        } else if (!matchesHome.equals(other.matchesHome))
            return false;
        if (matchesAway == null) {
            if (other.matchesAway != null)
                return false;
        } else if (!matchesAway.equals(other.matchesAway))
            return false;
        return true;
    }
}
