package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class ClassroomDTO {

    @JsonView(ViewModel.Internal.class)
    String name;

    @JsonView(ViewModel.Internal.class)
    String code;

    @JsonView(ViewModel.Internal.class)
    String schedule;

    @JsonView(ViewModel.Internal.class)
    int grade;

    @JsonView(ViewModel.Internal.class)
    TeacherDTO teacher;
}