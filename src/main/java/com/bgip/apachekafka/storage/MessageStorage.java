package com.bgip.apachekafka.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bgip.apachekafka.services.KafkaProducer;

@Component
public class MessageStorage {
	
	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get("upload-dir");
	
	int lineCount = 0;
    FileInputStream fis;
    BufferedReader br = null;
	
	@Autowired
	KafkaProducer producer;
	
	
	public void store(MultipartFile file){
		try {
		
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
        	throw new RuntimeException("FAIL!");
        }
	}
	
	
	public void fileConverter(MultipartFile file) {
//		fis = new FileInputStream(file);
        //Construct BufferedReader from InputStreamReader
		try{
        br = new BufferedReader(new InputStreamReader(file.getInputStream()));
        
        String line = null;
        while ((line = br.readLine()) != null) {
            lineCount++;
            producer.send(line);
        }
        
		}catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }	
        }
	
	}
	
	public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
        	throw new RuntimeException("FAIL!");
        }
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private List<String> storage = new ArrayList<String>();
	
	public void put(String message){
		storage.add(message);
	}
	
	public String toString(){
		StringBuffer info = new StringBuffer();
		storage.forEach(msg->info.append(msg).append("<br/>"));
		return info.toString();
	}
	
	public void clear(){
		storage.clear();
	}

	
}
	
	
	
