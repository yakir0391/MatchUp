package com.example.MatchUp.MatchUp_Event.Model;

public class UserIdAndPhone {

    private Long userId;
    private String phone;

    public UserIdAndPhone(Long userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserIdAndPhone{" +
                "userId=" + userId +
                ", phone='" + phone + '\'' +
                '}';
    }
}
