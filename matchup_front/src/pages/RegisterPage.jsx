import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

function RegisterPage() {
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch("http://localhost:8765/MATCHUPAUTH/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email, phone, password }),
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "שגיאה בהרשמה");
            }

            setMessage("נרשמת בהצלחה! אפשר להתחבר עכשיו.");
            setTimeout(() => navigate("/login"), 1500);
        } catch (error) {
            console.error(error);
            setMessage("שגיאה: " + error.message);
        }
    };

    return (
        <div style={{
            maxWidth: "400px",
            margin: "60px auto",
            padding: "30px",
            background: "white",
            borderRadius: "12px",
            boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
            direction: "rtl"
        }}>
            <h2 style={{ textAlign: "center", color: "#333" }}>הרשמה</h2>
            <form onSubmit={handleRegister} style={{ display: "grid", gap: "12px" }}>
                <div>
                    <label>אימייל:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        style={{ width: "100%", padding: "8px" }}
                    />
                </div>

                <div>
                    <label>טלפון:</label>
                    <input
                        type="tel"
                        value={phone}
                        onChange={(e) => setPhone(e.target.value)}
                        required
                        style={{ width: "100%", padding: "8px" }}
                    />
                </div>

                <div>
                    <label>סיסמה:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                        style={{ width: "100%", padding: "8px" }}
                    />
                </div>

                <button type="submit" style={{
                    background: "#007AFF",
                    color: "white",
                    border: "none",
                    borderRadius: "6px",
                    padding: "10px",
                    cursor: "pointer"
                }}>
                    הירשם
                </button>
            </form>

            {message && <p style={{ textAlign: "center", color: "#007AFF", marginTop: "10px" }}>{message}</p>}
        </div>
    );
}

export default RegisterPage;