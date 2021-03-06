package com.csprojectback.freelork.service;

import com.csprojectback.freelork.dto.RegisterDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RegisterService {

    void createRegister(MultipartFile multipartFile, RegisterDTO registerDTO) throws IOException;

    RegisterDTO getRegister(int id);

    List<RegisterDTO> getRegisterList(int id);

    List<RegisterDTO> getRegisterListDate(int id, String date1, String date2);

    void deleteRegister(int id);

}
