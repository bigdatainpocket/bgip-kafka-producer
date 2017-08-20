package com.bgip.apachekafka.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.bgip.apachekafka.storage.MessageStorage;


@Controller
public class KafkaRestContorller {

	List<String> files = new ArrayList<String>();
	
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "F://temp//";

	
	@Autowired
	MessageStorage storageService;
	
	
	
	  @RequestMapping("/upload")
	    public String uploading(Model model) {
	        File file = new File(UPLOADED_FOLDER);
	        model.addAttribute("files", file.listFiles());
	        return "uploading";
	    }

	    @RequestMapping(value = "/upload", method = RequestMethod.POST)
	    public String uploadingPost(@RequestParam("uploadingFiles") MultipartFile[] uploadingFiles) throws IOException {
	        for(MultipartFile uploadedFile : uploadingFiles) {
	            File file = new File(UPLOADED_FOLDER + uploadedFile.getOriginalFilename());
	            for( MultipartFile mfile : uploadingFiles){
	            	storageService.fileConverter(mfile);
	            }
	            
	            uploadedFile.transferTo(file);
	        }

	        return "redirect:/";
	    }
	
	
	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@PostMapping("/")
//	public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
//		try {
//			storageService.store(file);
//			model.addAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
//			files.add(file.getOriginalFilename());
//		} catch (Exception e) {
//			model.addAttribute("message", "FAIL to upload " + file.getOriginalFilename() + "!");
//		}
//		return "uploadForm";
//	}
// 
//	@GetMapping("/gellallfiles")
//	public String getListFiles(Model model) {
//		model.addAttribute("files",
//				files.stream()
//						.map(fileName -> MvcUriComponentsBuilder
//								.fromMethodName(KafkaRestContorller.class, "getFile", fileName).build().toString())
//						.collect(Collectors.toList()));
//		model.addAttribute("totalFiles", "TotalFiles: " + files.size());
//		return "listFiles";
//	}
// 
//	
//	
//	
//	@GetMapping("/files/{filename:.+}")
//	@ResponseBody
//	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//		Resource file = storageService.loadFile(filename);
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//				.body(file);
//	}
//}
//	
//	
	
	
	
	
