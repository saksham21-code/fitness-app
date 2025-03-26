package dto;


import lombok.Data;

@Data
public class BookingRequest {
    private String userId;
    private String classId;
}
