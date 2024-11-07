import java.util.List;
import java.util.Optional;

public class ProjectRepository {

    public static Project store(ProjectModel projectModel) throws Exception {
        try {
            // Prepare data for database insertion from ProjectModel object
            Project project = new Project();
            project.setName(projectModel.getName().getValue());
            project.setIcon(Optional.ofNullable(projectModel.getIcon()).map(Icon::getValue).orElse(null));
            project.setDescription(Optional.ofNullable(projectModel.getDescription()).map(Description::getValue).orElse(null));
            project.setTenantId(projectModel.getTenantId());
            project.setStartDate(Optional.ofNullable(projectModel.getStartDate()).map(date -> date.format("yyyy-MM-dd HH:mm:ss")).orElse(null));
            project.setCompletionDate(Optional.ofNullable(projectModel.getCompletionDate()).map(date -> date.format("yyyy-MM-dd HH:mm:ss")).orElse(null));

            // Save and return the project
            return project.save();
        } catch (Exception e) {
            throw new Exception("Project creation failed. " + e.getMessage(), e);
        }
    }

    public void uploadIcon(Request request, Project project) throws Exception {
        if (request.hasFile("icon")) {
            File icon = request.getFile("icon");
            String iconPath = FileUploadHelper.uploadFile(icon, project.getTenantId(), "icons", FileCategory.IMAGE);
            project.setIcon(iconPath);
            project.save();
        }
    }

    public static Project update(ProjectModel projectModel) throws Exception {
        try {
            Project project = Project.findById(projectModel.getId().getValue());

            // Update the project with new data from ProjectModel object
            project.setName(projectModel.getName().getValue());
            project.setIcon(Optional.ofNullable(projectModel.getIcon()).map(Icon::getValue).orElse(null));
            project.setDescription(Optional.ofNullable(projectModel.getDescription()).map(Description::getValue).orElse(null));
            project.setTenantId(projectModel.getTenantId());
            project.setStartDate(Optional.ofNullable(projectModel.getStartDate()).map(date -> date.format("yyyy-MM-dd HH:mm:ss")).orElse(null));
            project.setCompletionDate(Optional.ofNullable(projectModel.getCompletionDate()).map(date -> date.format("yyyy-MM-dd HH:mm:ss")).orElse(null));

            project.save();

            return project;
        } catch (Exception e) {
            throw new Exception("Project update failed. " + e.getMessage(), e);
        }
    }

    public static Project findById(ProjectId projectId) throws Exception {
        try {
            return Project.findById(projectId.getValue()).orElseThrow(() -> new Exception("Project not found."));
        } catch (Exception e) {
            throw new Exception("An error occurred while retrieving the project. " + e.getMessage(), e);
        }
    }

    public static Project delete(ProjectId projectId) throws Exception {
        try {
            Project project = Project.findById(projectId.getValue()).orElseThrow(() -> new Exception("Project not found."));
            project.delete();
            return project;
        } catch (Exception e) {
            throw new Exception("An error occurred while deleting the project. " + e.getMessage(), e);
        }
    }

    public static List<EmployeeDetails> getEmployeesWithDetails(Project project) throws Exception {
        try {
            List<Employee> employees = project.getEmployeesWithDetails();
            return employees.stream()
                    .map(employee -> new EmployeeDetails(
                            employee.getId(),
                            employee.getName(),
                            employee.getUser().map(user -> user.getEmail()).orElse(null),
                            Responsibility.findById(employee.getResponsibilityId()).map(Responsibility::getName).orElse(null)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("An error occurred while fetching employees. " + e.getMessage(), e);
        }
    }

    public static void assignEmployee(Project project, Employee employee, Responsibility responsibility) throws Exception {
        try {
            // Check if the employee is already assigned to the project
            if (project.hasEmployee(employee)) {
                throw new Exception("Employee is already assigned to this project.");
            }

            // Assign the employee with the responsibility to the project
            project.assignEmployee(employee, responsibility);
        } catch (Exception e) {
            throw new Exception("An error occurred while assigning the employee to the project: " + e.getMessage(), e);
        }
    }

    public static void removeEmployee(Project project, Employee employee) throws Exception {
        project.removeEmployee(employee);
    }

    public static List<Project> findAll() throws Exception {
        try {
            return Project.findAll();
        } catch (Exception e) {
            throw new Exception("Failed to retrieve projects. " + e.getMessage(), e);
        }
    }

    public static void destroyMultipleProjects(List<String> projectIds) throws Exception {
        try {
            for (String projectId : projectIds) {
                Project project = Project.findById(projectId).orElseThrow(() -> new Exception("The specified project could not be found."));
                project.delete();
            }
        } catch (Exception e) {
            throw new Exception("An error occurred while deleting the projects: " + e.getMessage(), e);
        }
    }
}

class Project {
    // Fields and methods for the Project class (save, delete, findById, etc.)

    public static Project save(Project project) {
        // Save the project to the database
        return project;
    }

    public static Optional<Project> findById(String id) {
        // Find project by ID
        return Optional.ofNullable(new Project()); // Placeholder for actual logic
    }

    public static List<Project> findAll() {
        // Retrieve all projects
        return List.of(new Project()); // Placeholder for actual logic
    }

    // Other methods like assignEmployee, removeEmployee, etc.
}

class EmployeeDetails {
    private String employeeId;
    private String name;
    private String email;
    private String responsibility;

    // Constructor and getters/setters
}

class Responsibility {
    public static Optional<Responsibility> findById(String id) {
        // Find responsibility by ID
        return Optional.of(new Responsibility()); // Placeholder for actual logic
    }

    public String getName() {
        return "Responsibility Name"; // Placeholder for actual logic
    }
}
