package com.example.Newsternews.DatabaseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User userData) {
        System.out.println(userData.toString());
        User requestUser = userRepository.findByToken(userData.getToken());

        System.out.println(userData.getEmail());
        System.out.println(userData.getToken());
        System.out.println(userData.getFrequency());
        System.out.println(userData.getTopics());

        requestUser.setFrequency(userData.getFrequency());
        requestUser.setTopics(userData.getTopics());

        userRepository.save(requestUser);
        HttpHeaders headers = new HttpHeaders();
        String responseString = "{\"Correct\": \"Yes\"}";
        // return Json
        return new ResponseEntity<>(responseString, headers, HttpStatus.OK);
    }
}