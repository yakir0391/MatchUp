package com.example.MatchUp.MatchUp_Event.Model;

import javax.xml.crypto.Data;
import java.util.Date;

public class EventDetails {
    private Long id;
    private String title;
    private String location;
    private Date date;
    private String time;
    private Integer joinedPlayers;
    private Integer requiredPlayers;
    private String organizerPhone;
    private String sportName;
    private String cityName;

    public EventDetails(Long id, String title, String location, Date date, String time, Integer joinedPlayers,
                        Integer requiredPlayers, String organizerPhone, String sportName, String cityName) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.time = time;
        this.joinedPlayers = joinedPlayers;
        this.requiredPlayers = requiredPlayers;
        this.organizerPhone = organizerPhone;
        this.sportName = sportName;
        this.cityName = cityName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getJoinedPlayers() {
        return joinedPlayers;
    }

    public void setJoinedPlayers(Integer joinedPlayers) {
        this.joinedPlayers = joinedPlayers;
    }

    public Integer getRequiredPlayers() {
        return requiredPlayers;
    }

    public void setRequiredPlayers(Integer requiredPlayers) {
        this.requiredPlayers = requiredPlayers;
    }

    public String getOrganizerPhone() {
        return organizerPhone;
    }

    public void setOrganizerPhone(String organizerPhone) {
        this.organizerPhone = organizerPhone;
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
}
