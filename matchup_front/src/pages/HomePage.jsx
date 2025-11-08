import React, { useEffect, useState } from "react";

function HomePage() {
    const [events, setEvents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchEvents = async () => {
            try {
                const response = await fetch("http://localhost:8765/MATCHUPEVENT/events");
                if (!response.ok) {
                    throw new Error("שגיאה בטעינת אירועים");
                }
                const data = await response.json();
                setEvents(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };

        fetchEvents();
    }, []);

    if (loading) return <p style={{ textAlign: "center", marginTop: "50px" }}>טוען אירועים...</p>;
    if (error) return <p style={{ color: "red", textAlign: "center", marginTop: "50px" }}>שגיאה: {error}</p>;

    return (
        <div
            style={{
                minHeight: "100vh",
                backgroundColor: "#f5f7fa",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                padding: "40px 20px",
            }}
        >
            <div
                style={{
                    background: "white",
                    padding: "20px 40px",
                    borderRadius: "16px",
                    boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
                    marginBottom: "30px",
                }}
            >
                <h1 style={{ textAlign: "center", color: "#333" }}>🎯 אירועים זמינים</h1>
            </div>

            <div
                style={{
                    display: "grid",
                    gridTemplateColumns: "repeat(auto-fit, minmax(300px, 1fr))",
                    gap: "20px",
                    width: "100%",
                    maxWidth: "1200px",
                }}
            >
                {events.map((event) => (
                    <div
                        key={event.id}
                        style={{
                            backgroundColor: "white",
                            borderRadius: "12px",
                            padding: "20px",
                            boxShadow: "0 2px 8px rgba(0,0,0,0.08)",
                            transition: "transform 0.2s ease, box-shadow 0.2s ease",
                            cursor: "pointer",
                        }}
                        onMouseEnter={(e) => {
                            e.currentTarget.style.transform = "translateY(-5px)";
                            e.currentTarget.style.boxShadow = "0 4px 12px rgba(0,0,0,0.15)";
                        }}
                        onMouseLeave={(e) => {
                            e.currentTarget.style.transform = "translateY(0)";
                            e.currentTarget.style.boxShadow = "0 2px 8px rgba(0,0,0,0.08)";
                        }}
                    >
                        <h3 style={{ color: "#222", marginBottom: "10px" }}>{event.title}</h3>
                        <p><strong>🏙 עיר:</strong> {event.cityName}</p>
                        <p><strong>🏐 ספורט:</strong> {event.sportName}</p>
                        <p><strong>📍 מיקום:</strong> {event.location}</p>
                        <p><strong>📅 תאריך:</strong> {event.date}</p>
                        <p><strong>⏰ שעה:</strong> {event.time}</p>
                        <p><strong>👥 שחקנים:</strong> {event.joinedPlayers}/{event.requiredPlayers}</p>
                        <p><strong>📞 מארגן:</strong> {event.organizerPhone}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default HomePage;