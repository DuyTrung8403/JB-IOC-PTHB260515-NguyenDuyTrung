package ra.edu.baithi.repository;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ra.edu.baithi.entity.Building;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BuildingRepository extends JpaRepository<Building, Long>, JpaSpecificationExecutor<Building> {
    boolean existsByBuildingName(String name);
    boolean existsByBuildingNameAndIdNot(String buildingName, Long id);

    static Specification<Building> filter(String name, Short status) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(name != null && !name.trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("buildingName")), "%" + name.trim().toLowerCase() + "%"));
            }

            if(status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
