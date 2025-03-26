package dto;


import lombok.Data;

@Data
public class UserRequest {
    private String id;
    private String name;
    private String tier; // PLATINUM, GOLD, SILVER
}
