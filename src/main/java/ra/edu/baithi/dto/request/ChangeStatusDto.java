package ra.edu.baithi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusDto {
    @NotNull(message = "Trạng thái không được để trống")
    private Short status;
}
