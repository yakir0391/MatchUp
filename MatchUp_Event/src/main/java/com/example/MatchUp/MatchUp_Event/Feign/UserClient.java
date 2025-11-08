package com.example.MatchUp.MatchUp_Event.Feign;

import com.example.MatchUp.MatchUp_Event.Model.UserIdAndPhone;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "MATCHUPAUTH")
public interface UserClient {

    @GetMapping("/auth/userId")
    Long getUserIdByEmail(@RequestParam String email);

    @GetMapping("/auth/phoneNumber")
    String getPhoneNumberByEmail(@RequestParam String email);

    @GetMapping("/auth/IdPhone")
    List<UserIdAndPhone> getIdPhone();
}
