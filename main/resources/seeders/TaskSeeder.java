import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.stream.IntStream;

@Component
public class TaskSeeder implements CommandLineRunner {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed 1200 tasks
        IntStream.range(0, 1200).forEach(i -> {
            Task task = new Task();
            task.setName("Task " + (i + 1));
            task.setDescription("Description for Task " + (i + 1));
            taskRepository.save(task);
        });
        System.out.println("1200 tasks seeded successfully.");
    }
}
