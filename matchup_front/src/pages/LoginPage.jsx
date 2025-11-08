import React, { useState } from "react";
import { Link } from "react-router-dom";

function LoginPage() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [message, setMessage] = useState("");

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await fetch("http://localhost:8765/MATCHUPAUTH/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) {
                throw new Error("שגיאה בהתחברות");
            }

            const data = await response.json();
            const token = data.token;


            localStorage.setItem("token", token);

            setMessage("התחברת בהצלחה!");
        } catch (error) {
            console.error(error);
            setMessage("שגיאה: " + error.message);
        }
    };

    return (
        <div style={styles.container}>
            <div style={styles.card}>
                <h1 style={styles.title}>MatchUp ברוך הבא ל </h1>
                <p style={styles.subtitle}>התחבר כדי להמשיך</p>
                <form onSubmit={handleLogin} style={styles.form}>
                    <div style={styles.inputGroup}>
                        <label style={styles.label}>אימייל</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            style={styles.input}
                            placeholder="example@email.com"
                        />
                    </div>

                    <div style={styles.inputGroup}>
                        <label style={styles.label}>סיסמה</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            style={styles.input}
                            placeholder="••••••••"
                        />
                    </div>

                    <button type="submit" style={styles.button}>
                        התחבר
                    </button>
                </form>

                {message && <p style={{ textAlign: "center", color: "#007AFF", marginTop: "10px" }}>{message}</p>}

                <p style={{ textAlign: "center", marginTop: "15px" }}>
                    אין לך משתמש?{" "}
                    <Link to="/register" style={{ color: "#007AFF", textDecoration: "none" }}>
                        להרשמה
                    </Link>
                </p>
            </div>
        </div>
    );
}

const styles = {
    container: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        background: "linear-gradient(135deg, #2196f3, #21cbf3)",
        fontFamily: "Heebo, sans-serif",
    },
    card: {
        background: "#fff",
        padding: "40px 30px",
        borderRadius: "12px",
        boxShadow: "0 4px 20px rgba(0,0,0,0.15)",
        width: "100%",
        maxWidth: "400px",
        textAlign: "center",
    },
    title: {
        marginBottom: "10px",
        color: "#1976d2",
        fontSize: "28px",
        fontWeight: "700",
    },
    subtitle: {
        marginBottom: "30px",
        color: "#555",
        fontSize: "16px",
    },
    form: {
        display: "flex",
        flexDirection: "column",
        gap: "15px",
    },
    inputGroup: {
        textAlign: "right",
    },
    label: {
        display: "block",
        fontWeight: "500",
        marginBottom: "5px",
        color: "#333",
    },
    input: {
        width: "100%",
        padding: "10px 12px",
        borderRadius: "8px",
        border: "1px solid #ccc",
        outline: "none",
        fontSize: "14px",
        transition: "border 0.3s",
    },
    button: {
        background: "#1976d2",
        color: "#fff",
        border: "none",
        padding: "12px",
        borderRadius: "8px",
        cursor: "pointer",
        fontSize: "16px",
        fontWeight: "600",
        transition: "background 0.3s",
    },
    message: {
        marginTop: "20px",
        fontSize: "14px",
        fontWeight: "500",
    },
};

export default LoginPage;