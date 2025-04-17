import "./shim";
import React from "react";
import ReactDOM from "react-dom/client";
import GetEvents from "./pages/GetEvents";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <GetEvents />
  </React.StrictMode>
);