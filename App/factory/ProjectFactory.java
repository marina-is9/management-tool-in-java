package com.example.project.factory;

import com.example.project.dto.ProjectDTO;
import com.example.project.model.Project;
import com.example.project.valueobjects.ProjectId;
import com.example.project.valueobjects.Name;
import com.example.project.valueobjects.Description;
import com.example.project.valueobjects.Icon;
import com.example.project.valueobjects.StartDate;
import com.example.project.valueobjects.CompletionDate;

public class ProjectFactory {

    public static Project create(ProjectDTO projectDTO) {
        return new Project(
                projectDTO.getId() != null ? new ProjectId(projectDTO.getId()) : null,
                new Name(projectDTO.getName()),
                projectDTO.getDescription() != null ? new Description(projectDTO.getDescription()) : null,
                projectDTO.getIcon() != null ? new Icon(projectDTO.getIcon()) : null,
                projectDTO.getTenantId(),
                new StartDate(projectDTO.getStartDate()),
                projectDTO.getCompletionDate() != null ? new CompletionDate(projectDTO.getCompletionDate()) : null
        );
    }
}
