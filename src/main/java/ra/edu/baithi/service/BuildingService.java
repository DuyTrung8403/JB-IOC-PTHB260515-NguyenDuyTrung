package ra.edu.baithi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ra.edu.baithi.dto.request.BuildingRequestDto;
import ra.edu.baithi.dto.request.BuildingUpdateDto;
import ra.edu.baithi.dto.request.ChangeStatusDto;
import ra.edu.baithi.entity.Building;
import ra.edu.baithi.exception.ResourceNotFoundException;
import ra.edu.baithi.repository.BuildingRepository;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BuildingService {
    private final BuildingRepository buildingRepository;
    private final Cloudinary cloudinary;

    public Page<Building> getAllBuildings(String name, Short status, Pageable pageable) {
        return buildingRepository.findAll(BuildingRepository.filter(name, status), pageable);
    }

    @Transactional
    public Building createBuilding(BuildingRequestDto req) {
        if(buildingRepository.existsByBuildingName(req.getBuildingName())) {
            throw new IllegalArgumentException("Tên tòa nhà đã tồn tại");
        }
        Building building = Building.builder()
                .buildingName(req.getBuildingName())
                .buildingArea(req.getBuildingArea())
                .areaUnit(req.getAreaUnit())
                .startDate(req.getStartDate())
                .time(req.getTime())
                .timeUnit(req.getTimeUnit())
                .design(req.getDesign())
                .content(req.getContent())
                .status(req.getStatus())
                .build();
        return buildingRepository.save(building);
    }

    @Transactional
    public Building changeStatus(Long id, ChangeStatusDto req) {
        Building building = buildingRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Không tìm thấy tòa nhà với id: " + id));
        if(building.getStatus() != null && building.getStatus() == 2){
            throw new IllegalArgumentException("Tòa nhà đã hoàn thành, không thể thay đổi trạng thái");
        }

        if(req.getStatus() < 0 || req.getStatus() > 2){
            throw new IllegalArgumentException("Trạng thái không hợp lệ, chỉ chấp nhận 0, 1 hoặc 2");
        }
        building.setStatus(req.getStatus());
        return buildingRepository.save(building);
    }

    @Transactional
    public Building updateBuilding(Long id, BuildingUpdateDto req) {
        Building building = buildingRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Khong tim thấy tòa nhà nào có id: " + id));

        if(buildingRepository.existsByBuildingNameAndIdNot(req.getBuildingName(), id)) {
            throw new IllegalArgumentException("Tên tòa nhà đã tồn tại");
        }

        MultipartFile image = req.getImage();
        if(image != null && !image.isEmpty()){
            try{
                Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("folder", "buildings","resource_type", "auto"));
                String imageUrl = uploadResult.get("secure_url").toString();
                building.setDesign(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Upload ảnh lên cloud thất bại"+e.getMessage());
            }
        }
        building.setBuildingName(req.getBuildingName());
        building.setBuildingArea(req.getBuildingArea());
        building.setAreaUnit(req.getAreaUnit());
        building.setStartDate(req.getStartDate());
        building.setTime(req.getTime());
        building.setTimeUnit(req.getTimeUnit());
        building.setContent(req.getContent());
        return buildingRepository.save(building);
    }
}

