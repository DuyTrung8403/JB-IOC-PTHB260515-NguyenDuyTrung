package ra.edu.baithi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuildingUpdateDto {
    @NotBlank(message = "Tên tòa nhà không được để trống")
    @Size(max = 100, message = "Tên tòa nhà không được vượt quá 100 ký tự")
    private String buildingName;

    @NotNull(message = "Diện tích khoogn được để trống")
    @Positive(message = "Diện tích phải lớn hơn 0")
    private Double buildingArea;

    @NotBlank(message = "Đơn vị không được để trống")
    @Size(max = 10, message = "Đơn vị không được vượt quá 10 ký tự")
    private String areaUnit;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Thời gian không được để trống")
    @Positive(message = "Thời gian phải lớn hơn 0")
    private Integer time;

    @NotBlank(message = "Đơn vị thời gian không được để trống")
    @Size(max = 10, message = "Đơn vị thời gian không được vượt quá 10 ký tự")
    private String timeUnit;

    @NotBlank(message = "Nội dung không được để trống")
    @Size(max = 255, message = "Nội dung không được vượt quá 255 ký tự")
    private String content;

    private MultipartFile image;
}


