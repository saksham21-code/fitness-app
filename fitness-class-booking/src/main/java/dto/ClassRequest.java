package dto;

import lombok.Data;

@Data
public class ClassRequest {
    private String id;
    private String type; // YOGA, SWIMMING, CYCLING
    private int capacity;
    private String scheduledTime; // ISO 8601 format, e.g., "2025-03-28T10:00:00"
}
