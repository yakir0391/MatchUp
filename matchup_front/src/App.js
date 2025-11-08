import React from "react";
import { BrowserRouter as Router, Route, Routes, Link, useLocation } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";
import CreateEventPage from "./pages/CreateEventPage";
import MyEventsPage from "./pages/MyEventsPage";
import RegisterPage from "./pages/RegisterPage";

function Navbar() {
    const location = useLocation();

    return (
        <nav
            style={{
                background: "linear-gradient(90deg, #4A90E2, #007AFF)",
                padding: "15px 30px",
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                color: "white",
                boxShadow: "0 2px 8px rgba(0,0,0,0.15)",
            }}
        >
            <h2 style={{ margin: 0, fontSize: "22px", fontWeight: "bold" }}>⚽ MatchUp</h2>

            <div style={{ display: "flex", gap: "20px" }}>
                <Link
                    to="/"
                    style={{
                        color: location.pathname === "/" ? "#FFD700" : "white",
                        textDecoration: "none",
                        fontWeight: location.pathname === "/" ? "bold" : "normal",
                    }}
                >
                    בית
                </Link>
                <Link
                    to="/login"
                    style={{
                        color: location.pathname === "/login" ? "#FFD700" : "white",
                        textDecoration: "none",
                        fontWeight: location.pathname === "/login" ? "bold" : "normal",
                    }}
                >
                    התחברות
                </Link>
                <Link
                    to="/create-event"
                    style={{
                        color: location.pathname === "/create-event" ? "#FFD700" : "white",
                        textDecoration: "none",
                        fontWeight: location.pathname === "/create-event" ? "bold" : "normal",
                    }}
                >
                    צור משחק
                </Link>
                <Link
                    to="/my-events"
                    style={{
                        color: location.pathname === "/my-events" ? "#FFD700" : "white",
                        textDecoration: "none",
                        fontWeight: location.pathname === "/my-events" ? "bold" : "normal",
                    }}
                >
                    האירועים שלי
                </Link>
            </div>
        </nav>
    );
}

function App() {
    return (
        <Router>
            <Navbar />
            <div style={{ padding: "30px", background: "#f5f7fa", minHeight: "100vh" }}>
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/create-event" element={<CreateEventPage />} />
                    <Route path="/my-events" element={<MyEventsPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
