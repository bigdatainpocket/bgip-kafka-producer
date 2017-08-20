package com.bgip.apachekafka.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bgip.apachekafka.services.KafkaProducer;
import com.bgip.apachekafka.storage.MessageStorage;

@RestController
@RequestMapping(value="/jsa/kafka")
public class WebRestController {
	
	@Autowired
	KafkaProducer producer;
	
	@Autowired
	MessageStorage storage;
	
	@GetMapping(value="/producer")
	public String producer(@RequestParam("data")String data){
//		producer.send(data);
		 File localFile = new File("/Users/muthahar/Desktop/kafkaproject/ttt.txt");
			
	

	        int lineCount = 0;
	        FileInputStream fis;
	        BufferedReader br = null;
	        try {
	            fis = new FileInputStream(localFile);
	            //Construct BufferedReader from InputStreamReader
	            br = new BufferedReader(new InputStreamReader(fis));

	            String line = null;
	            while ((line = br.readLine()) != null) {
	                lineCount++;
	                producer.send(line);
	            }

	        } catch (Exception e) {
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
		return "Done";
	}
	
	
	
//	 @PostMapping(value="/upload") // //new annotation since 4.3
//	    public String singleFileUpload(@RequestParam("file") MultipartFile file) {
//
//	        InputStream inputStream=  new BufferedInputStream("file.getInputStream())
//		 File localFile = new File("/Users/muthahar/Desktop/CODE/test.txt");
//		
//		 
//
//	        int lineCount = 0;
//	        FileInputStream fis;
//	        BufferedReader br = null;
//	        try {
//	           InputStream inputStream=  new BufferedInputStream("file.getInputStream())
//	            //Construct BufferedReader from InputStreamReader
//	            br = new BufferedReader(new InputStreamReader(fis));
//
//	            String line = null;
//	            while ((line = br.readLine()) != null) {
//	                lineCount++;
//	                producer.send(line);
//	            }
//
//	        } catch (Exception e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }finally{
//	            try {
//	                br.close();
//	            } catch (IOException e) {
//	                // TODO Auto-generated catch block
//	                e.printStackTrace();
//	            }
//	        }
//
//
//	        return "redirect:/uploadStatus";
//	    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(value="/consumer")
	public String getAllRecievedMessage(){
		String messages = storage.toString();
		storage.clear();
		
		return messages;

	
	}
}
