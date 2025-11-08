import React, { useEffect, useState } from "react";
import axios from "axios";

export default function MyEventsPage() {
    const [events, setEvents] = useState([]);
    const [editingEvent, setEditingEvent] = useState(null);
    const [formData, setFormData] = useState({});
    const [sports, setSports] = useState([]);
    const [cities, setCities] = useState([]);
    const token = localStorage.getItem("token");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [sportsRes, citiesRes] = await Promise.all([
                    axios.get("http://localhost:8765/MATCHUPEVENT/sports"),
                    axios.get("http://localhost:8765/MATCHUPEVENT/cities"),
                ]);
                setSports(sportsRes.data);
                setCities(citiesRes.data);
            } catch (err) {
                console.error("Failed to load sports or cities", err);
            }
        };
        fetchData();
    }, []);


    useEffect(() => {
        const fetchMyEvents = async () => {
            try {
                const response = await axios.get("http://localhost:8765/MATCHUPEVENT/events/my-events", {
                    method: "GET",
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setEvents(response.data);
            } catch (err) {
                console.error("Failed to load events", err);
            }
        };
        fetchMyEvents();
    }, [token]);

    const handleDeleteUser = async () => {

        if (!token) {
            alert("לא נמצא משתמש מחובר.");
            return;
        }

        if (!window.confirm("האם אתה בטוח שברצונך למחוק את המשתמש שלך וכל האירועים שלו?")) return;

        try {
            const response = await axios.delete("http://localhost:8765/MATCHUPAUTH/auth", {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
            });

            if (response.status === 200) {
                alert("המשתמש וכל האירועים הקשורים אליו נמחקו בהצלחה!");
                localStorage.clear();
                window.location.href = "/";
            } else {
                alert("שגיאה במחיקת המשתמש");
            }
        } catch (err) {
            console.error("Error deleting user:", err);
            alert("שגיאה: " + (err.response?.data?.message || err.message));
        }
    };

    const handleEdit = (event) => {
        setEditingEvent(event);
        setFormData({
            title: event.title,
            sportName: event.sportName,
            cityName: event.cityName,
            location: event.location,
            eventDate: event.eventDate,
            eventTime: event.eventTime,
            playersNeeded: event.playersNeeded,
            playersJoined: event.playersJoined,
        });
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleUpdate = async () => {
        try {
            await axios.put(`http://localhost:8765/MATCHUPEVENT/events/${editingEvent.id}`, formData, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            alert("Event updated successfully!");
            setEditingEvent(null);
            window.location.reload();
        } catch (err) {
            console.error("Failed to update event", err);
            alert("Failed to update event");
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm("האם אתה בטוח שברצונך למחוק את האירוע?")) return;

        try {
            await axios.delete(`http://localhost:8765/MATCHUPEVENT/events/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            alert("האירוע נמחק בהצלחה!");
            setEvents(events.filter((e) => e.id !== id)); // מעדכן את הרשימה מבלי לטעון מחדש
        } catch (err) {
            console.error("Failed to delete event", err);
            alert("נכשל במחיקת האירוע");
        }
    };

    return (


        <div
            style={{
                minHeight: "100vh",
                background: "linear-gradient(135deg, #f7f9fc, #e8eef7)",
                padding: "40px 20px",
                fontFamily: "Arial, sans-serif",
            }}
        >
            <div
                style={{
                    maxWidth: "900px",
                    margin: "0 auto",
                    background: "white",
                    borderRadius: "16px",
                    boxShadow: "0 8px 20px rgba(0,0,0,0.1)",
                    padding: "30px",
                }}
            >

                <div style={{ display: "flex", justifyContent: "flex-start", marginBottom: "20px" }}>
                    <button
                        onClick={handleDeleteUser}
                        style={{
                            background: "#e74c3c",
                            color: "white",
                            border: "none",
                            borderRadius: "8px",
                            padding: "10px 16px",
                            cursor: "pointer",
                            fontSize: "15px",
                            fontWeight: "bold",
                            transition: "0.3s",
                            boxShadow: "0 3px 8px rgba(0,0,0,0.1)",
                        }}
                        onMouseOver={(e) => (e.target.style.background = "#c0392b")}
                        onMouseOut={(e) => (e.target.style.background = "#e74c3c")}
                    >
                        🗑️ מחק משתמש
                    </button>
                </div>


                <h2
                    style={{
                        textAlign: "center",
                        color: "#333",
                        marginBottom: "30px",
                    }}
                >
                    האירועים שלי
                </h2>

                {/* רשימת אירועים */}
                {!editingEvent && (
                    <div style={{ display: "flex", flexDirection: "column", gap: "15px" }}>
                        {events.length === 0 ? (
                            <p style={{ textAlign: "center", color: "#777" }}>לא נמצאו אירועים.</p>
                        ) : (
                            events.map((event) => (
                                <div
                                    key={event.id}
                                    style={{
                                        border: "1px solid #ddd",
                                        borderRadius: "12px",
                                        padding: "15px",
                                        background: "#f9f9f9",
                                        transition: "0.2s",
                                        boxShadow: "0 2px 5px rgba(0,0,0,0.05)",
                                    }}
                                    onMouseOver={(e) => (e.currentTarget.style.transform = "scale(1.02)")}
                                    onMouseOut={(e) => (e.currentTarget.style.transform = "scale(1)")}
                                >
                                    <h3 style={{ marginBottom: "8px", color: "#333" }}>{event.title}</h3>
                                    <p style={{ margin: "4px 0" }}>
                                        <strong>🏟️ ספורט:</strong> {event.sportName}
                                    </p>
                                    <p style={{ margin: "4px 0" }}>
                                        <strong>📍 עיר:</strong> {event.cityName}
                                    </p>
                                    <p style={{ margin: "4px 0" }}>
                                        <strong>📅 תאריך:</strong> {event.date} | <strong>⏰ שעה:</strong> {event.time}
                                    </p>
                                    <p style={{ margin: "4px 0" }}>
                                        <strong>👥 משתתפים:</strong> {event.joinedPlayers}/{event.requiredPlayers}
                                    </p>
                                    <button
                                        onClick={() => handleEdit(event)}
                                        style={{
                                            marginTop: "10px",
                                            background: "#4a90e2",
                                            color: "white",
                                            border: "none",
                                            borderRadius: "8px",
                                            padding: "8px 14px",
                                            cursor: "pointer",
                                            transition: "0.3s",
                                        }}
                                        onMouseOver={(e) => (e.target.style.background = "#357ABD")}
                                        onMouseOut={(e) => (e.target.style.background = "#4a90e2")}
                                    >
                                        ערוך אירוע
                                    </button>

                                    <button
                                        onClick={() => handleDelete(event.id)}
                                        style={{
                                            background: "#e74c3c",
                                            color: "white",
                                            border: "none",
                                            borderRadius: "8px",
                                            padding: "8px 14px",
                                            cursor: "pointer",
                                        }}
                                        onMouseOver={(e) => (e.target.style.background = "#c0392b")}
                                        onMouseOut={(e) => (e.target.style.background = "#e74c3c")}
                                    >
                                        מחק אירוע
                                    </button>
                                </div>
                            ))
                        )}
                    </div>
                )}

                {/* טופס עריכה */}
                {editingEvent && (
                    <div
                        style={{
                            marginTop: "20px",
                            display: "flex",
                            flexDirection: "column",
                            gap: "10px",
                            maxWidth: "500px",
                            marginInline: "auto",
                        }}
                    >
                        <h3 style={{ textAlign: "center" }}>ערוך אירוע: {editingEvent.title}</h3>

                        <input name="title" value={formData.title} onChange={handleChange} placeholder="כותרת" style={inputStyle} />

                        <select name="sportName" value={formData.sportName} onChange={handleChange} style={inputStyle}>
                            <option value="">בחר ספורט</option>
                            {sports.map((sport) => (
                                <option key={sport.id} value={sport.name}>
                                    {sport.name}
                                </option>
                            ))}
                        </select>

                        <select name="cityName" value={formData.cityName} onChange={handleChange} style={inputStyle}>
                            <option value="">בחר עיר</option>
                            {cities.map((city) => (
                                <option key={city.id} value={city.name}>
                                    {city.name}
                                </option>
                            ))}
                        </select>

                        <input name="location" value={formData.location} onChange={handleChange} placeholder="מיקום" style={inputStyle} />
                        <input type="date" name="eventDate" value={formData.eventDate} onChange={handleChange} style={inputStyle} />
                        <input type="time" name="eventTime" value={formData.eventTime} onChange={handleChange} style={inputStyle} />
                        <input type="number" name="playersNeeded" value={formData.playersNeeded} onChange={handleChange} placeholder="נדרשים" style={inputStyle} />
                        <input type="number" name="playersJoined" value={formData.playersJoined} onChange={handleChange} placeholder="הצטרפו" style={inputStyle} />

                        <div style={{ display: "flex", justifyContent: "center", gap: "10px", marginTop: "10px" }}>
                            <button onClick={handleUpdate} style={btnPrimary}>שמור</button>
                            <button onClick={() => setEditingEvent(null)} style={btnCancel}>ביטול</button>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
}

const inputStyle = {
    padding: "10px 12px",
    borderRadius: "8px",
    border: "1px solid #ccc",
    fontSize: "14px",
    outline: "none",
};

const btnPrimary = {
    background: "#4a90e2",
    color: "white",
    padding: "10px 18px",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    transition: "0.3s",
};

const btnCancel = {
    background: "#ccc",
    color: "#333",
    padding: "10px 18px",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    transition: "0.3s",
};