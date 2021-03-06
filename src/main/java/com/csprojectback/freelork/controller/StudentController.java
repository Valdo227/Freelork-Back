package com.csprojectback.freelork.controller;

import com.csprojectback.freelork.dto.*;
import com.csprojectback.freelork.exception.BusinessException;
import com.csprojectback.freelork.model.ViewModel;
import com.csprojectback.freelork.service.StudentService;
import com.fasterxml.jackson.annotation.JsonView;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.csprojectback.freelork.constants.AuthConstants.URL_PRIVATE_AUTHENTICATION_BASE;

@RestController
@RequestMapping(URL_PRIVATE_AUTHENTICATION_BASE +"student/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS })
public class StudentController {

    @Autowired
    StudentService studentService;

    @PutMapping("update")
    public ResponseEntity<Message> updateStudent(@RequestParam(name = "file" ,required = false) MultipartFile file, StudentDTO studentDTO){
        try {
            studentService.updateStudent(file,studentDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @GetMapping("{id}")
    @JsonView(ViewModel.Internal.class)
    public StudentDTO getStudent(@PathVariable("id") int id){
        return studentService.getStudent(id);
    }

    @GetMapping("summary/{id}")
    public SummaryStudentDTO getSummary(@PathVariable("id") int id){
        return studentService.getSummary(id);
    }

    @GetMapping("projects/{id}")
    @JsonView(ViewModel.Internal.class)
    public List<ProjectDTO> getProjects(@PathVariable("id") int id){
        return studentService.getProjects(id);
    }


    @PostMapping("{idStudent}/project/{idProject}")
    public JSONObject setProject(@PathVariable("idStudent")int idStudent,@PathVariable("idProject") int idProject){
        try {
            JSONObject json = new JSONObject();
            studentService.setProject(idStudent,idProject);
            json.put("Status", "200");
            return json;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.EXPECTATION_FAILED, "StudentController");
        }
    }

    @PutMapping("delete/{idStudent}/project/{idProject}")
    public JSONObject deleteProject(@PathVariable("idStudent")int idStudent,@PathVariable("idProject") int idProject){
        try {
            JSONObject json = new JSONObject();
            studentService.deleteProject(idStudent,idProject);
            json.put("Status", "200");
            return json;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.EXPECTATION_FAILED, "StudentController");
        }
    }

    @GetMapping("companies")
    public List<CompanyProfileDTO> getCompanies(){
        return studentService.getCompanies();
    }

    @PostMapping("{idStudent}/company/{idCompany}")
    public JSONObject setCompany(@PathVariable("idStudent")int idStudent,@PathVariable("idCompany") int idCompany){
        try {
            JSONObject json = new JSONObject();
            studentService.setCompany(idStudent,idCompany);
            json.put("Status", "200");
            return json;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.EXPECTATION_FAILED, "StudentController");
        }
    }

    @PutMapping("delete/{idStudent}/company/{idCompany}")
    public JSONObject deleteCompany(@PathVariable("idStudent")int idStudent,@PathVariable("idCompany") int idCompany){
        try {
            JSONObject json = new JSONObject();
            studentService.deleteCompany(idStudent,idCompany);
            json.put("Status", "200");
            return json;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), HttpStatus.EXPECTATION_FAILED, "StudentController");
        }
    }

    @GetMapping("projects/company/{id}")
    @JsonView(ViewModel.Internal.class)
    public List<ProjectRegistersDTO> getProjectsCompany(@PathVariable("id") int id){
        return studentService.getProjectsCompany(id);
    }

    @GetMapping("classroom/{id}")
    @JsonView(ViewModel.Internal.class)
    public ClassroomDTO getClassroom(@PathVariable("id") int id){
        return studentService.getClassroom(id);
    }

    @PostMapping("{idStudent}/classroom/{code}")
    public ResponseEntity<Message> setClassroom(@PathVariable("idStudent")int idStudent,@PathVariable("code") String code){
        try {
            studentService.setClassroom(idStudent,code);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @PutMapping("delete/classroom/{id}")
    @JsonView(ViewModel.Internal.class)
    public ResponseEntity<Message> deleteClassroom(@PathVariable("id") int id){
        try {
            studentService.deleteClassroom(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Ok"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new Message(e.getMessage()));
        }
    }

    @GetMapping("company/{id}")
    @JsonView(ViewModel.Internal.class)
    public CompanyDTO getCompany(@PathVariable("id") int id){
        return studentService.getCompany(id);
    }

    @GetMapping("profile/{id}")
    @JsonView(ViewModel.Internal.class)
    public StudentProfileDTO getProfile(@PathVariable("id") int id){
        return studentService.getProfile(id);
    }

}
