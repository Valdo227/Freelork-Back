package com.csprojectback.freelork.dto;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class RegisterDTO {

    @JsonView(ViewModel.Internal.class)
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate dateRegister;

    @JsonFormat(pattern="HH:mm")
    @JsonView(ViewModel.Internal.class)
    private LocalTime timeRegister;

    @JsonView(ViewModel.Internal.class)
    private String title;

    @JsonView(ViewModel.Internal.class)
    private String description;

    @JsonView(ViewModel.Internal.class)
    private int idProject;

    @JsonView(ViewModel.Internal.class)
    private int idUser;

}
