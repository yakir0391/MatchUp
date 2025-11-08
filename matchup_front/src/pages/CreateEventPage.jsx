import React, { useState, useEffect } from "react";

function CreateEventPage() {
    const [sports, setSports] = useState([]);
    const [cities, setCities] = useState([]);
    const [formData, setFormData] = useState({
        title: "",
        sportName: "",
        cityName: "",
        location: "",
        eventDate: "",
        eventTime: "",
        playersNeeded: "",
        playersJoined: ""
    });
    const [message, setMessage] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const sportRes = await fetch("http://localhost:8765/MATCHUPEVENT/events/sports");
                const sportsData = await sportRes.json();
                setSports(sportsData);

                const cityRes = await fetch("http://localhost:8765/MATCHUPEVENT/events/cities");
                const citiesData = await cityRes.json();
                setCities(citiesData);
            } catch (err) {
                console.error("שגיאה בטעינת נתונים:", err);
            }
        };
        fetchData();
    }, []);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem("token");
            const response = await fetch("http://localhost:8765/MATCHUPEVENT/events/create", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`
                },
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error("שגיאה ביצירת האירוע");
            }

            setMessage(" האירוע נוצר בהצלחה!");
            setFormData({
                title: "",
                sportName: "",
                cityName: "",
                location: "",
                eventDate: "",
                eventTime: "",
                playersNeeded: "",
                playersJoined: ""
            });
        } catch (err) {
            setMessage("error : " + err.message);
        }
    };

    return (
        <div style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            minHeight: "100vh",
            background: "linear-gradient(135deg, #f7f9fc 0%, #e8eef7 100%)",
            fontFamily: "Arial, sans-serif"
        }}>
            <div style={{
                backgroundColor: "white",
                borderRadius: "16px",
                padding: "30px 40px",
                boxShadow: "0 8px 20px rgba(0,0,0,0.1)",
                width: "100%",
                maxWidth: "500px",
            }}>
                <h2 style={{
                    textAlign: "center",
                    color: "#333",
                    marginBottom: "25px"
                }}>
                    צור אירוע חדש
                </h2>

                <form onSubmit={handleSubmit} style={{ display: "grid", gap: "15px" }}>
                    <input
                        type="text"
                        name="title"
                        placeholder="כותרת האירוע"
                        value={formData.title}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />

                    <select
                        name="sportName"
                        value={formData.sportName}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    >
                        <option value="">בחר ספורט</option>
                        {sports.map((sport) => (
                            <option key={sport.id} value={sport.name}>{sport.name}</option>
                        ))}
                    </select>

                    <select
                        name="cityName"
                        value={formData.cityName}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    >
                        <option value="">בחר עיר</option>
                        {cities.map((city) => (
                            <option key={city.id} value={city.name}>{city.name}</option>
                        ))}
                    </select>

                    <input
                        type="text"
                        name="location"
                        placeholder="מיקום"
                        value={formData.location}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />

                    <input
                        type="date"
                        name="eventDate"
                        value={formData.eventDate}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />

                    <input
                        type="time"
                        name="eventTime"
                        value={formData.eventTime}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />

                    <input
                        type="number"
                        name="playersNeeded"
                        placeholder="מספר משתתפים נדרש"
                        value={formData.playersNeeded}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />

                    <input
                        type="number"
                        name="playersJoined"
                        placeholder="משתתפים שהצטרפו"
                        value={formData.playersJoined}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />

                    <button
                        type="submit"
                        style={{
                            backgroundColor: "#4a90e2",
                            color: "white",
                            padding: "12px",
                            border: "none",
                            borderRadius: "8px",
                            fontSize: "16px",
                            cursor: "pointer",
                            transition: "0.3s"
                        }}
                        onMouseOver={(e) => e.target.style.backgroundColor = "#357ABD"}
                        onMouseOut={(e) => e.target.style.backgroundColor = "#4a90e2"}
                    >
                        צור אירוע
                    </button>
                </form>

                {message && (
                    <p style={{
                        textAlign: "center",
                        marginTop: "20px",
                        color: message.includes("✅") ? "green" : "red",
                        fontWeight: "bold"
                    }}>
                        {message}
                    </p>
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
    transition: "0.2s",
};

export default CreateEventPage;