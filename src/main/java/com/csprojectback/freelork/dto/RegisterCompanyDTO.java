package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterCompanyDTO {
    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    private String title;

    @JsonView(ViewModel.Internal.class)
    String dateCreated;

    @JsonView(ViewModel.Internal.class)
    int idUser;

    @JsonView(ViewModel.Internal.class)
    String nameStudent;

    @JsonView(ViewModel.Internal.class)
    String imageStudent;

    @JsonView(ViewModel.Internal.class)
    int idProject;

    @JsonView(ViewModel.Internal.class)
    String nameProject;

    @JsonView(ViewModel.Internal.class)
    int status;

    @JsonView(ViewModel.Internal.class)
    int hours;
}