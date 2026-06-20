import React from "react";
import { createRoot } from "react-dom/client";
import { App } from "./App.jsx";

const rootElement = document.getElementById("react-players-root");
const rawData = window.SIW_PLAYERS_DATA || [];

if (rootElement) {
  createRoot(rootElement).render(<App players={rawData} />);
}
