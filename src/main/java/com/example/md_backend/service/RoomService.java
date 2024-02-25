package com.example.md_backend.service;


import com.example.md_backend.dao.RoomDao;
import com.example.md_backend.entity.Room;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class RoomService {


    @Autowired
    RoomDao roomDao;


    public boolean serviceAccessDao(Room room){
        return roomDao.storeToDbMeeting(room);
    }


    public String pdfExtractor(byte[] extractor) throws IOException {

        ByteArrayInputStream bis = new ByteArrayInputStream(extractor);
        PDDocument document = PDDocument.load(bis);

        PDFTextStripper pdfStripper = new PDFTextStripper();

        String text = pdfStripper.getText(document);
        System.out.println("Extracted text:\n" + text);

        // Close the document
        document.close();


        return text;
    }

}
