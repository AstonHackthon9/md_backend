package com.example.md_backend.controller;


import com.example.md_backend.entity.Dsa;
import com.example.md_backend.entity.Room;
import com.example.md_backend.repository.DsaRepo;
import com.example.md_backend.repository.RoomRepository;
import com.example.md_backend.service.RoomService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequestMapping("/schedule")
@RestController
public class RequestController {

    @Autowired
    RoomService service;


    @Autowired
    DsaRepo repo;

    @Autowired
    RoomRepository repository;


//    @GetMapping("/hello")
//    public String sayHello(){
//        return "hello";
//    }


//    @PostMapping("/create-dsa")
//    public void addDsa(@RequestBody  Dsa dsa){
//
//        repo.save(dsa);
//
//    }


    @PostMapping("/random-dsa-question")
    public ResponseEntity<Map<String,String>> randomQuestionGen(){

        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1;

        Optional<Dsa> dsa = repo.findById(randomNumber) ;

        if(dsa.isPresent()){

            Map<String, String> dsaMap = convertDsaToMap(dsa.get());
            return ResponseEntity.ok(dsaMap);

        }

        return ResponseEntity.badRequest().body(null);


    }


//    @PostMapping("/pp")
//    public String transform(@RequestParam("file") MultipartFile file) throws IOException {
//
//        return service.pdfExtractor(file.getBytes());
//    }









    private Map<String, String> convertDsaToMap(Dsa dsa) {
        Map<String, String> dsaMap = new HashMap<>();
        dsaMap.put("id", String.valueOf(dsa.getId()));
        dsaMap.put("question", dsa.getQuestion());
        dsaMap.put("constraints", dsa.getConstraints());
        dsaMap.put("exampleInput1", dsa.getExampleInput1());
        dsaMap.put("exampleInput2", dsa.getExampleInput2());
        dsaMap.put("output1", dsa.getOutput1());
        dsaMap.put("output2", dsa.getOutput2());
        return dsaMap;
    }


    @GetMapping ("/get-all")
    public List<Room> print(){
        return repository.findAll();
    }


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
