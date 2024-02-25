package com.example.md_backend.dao;

import com.example.md_backend.entity.MetaData;
import com.example.md_backend.entity.Room;
import com.example.md_backend.entity.User;
import com.example.md_backend.repository.MetaDataRepo;
import com.example.md_backend.repository.RoomRepository;
import com.example.md_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RoomDao {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MetaDataRepo metaDataRepo;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public boolean storeToDbMeeting(Room room) {
        try {



            Room savedRoom = parseRoom(room);

            roomRepository.save(savedRoom);
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    private Room parseRoom(Room room) {
        Room newRoom = new Room();
        try {
            MetaData metaData = new MetaData();
            metaData.setJobRole(room.getMetaData().getJobRole());
            metaData.setJobDesc(room.getMetaData().getJobDesc().substring(0,255));
            metaData.setPdf(room.getMetaData().getPdf() == null ? null: room.getMetaData().getPdf() );
            metaDataRepo.save(metaData);

            List<User> userList = new ArrayList<>();
            for (User user : room.getUsers()) {
                User newUser = new User();
                newUser.setFirstName(user.getFirstName());
                newUser.setLastName(user.getLastName());
                newUser.setRole(user.getRole());
                newUser.setPhoneNumber(user.getPhoneNumber());
                userList.add(newUser); // Add newUser to userList
            }

            userRepository.saveAll(userList);

            newRoom.setUsers(userList); // Set userList to newRoom
            newRoom.setMetaData(metaData);
            newRoom.setScheduledTime(LocalDateTime.now());


        } catch (Exception ex) {
            log.warn(ex.getMessage());
            ex.printStackTrace();
            throw ex;
        }
        return newRoom;
    }
}
