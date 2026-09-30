package ra.edu.baithi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "buildings")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "building_name", length = 100, nullable = false, unique = true)
    private String buildingName;

    @Column(name = "building_area", nullable = false)
    private Double buildingArea;

    @Column(name = "area_unit", length = 10, nullable = false)
    private String areaUnit;

    @Column(name="start_date", nullable=false)
    private LocalDate startDate;

    @Column(name = "time", nullable = false)
    private Integer time;

    @Column(name = "time_unit", length = 10, nullable = false)
    private String timeUnit;

    @Column(name = "design", length = 255, nullable = false)
    private String design;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private Short status = 1;
}
