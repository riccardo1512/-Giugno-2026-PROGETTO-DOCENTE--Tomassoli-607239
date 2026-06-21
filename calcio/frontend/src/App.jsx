import React, { useState } from "react";

export function App({ players }) {
  // --- STATO DI REACT (Variabili che quando cambiano ricaricano la grafica) ---
  // useState è la funzione base di React: crea una variabile e la funzione per modificarla
  const [filterRole, setFilterRole] = useState("TUTTI");
  const [filterTeam, setFilterTeam] = useState("TUTTE");
  
  // Nuova barra di ricerca per testo libero (nome/cognome)
  const [searchName, setSearchName] = useState("");

  // Stato per l'effetto a "fisarmonica" (salviamo l'ID del giocatore i cui dettagli sono visibili)
  const [expandedPlayerId, setExpandedPlayerId] = useState(null);

  // --- LOGICA DI CALCOLO ---
  // Estraiamo ruoli e squadre univoche da mettere nei menu a tendina (usiamo Set per togliere i duplicati)
  const uniqueRoles = Array.from(new Set(players.map(p => p.role).filter(Boolean))).sort();
  const uniqueTeams = Array.from(new Set(players.map(p => p.team?.name).filter(Boolean))).sort();

  // Questa è la parte dove applichiamo i filtri. Viene ricalcolata in automatico da React ad ogni modifica.
  const filteredPlayers = players.filter(p => {
    // 1. Controllo ruolo
    const roleMatch = filterRole === "TUTTI" || p.role === filterRole;
    // 2. Controllo squadra (alcuni potrebbero non avere una squadra)
    const teamMatch = filterTeam === "TUTTE" || p.team?.name === filterTeam;
    // 3. Controllo testo (cerchiamo dentro nome e cognome, ignorando maiuscole/minuscole)
    const fullName = `${p.name} ${p.surname}`.toLowerCase();
    const searchMatch = fullName.includes(searchName.toLowerCase());

    return roleMatch && teamMatch && searchMatch;
  });

  // Funzione che gestisce il click su una riga (apre o chiude i dettagli)
  const toggleRow = (playerId) => {
    if (expandedPlayerId === playerId) {
      setExpandedPlayerId(null); // Se è già aperto e ci riclicco, lo chiudo (impostando null)
    } else {
      setExpandedPlayerId(playerId); // Altrimenti salvo questo ID come "quello da espandere"
    }
  };

  return (
    <div style={{ marginTop: "20px" }}>
      
      {/* 1. SEZIONE FILTRI (Dropdown e Barra di ricerca) */}
      {/* Usiamo display: flex per affiancarli orizzontalmente in modo carino */}
      <div style={{ display: "flex", gap: "20px", marginBottom: "20px", padding: "15px", backgroundColor: "white", borderRadius: "8px", border: "1px solid lightgray", flexWrap: "wrap" }}>
        
        {/* Nuovo filtro: Ricerca testo */}
        <div>
          <label style={{ fontWeight: "bold", marginRight: "10px" }}>Cerca:</label>
          <input 
            type="text" 
            placeholder="Nome o cognome..." 
            value={searchName} 
            onChange={(e) => setSearchName(e.target.value)} // Quando l'utente digita, React salva il testo
            style={{ padding: "5px", borderRadius: "4px", border: "1px solid gray" }}
          />
        </div>

        <div>
          <label style={{ fontWeight: "bold", marginRight: "10px" }}>Ruolo:</label>
          <select value={filterRole} onChange={(e) => setFilterRole(e.target.value)}>
            <option value="TUTTI">Tutti i ruoli</option>
            {uniqueRoles.map(role => (
              <option key={role} value={role}>{role}</option>
            ))}
          </select>
        </div>

        <div>
          <label style={{ fontWeight: "bold", marginRight: "10px" }}>Squadra:</label>
          <select value={filterTeam} onChange={(e) => setFilterTeam(e.target.value)}>
            <option value="TUTTE">Tutte le squadre</option>
            {uniqueTeams.map(team => (
              <option key={team} value={team}>{team}</option>
            ))}
          </select>
        </div>

      </div>

      {/* 2. LA LISTA DEI RISULTATI */}
      {/* Stile a lista non puntata pulita */}
      <ul style={{ padding: 0, margin: 0, listStyleType: "none", backgroundColor: "white", borderRadius: "8px", border: "1px solid lightgray" }}>
        
        {/* Caso A: Non c'è nessun giocatore dopo i filtri */}
        {filteredPlayers.length === 0 ? (
          <li style={{ padding: "20px", textAlign: "center" }}>Nessun giocatore trovato con questi filtri.</li>
        ) : (
          /* Caso B: Cicliamo sui giocatori filtrati per disegnare le righe */
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
