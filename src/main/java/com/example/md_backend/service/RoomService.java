package com.example.md_backend.service;


import com.example.md_backend.dao.RoomDao;
import com.example.md_backend.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RoomService {


    @Autowired
    RoomDao roomDao;


    public boolean serviceAccessDao(Room room){
        return roomDao.storeToDbMeeting(room);
    }

}
