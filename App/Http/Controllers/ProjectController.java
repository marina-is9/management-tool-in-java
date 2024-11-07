import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ResponsibilityRepository responsibilityRepository;

    @Autowired
    private AuthManager authManager;

    @Autowired
    private ResponseHandler responseHandler;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectDTO projectDTO) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create project entity from DTO
            Project project = ProjectFactory.create(projectDTO);

            // Store project in the database
            project = projectRepository.save(project);

            // Upload project icon (if applicable)
            projectRepository.uploadIcon(projectDTO.getIcon(), project);

            return responseHandler.success(HttpStatus.CREATED, project);

        } catch (InvalidArgumentException e) {
            // Handle bad request exception
            return responseHandler.error(HttpStatus.BAD_REQUEST, "Invalid request", e.getMessage(), null);
        } catch (Exception e) {
            // Handle other exceptions
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @GetMapping
    public ResponseEntity<?> viewAllProjects() {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Fetch all projects
            List<Project> projects = projectRepository.findAll();

            return responseHandler.success(HttpStatus.OK, projects);

        } catch (InvalidArgumentException e) {
            return responseHandler.error(HttpStatus.BAD_REQUEST, "Invalid request", e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable Long projectId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Fetch project by ID
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

            return responseHandler.success(HttpStatus.OK, project);

        } catch (ResponseStatusException e) {
            return responseHandler.error(HttpStatus.NOT_FOUND, "Project not found", e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@RequestBody ProjectDTO projectDTO, @PathVariable Long projectId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Merge the ID into the project DTO
            projectDTO.setId(projectId);

            // Create project entity from DTO
            Project project = ProjectFactory.create(projectDTO);

            // Update project in the database
            project = projectRepository.save(project);

            return responseHandler.success(HttpStatus.OK, project);

        } catch (ResponseStatusException e) {
            return responseHandler.error(HttpStatus.NOT_FOUND, "Project not found", e.getMessage(), null);
        } catch (InvalidArgumentException e) {
            return responseHandler.error(HttpStatus.BAD_REQUEST, "Invalid request", e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Delete project by ID
            projectRepository.deleteById(projectId);

            return responseHandler.success(HttpStatus.OK, "Project deleted successfully");

        } catch (ResponseStatusException e) {
            return responseHandler.error(HttpStatus.NOT_FOUND, "Project not found", e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @GetMapping("/{projectId}/employees")
    public ResponseEntity<?> viewEmployees(@PathVariable Long projectId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Fetch project by ID
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

            // Get employees related to the project
            List<Employee> employees = projectRepository.getEmployeesWithDetails(project);

            return responseHandler.success(HttpStatus.OK, employees);

        } catch (ResponseStatusException e) {
            return responseHandler.error(HttpStatus.NOT_FOUND, "Project not found", e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @PostMapping("/{projectId}/add-employee")
    public ResponseEntity<?> addEmployeeToProject(@RequestBody AddEmployeeRequest request) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate and fetch project, employee, and responsibility
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
            Responsibility responsibility = responsibilityRepository.findById(request.getResponsibilityId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Responsibility not found"));

            // Assign employee to project
            projectRepository.assignEmployee(project, employee, responsibility);

            return responseHandler.success(HttpStatus.CREATED, "Employee assigned to project successfully");

        } catch (ResponseStatusException e) {
            return responseHandler.error(HttpStatus.NOT_FOUND, e.getReason(), e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @DeleteMapping("/{projectId}/remove-employee/{employeeId}")
    public ResponseEntity<?> deleteEmployeeFromProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate and fetch project and employee
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

            // Remove employee from project
            projectRepository.removeEmployee(project, employee);

            return responseHandler.success(HttpStatus.OK, "Employee removed from project");

        } catch (ResponseStatusException e) {
            return responseHandler.error(HttpStatus.NOT_FOUND, e.getReason(), e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete-multiple")
    public ResponseEntity<?> deleteMultipleProjects(@RequestBody DeleteMultipleRequest request) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Delete multiple projects
            projectRepository.deleteAllById(request.getProjectIds());

            return responseHandler.success(HttpStatus.OK, "Projects deleted successfully");

        } catch (InvalidArgumentException e) {
            return responseHandler.error(HttpStatus.BAD_REQUEST, "Invalid request", e.getMessage(), null);
        } catch (Exception e) {
            return responseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", e.getMessage(), null);
        }
    }
}
