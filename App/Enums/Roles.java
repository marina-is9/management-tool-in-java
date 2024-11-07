public enum Roles {
    // Role IDs
    MANAGER_ID("1"),
    TEAM_LEAD_ID("2"),
    EMPLOYEE_ID("3"),
    SYSTEM_ID("4"),

    // Role names
    MANAGER("Manager"),
    TEAM_LEAD("Team Lead"),
    EMPLOYEE("Employee"),
    SYSTEM("System");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
