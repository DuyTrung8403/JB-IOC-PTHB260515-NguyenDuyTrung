package ra.edu.baithi.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private Object error;
    private int httpStatus;

    public static <T> ApiResponse<T> success(String message, T data, int httpStatus) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .httpStatus(httpStatus).build();
    }

    public static <T> ApiResponse<T> fail(String message, Object error, int httpStatus) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .error(error)
                .httpStatus(httpStatus).build();
    }
}
