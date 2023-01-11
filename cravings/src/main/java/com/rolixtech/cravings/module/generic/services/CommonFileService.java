package com.rolixtech.cravings.module.generic.services;

/*import com.example.filedemo.exception.FileStorageException;
import com.example.filedemo.exception.MyFileNotFoundException;
import com.example.filedemo.property.FileStorageProperties;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CommonFileService {

    private  Path fileStorageLocation ;

  

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
    	System.out.println("fileName "+fileName);
        Path filePath;
		Resource resource = null ;
		try {
			filePath = Paths.get(StringUtils.cleanPath(GenericUtility.getFileDirectoryPath() + File.separator + "common" + File.separator + fileName));
			 resource = new UrlResource(filePath.toUri());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		if(resource.exists()) {
		    return resource;
		} else {
		    throw new CustomFileNotFoundException("File not found " + fileName);
		}
    }
}


