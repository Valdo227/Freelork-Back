package com.csprojectback.freelork.service.imp;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.csprojectback.freelork.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImp implements CloudinaryService {

    Cloudinary cloudinary;

    public CloudinaryServiceImp(){
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name","fvkhteam");
        valuesMap.put("api_key","396642652437161");
        valuesMap.put("api_secret","wfqkB2bo-pDNwTiZ2Wph-rxSOKM");
        cloudinary = new Cloudinary(valuesMap);
    }

    @Override
    public Map uploadFile(MultipartFile multipartFile, String folder) throws IOException{
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.asMap(
                "folder",folder,
                "resource_type", "auto"));
        file.delete();
        return result;
    }

    @Override
    public Map deleteFile(String id) throws IOException{
        return cloudinary.uploader().destroy(id,ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        FileOutputStream fileOutput = new FileOutputStream(file);
        fileOutput.write(multipartFile.getBytes());
        fileOutput.close();
        return file;
    }

}
