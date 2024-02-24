package com.example.md_backend.controller;


import com.example.md_backend.entity.Room;
import com.example.md_backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequestMapping("/schedule")
@RestController
public class RequestController {

    @Autowired
    RoomService service;


//    @GetMapping("/hello")
//    public String sayHello(){
//        return "hello";
//    }


    @PostMapping("/create-meeting")
    public ResponseEntity<Map<String, Boolean>> createMeeting(
            @RequestBody Room room

    ){

        boolean ans = false;

        if(room != null && room.getUsers() != null && room.getMetaData() != null ){
            ans = service.serviceAccessDao(room);
        }




        if(!ans){
            return ResponseEntity.badRequest().body(Map.of("response",ans));
        }


        return ResponseEntity.ok().body(Map.of("response",ans));


    }
}
