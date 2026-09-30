package ra.edu.baithi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.edu.baithi.dto.request.BuildingRequestDto;
import ra.edu.baithi.dto.request.BuildingUpdateDto;
import ra.edu.baithi.dto.request.ChangeStatusDto;
import ra.edu.baithi.dto.response.ApiResponse;
import ra.edu.baithi.entity.Building;
import ra.edu.baithi.service.BuildingService;

@RestController
    @RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Building>>> getAllBuildings(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Short status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Building> buildings = buildingService.getAllBuildings(name, status, pageable);
        return ResponseEntity.ok(
                ApiResponse.success("Lấy danh sách tòa nhà thành công", buildings, HttpStatus.OK.value())
        );
    }


    @PostMapping
    public ResponseEntity<ApiResponse<Building>> createBuilding(@Valid @RequestBody BuildingRequestDto req) {
        Building building = buildingService.createBuilding(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("Tạo tòa nhà thành công", building, HttpStatus.CREATED.value())
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<Building>> updateBuildingStatus(
            @PathVariable Long id,
            @Valid @RequestBody ChangeStatusDto req
    ){
        Building changed = buildingService.changeStatus(id, req);
        return ResponseEntity.ok(
                ApiResponse.success("Cập nhật trạng thái tòa nhà thành công", changed, HttpStatus.OK.value())
        );
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ApiResponse<Building>> updateBuilding(
            @PathVariable Long id,
            @Valid @ModelAttribute BuildingUpdateDto req){
        Building changed = buildingService.updateBuilding(id, req);
        return ResponseEntity.ok(
                ApiResponse.success("Cập nhật tòa nhà thành công", changed, HttpStatus.OK.value())
        );
    }
}
