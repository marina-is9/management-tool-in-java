import java.util.Arrays;
import java.util.List;

public enum FileCategory {
    VIDEO("video", Arrays.asList("mp4", "avi", "mov", "mkv", "wmv")),
    IMAGE("image", Arrays.asList("jpeg", "jpg", "png", "gif")),
    DOCUMENT("document", Arrays.asList("pdf", "doc", "docx", "txt")),
    SPREADSHEET("spreadsheet", Arrays.asList("xls", "xlsx", "csv")),
    PRESENTATION("presentation", Arrays.asList("ppt", "pptx")),
    COMPRESSED("compressed", Arrays.asList("zip", "rar")),
    ALL("all", List.of());  // Empty list for all file types

    private final String category;
    private final List<String> allowedTypes;

    FileCategory(String category, List<String> allowedTypes) {
        this.category = category;
        this.allowedTypes = allowedTypes;
    }

    public List<String> allowedTypes() {
        return allowedTypes;
    }

    public boolean allowsAllTypes() {
        return this == ALL;
    }

    public String getCategory() {
        return category;
    }

    public static void main(String[] args) {
        // Example usage
        FileCategory category = FileCategory.VIDEO;
        System.out.println("Allowed types for VIDEO: " + category.allowedTypes());
        System.out.println("Does ALL allow all types? " + FileCategory.ALL.allowsAllTypes());
    }
}
