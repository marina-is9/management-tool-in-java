import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Component
public class ProjectFactory {

    private final Faker faker = new Faker();
    private final Random random = new Random();
    
    @Autowired
    private ProjectRepository projectRepository;

    public Project createRandomProject() {
        LocalDateTime startDate = faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime endDate = faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();

        // Create a new project
        Project project = new Project();
        project.setName(faker.company().name());
        project.setIcon("icons/" + faker.letterify("?????") + ".png");
        project.setDescription(faker.lorem().paragraph());
        project.setStartDate(startDate);
        project.setCompletionDate(endDate);
        project.setTenantId("12345678"); // Assuming tenant_id is fixed for now
        
        return project;
    }

    // Create multiple random projects
    public void createMultipleProjects(int count) {
        for (int i = 0; i < count; i++) {
            Project project = createRandomProject();
            projectRepository.save(project);
        }
    }
}
