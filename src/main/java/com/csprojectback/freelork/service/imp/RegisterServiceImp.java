package com.csprojectback.freelork.service.imp;

import com.csprojectback.freelork.dto.RegisterCompanyDTO;
import com.csprojectback.freelork.dto.RegisterDTO;
import com.csprojectback.freelork.entity.*;
import com.csprojectback.freelork.repository.ProjectRepository;
import com.csprojectback.freelork.repository.RegisterRepository;
import com.csprojectback.freelork.repository.StudentRepository;
import com.csprojectback.freelork.repository.UserRepository;
import com.csprojectback.freelork.service.CloudinaryService;
import com.csprojectback.freelork.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImp implements RegisterService {

    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    StudentRepository studentRepository;

   @Autowired
   UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void createRegister(MultipartFile multipartFile, RegisterDTO registerDTO) throws IOException {

        RegisterEntity registerEntity = new RegisterEntity();
        UserEntity userEntity = userRepository.findById(registerDTO.getIdUser());
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        ProjectEntity projectEntity = projectRepository.findById(registerDTO.getIdProject());

        if(!String.valueOf(registerDTO.getId()).equals("0")){
            RegisterEntity registerSaved = registerRepository.getById(registerDTO.getId());

            registerEntity.setId(registerDTO.getId());
            registerEntity.setDateCreated(registerSaved.getDateCreated());

            if(multipartFile == null && !registerDTO.getImageId().equals("null")  && registerDTO.getImageUrl().equals("null") ){
                cloudinaryService.deleteFile(registerDTO.getImageId());
                registerEntity.setImageId(null);
                registerEntity.setImageUrl(null);

            }
            else if(!registerDTO.getImageId().equals("null") && multipartFile != null) {
                cloudinaryService.deleteFile(registerDTO.getImageId());
                Map result = cloudinaryService.uploadFile(multipartFile, "/register");
                registerEntity.setImageId(result.get("public_id").toString());
                registerEntity.setImageUrl(result.get("url").toString());
            }
            else if(multipartFile != null) {
                Map result = cloudinaryService.uploadFile(multipartFile, "/register");
                registerEntity.setImageId(result.get("public_id").toString());
                registerEntity.setImageUrl(result.get("url").toString());
            }else{
                registerEntity.setImageId(registerSaved.getImageId());
                registerEntity.setImageUrl(registerSaved.getImageUrl());

            }
        }
        else {
            registerEntity.setDateCreated(LocalDateTime.now());
            projectEntity.setDateUpdated(LocalDateTime.now());

            if (multipartFile != null) {
                Map result = cloudinaryService.uploadFile(multipartFile, "/register");
                registerEntity.setImageId(result.get("public_id").toString());
                registerEntity.setImageUrl(result.get("secure_url").toString());
            }
        }

        registerEntity.setDateRegister(LocalDate.parse(registerDTO.getDateRegister(), format));
        registerEntity.setTimeRegister(registerDTO.getTimeRegister());
        registerEntity.setTitle(registerDTO.getTitle());
        registerEntity.setDescription((registerDTO.getDescription().equals("")) ? null: registerDTO.getDescription());

        registerEntity.setStatus(registerDTO.getStatus());
        registerEntity.setDateUpdated(LocalDateTime.now());
        registerEntity.setStudentEntity(studentEntity);
        registerEntity.setProjectEntity(projectEntity);

        registerRepository.save(registerEntity);
    }

    @Override
    public RegisterDTO getRegister(int id) {
        RegisterEntity registerEntity = registerRepository.findById(id);
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setId(registerEntity.getId());
        registerDTO.setTitle(registerEntity.getTitle());
        registerDTO.setStudent(registerEntity.getStudentEntity().getUserEntity().getFullName());
        registerDTO.setDescription(registerEntity.getDescription());
        registerDTO.setDateRegister(registerEntity.getDateRegister().format(format));
        registerDTO.setTimeRegister(registerEntity.getTimeRegister());
        registerDTO.setStatus(registerEntity.getStatus());
        registerDTO.setIdProject(registerEntity.getProjectEntity().getId());
        registerDTO.setNameProject(registerEntity.getProjectEntity().getName());
        registerDTO.setImageId(registerEntity.getImageId());
        registerDTO.setImageUrl(registerEntity.getImageUrl());


        return registerDTO;
    }

    @Override
    public List<RegisterDTO> getRegisterList(int id){
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        List<RegisterDTO> registerDTOS = new ArrayList<>();

        for (RegisterEntity registerEntity : registerRepository.findByStudentEntityAndStatusNotOrderByIdDesc(studentEntity,0)) {
                registerBaseDTO(registerDTOS, registerEntity);
        }
        return registerDTOS;
    }

    @Override
    public List<RegisterDTO> getRegisterListDate(int id, String date1, String date2) {
        UserEntity userEntity = userRepository.findById(id);
        StudentEntity studentEntity = studentRepository.findByUserEntity(userEntity);
        List<RegisterDTO> registerDTOS = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(date1, format);
        LocalDate endDate = LocalDate.parse(date2, format);

        for (RegisterEntity registerEntity: registerRepository.findByStudentEntityAndStatusNotAndDateRegisterBetweenOrderByIdDesc(studentEntity, 0, startDate,endDate)){
                registerBaseDTO(registerDTOS, registerEntity);
        }
        return registerDTOS;
    }


    @Override
    public void deleteRegister(int id) {
        RegisterEntity registerEntity = registerRepository.findById(id);

        registerEntity.setStatus(0);
        registerRepository.save(registerEntity);
    }

    static void registerBaseDTO(List<RegisterDTO> registerDTOS, RegisterEntity registerEntity) {
        RegisterDTO registerDTO = new RegisterDTO();

        registerDTO.setId(registerEntity.getId());
        registerDTO.setTitle(registerEntity.getTitle());
        registerDTO.setDateRegister(registerEntity.getDateRegister().format(format));
        registerDTO.setDescription(registerEntity.getDescription());
        registerDTO.setTimeRegister(registerEntity.getTimeRegister());
        registerDTO.setIdProject(registerEntity.getProjectEntity().getId());
        registerDTO.setNameProject(registerEntity.getProjectEntity().getName());
        registerDTO.setStatus(registerEntity.getStatus());

        registerDTOS.add(registerDTO);
    }


}
