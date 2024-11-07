package com.example.project.valueobjects;

import java.time.LocalDateTime;

public class StartDate {

    private final LocalDateTime startDate;

    public StartDate(String startDate) {
        this.startDate = LocalDateTime.parse(startDate);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}
