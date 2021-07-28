package com.csprojectback.freelork.dto;


import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompanyStudentsDTO {

    @JsonView(ViewModel.Internal.class)
    int id;

    @JsonView(ViewModel.Internal.class)
    String fullName;

    @JsonView(ViewModel.Internal.class)
    String enrollment;

    @JsonView(ViewModel.Internal.class)
    String email;

    @JsonView(ViewModel.Internal.class)
    String image;

    @JsonView(ViewModel.Internal.class)
    String group;

    @JsonView(ViewModel.Internal.class)
    int hours;
}