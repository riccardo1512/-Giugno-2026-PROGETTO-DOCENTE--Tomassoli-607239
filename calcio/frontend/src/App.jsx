import React, { useState } from "react";

export function App({ players }) {
  // Stati per i filtri a tendina
  const [filterRole, setFilterRole] = useState("TUTTI");
  const [filterTeam, setFilterTeam] = useState("TUTTE");

  // Stato per la fisarmonica (quale ID giocatore è attualmente aperto)
  const [expandedPlayerId, setExpandedPlayerId] = useState(null);

  // Calcoliamo le opzioni uniche per i dropdown (Ruoli e Squadre)
  const uniqueRoles = Array.from(new Set(players.map(p => p.role).filter(Boolean))).sort();
  const uniqueTeams = Array.from(new Set(players.map(p => p.team?.name).filter(Boolean))).sort();

  // Filtriamo i giocatori in base ai dropdown scelti
  const filteredPlayers = players.filter(p => {
    const roleMatch = filterRole === "TUTTI" || p.role === filterRole;
    const teamMatch = filterTeam === "TUTTE" || p.team?.name === filterTeam;
    return roleMatch && teamMatch;
  });

  // Funzione per espandere/collassare la riga
  const toggleRow = (playerId) => {
    if (expandedPlayerId === playerId) {
      setExpandedPlayerId(null); // Richiude se è già aperto
    } else {
      setExpandedPlayerId(playerId); // Apre quello nuovo
    }
  };

  return (
    <div style={{ marginTop: "20px" }}>
      
      {/* 1. I FILTRI (Menu a tendina) */}
      <div style={{ display: "flex", gap: "20px", marginBottom: "20px", padding: "15px", backgroundColor: "white", borderRadius: "8px", border: "1px solid lightgray" }}>
        
        <div>
          <label style={{ fontWeight: "bold", marginRight: "10px" }}>Filtra per Ruolo:</label>
          <select value={filterRole} onChange={(e) => setFilterRole(e.target.value)}>
            <option value="TUTTI">Tutti i ruoli</option>
            {uniqueRoles.map(role => (
              <option key={role} value={role}>{role}</option>
            ))}
          </select>
        </div>

        <div>
          <label style={{ fontWeight: "bold", marginRight: "10px" }}>Filtra per Squadra:</label>
          <select value={filterTeam} onChange={(e) => setFilterTeam(e.target.value)}>
            <option value="TUTTE">Tutte le squadre</option>
            {uniqueTeams.map(team => (
              <option key={team} value={team}>{team}</option>
            ))}
          </select>
        </div>

      </div>

      {/* 2. LA LISTA (L'Accordion) */}
      <ul style={{ padding: 0, margin: 0, listStyleType: "none", backgroundColor: "white", borderRadius: "8px", border: "1px solid lightgray" }}>
        {filteredPlayers.length === 0 ? (
          <li style={{ padding: "20px", textAlign: "center" }}>Nessun giocatore trovato con questi filtri.</li>
        ) : (
          filteredPlayers.map(player => (
            <li key={player.id} style={{ borderBottom: "1px solid lightgray" }}>
              
              {/* RIGA COMPATTA (Cliccabile) */}
              <div 
                onClick={() => toggleRow(player.id)}
                style={{ 
                  padding: "15px 20px", 
                  cursor: "pointer", 
                  display: "flex", 
                  justifyContent: "space-between",
                  backgroundColor: expandedPlayerId === player.id ? "whitesmoke" : "transparent"
                }}
              >
                <strong>{player.name} {player.surname}</strong>
                <span style={{ color: "forestgreen" }}>{expandedPlayerId === player.id ? "▲ Chiudi dettagli" : "▼ Espandi dettagli"}</span>
              </div>

              {/* DETTAGLIO ESPANSO (Si vede solo se l'ID corrisponde) */}
              {expandedPlayerId === player.id && (
                <div style={{ padding: "15px 20px", backgroundColor: "whitesmoke", borderTop: "1px solid lightgray" }}>
                  <p style={{ margin: "5px 0" }}><strong>Data di nascita:</strong> {player.dateOfBirth}</p>
                  <p style={{ margin: "5px 0" }}><strong>Altezza:</strong> {player.height} cm</p>
                  <p style={{ margin: "5px 0" }}><strong>Ruolo:</strong> {player.role}</p>
                  <p style={{ margin: "5px 0" }}><strong>Squadra:</strong> {player.team ? player.team.name : "Nessuna"}</p>
                </div>
              )}

            </li>
          ))
        )}
      </ul>

    </div>
  );
}
