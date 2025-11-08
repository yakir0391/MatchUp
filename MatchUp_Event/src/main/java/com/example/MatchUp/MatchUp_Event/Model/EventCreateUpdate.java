package com.example.MatchUp.MatchUp_Event.Model;

import java.util.Date;

public class EventCreateUpdate {
    private String title;
    private String sportName;
    private String cityName;
    private String location;
    private Date eventDate;
    private String eventTime;
    private int playersNeeded;
    private int playersJoined;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getPlayersNeeded() {
        return playersNeeded;
    }

    public void setPlayersNeeded(int playersNeeded) {
        this.playersNeeded = playersNeeded;
    }

    public int getPlayersJoined() {
        return playersJoined;
    }

    public void setPlayersJoined(int playersJoined) {
        this.playersJoined = playersJoined;
    }
}
