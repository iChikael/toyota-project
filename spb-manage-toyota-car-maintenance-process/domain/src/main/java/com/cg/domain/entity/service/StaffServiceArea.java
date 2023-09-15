package com.cg.domain.entity.service;

import com.cg.domain.entity.staff.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "staff_service_area", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"staff_id", "service_area_id"}, name = "UK_staff_service_area")
})
public class StaffServiceArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    private Staff staff;


    @ManyToOne
    @JoinColumn(name = "service_area_id", referencedColumnName = "id", nullable = false)
    private ServiceArea serviceArea;
}
