package com.example.project.seeder;

import com.example.project.entity.Project;
import com.example.project.repository.ProjectRepository;
import com.example.project.factory.ProjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create and save 1000 fake projects
        for (int i = 0; i < 1000; i++) {
            Project project = ProjectFactory.createFakeProject();
            projectRepository.save(project);
        }
    }
}
