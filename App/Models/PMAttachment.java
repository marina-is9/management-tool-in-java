package com.example.app.models;

public class PMAttachment {
    private final String name;
    private final long sizeKB;
    private final String type;
    private final String category;

    public PMAttachment(String name, long sizeKB, String type, String category) {
        this.name = name;
        this.sizeKB = sizeKB;
        this.type = type;
        this.category = category;

        validate();
    }

    public String getName() {
        return name;
    }

    public long getSizeKB() {
        return sizeKB;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    private void validate() {
        // Perform basic validation (customize as needed)
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty.");
        }
        if (sizeKB <= 0) {
            throw new IllegalArgumentException("File size must be greater than zero.");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("File type cannot be empty.");
        }
        if (category == null || category.isEmpty()) {
            throw new IllegalArgumentException("File category cannot be empty.");
        }

        // Optionally add more complex validation for type and category
    }
}
