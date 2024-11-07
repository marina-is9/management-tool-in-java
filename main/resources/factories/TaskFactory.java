import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class TaskFactory {

    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private PriorityRepository priorityRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    public Task createRandomTask() {
        // Get lists of existing IDs from the repositories
        List<Long> projectIds = projectRepository.findAllIds();
        List<Long> priorityIds = priorityRepository.findAllIds();
        List<Long> taskIds = taskRepository.findAllIds();
        List<Long> taskStatusIds = taskStatusRepository.findAllIds();
        
        // Randomly select entities
        Long projectId = randomFromList(projectIds);
        List<Long> sprints = sprintRepository.findIdsByProjectId(projectId);
        Long sprintId = sprints.isEmpty() ? null : randomFromList(sprints);
        
        // Create a new task object
        Task task = new Task();
        task.setTitle(faker.name().fullName());
        task.setDescription(faker.lorem().paragraph());
        task.setProgress(faker.number().numberBetween(0, 100));
        task.setProjectId(projectId);
        task.setPriorityId(randomFromList(priorityIds));
        task.setSprintId(sprintId);
        task.setStartedAt(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        task.setDeadline(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        task.setCompletedAt(faker.date().future(365, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());
        task.setRepeatDays(faker.number().numberBetween(1, 7));
        task.setExplanationClipPath(faker.file().filePath());
        task.setDependsOnId(randomFromList(taskIds));
        task.setStatusId(randomFromList(taskStatusIds));
        task.setMeetingCount(faker.number().numberBetween(1, 10));
        task.setTenantId("12345678");
        task.setDuration(random.nextInt(16) + 1); // Duration between 1 and 16
        
        return task;
    }

    // Helper method to pick a random element from a list
    private <T> T randomFromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    // You can call this method to create multiple tasks if needed
    public void createMultipleTasks(int count) {
        for (int i = 0; i < count; i++) {
            Task task = createRandomTask();
            taskRepository.save(task);
        }
    }
}
