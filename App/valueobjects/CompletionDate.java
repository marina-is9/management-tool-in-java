package com.example.project.valueobjects;

import java.time.LocalDateTime;

public class CompletionDate {

    private final LocalDateTime completionDate;

    public CompletionDate(String completionDate) {
        this.completionDate = LocalDateTime.parse(completionDate);
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }
}
