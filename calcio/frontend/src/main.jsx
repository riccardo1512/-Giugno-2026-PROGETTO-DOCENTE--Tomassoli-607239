import React from "react";
import { createRoot } from "react-dom/client";
import { App } from "./App.jsx";

// 1. Cerchiamo nel file HTML un "div" con l'ID specifico che fa da contenitore per React
const rootElement = document.getElementById("react-players-root");

// 2. Leggiamo i dati dei giocatori. In Thymeleaf abbiamo stampato un oggetto JSON 
//    dentro una variabile globale "window.SIW_PLAYERS_DATA"
const rawData = window.SIW_PLAYERS_DATA || [];

// 3. Se l'HTML contiene il div contenitore, "iniettiamo" la nostra applicazione React lì dentro
if (rootElement) {
  // createRoot fa partire il motore grafico di React. Gli passiamo i dati iniziali come "props"
  createRoot(rootElement).render(<App players={rawData} />);
}
