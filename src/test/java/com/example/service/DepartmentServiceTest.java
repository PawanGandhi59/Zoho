package com.example.service;

import com.example.dto.ApiResponse;
import com.example.dto.DepartmentDto;
import com.example.dto.DepartmentHodDto;
import com.example.entity.*;
import com.example.repository.DepartmentHodRepository;
import com.example.repository.DepartmentRepository;
import com.example.repository.EmployeeRepository;
import com.example.repository.OrganizationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private DepartmentHodRepository departmentHodRepository;
    @Mock
    private OrganizationRepository organizationRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private DepartmentService departmentService;

    // =====================================================
    // save()
    // =====================================================
    @Nested
    class SaveMethodTests {

        DepartmentDto dto;
        EmployeeEntity employee;
        OrganizationEntity org;

        @BeforeEach
        void setup() {
            dto = new DepartmentDto();
            dto.setName("IT");
            dto.setOrganization(1L);

            org = new OrganizationEntity();
            org.setId(1L);

            employee = new EmployeeEntity();
            employee.setOrganizationId(org);
        }

        @Test
        void shouldFailWhenEmployeeNotFound() {
            when(employeeRepository.findByIdAndStatus(1L, EmployeeStatus.ACTIVE))
                    .thenReturn(Optional.empty());

            ApiResponse<DepartmentDto> res =
                    departmentService.save(dto, 1L);

            assertFalse(res.isSuccess());
            assertEquals("Employee not found", res.getMessage());
        }

        @Test
        void shouldFailWhenOrganizationNotFound() {
            employee.setDesignation("hr");

            when(employeeRepository.findByIdAndStatus(
                    anyLong(), eq(EmployeeStatus.ACTIVE)))
                    .thenReturn(Optional.of(employee));

            when(organizationRepository.findByIdAndStatus(1L, "ACTIVE"))
                    .thenReturn(Optional.empty());

            ApiResponse<DepartmentDto> response =
                    departmentService.save(dto, 1L);

            assertFalse(response.isSuccess());
            assertEquals("Organization not found", response.getMessage());

            verify(departmentRepository, never()).save(any());
        }

        @Test
        void shouldFailWhenEmployeeFromDifferentOrg() {
            OrganizationEntity other = new OrganizationEntity();
            other.setId(99L);
            employee.setOrganizationId(other);

            when(employeeRepository.findByIdAndStatus(anyLong(), eq(EmployeeStatus.ACTIVE)))
                    .thenReturn(Optional.of(employee));

            ApiResponse<DepartmentDto> res =
                    departmentService.save(dto, 1L);

            assertFalse(res.isSuccess());
            assertEquals("Employee does not belong to organization", res.getMessage());
        }

        @Test
        void shouldFailWhenUnauthorized() {
            employee.setDesignation("developer");

            when(employeeRepository.findByIdAndStatus(anyLong(), eq(EmployeeStatus.ACTIVE)))
                    .thenReturn(Optional.of(employee));

            ApiResponse<DepartmentDto> res =
                    departmentService.save(dto, 1L);

            assertFalse(res.isSuccess());
            assertEquals("Unauthorized user to create department", res.getMessage());
        }

        @Test
        void shouldSaveWhenEmployeeIsHR() {
            employee.setDesignation("hr");

            DepartmentEntity entity = new DepartmentEntity();
            entity.setId(10L);
            entity.setName("IT");
            entity.setOrganization(org);

            when(modelMapper.map(dto, DepartmentEntity.class)).thenReturn(entity);
            when(employeeRepository.findByIdAndStatus(anyLong(), eq(EmployeeStatus.ACTIVE)))
                    .thenReturn(Optional.of(employee));
            when(organizationRepository.findByIdAndStatus(1L, "ACTIVE"))
                    .thenReturn(Optional.of(org));
            when(departmentRepository.save(any())).thenReturn(entity);

            ApiResponse<DepartmentDto> res =
                    departmentService.save(dto, 1L);

            assertTrue(res.isSuccess());
            assertEquals("success", res.getMessage());
            assertEquals(10L, res.getData().getId());
        }
    }

    // =====================================================
    // getAll()
    // =====================================================
    @Nested
    class GetAllMethodTests {

        @Test
        void shouldReturnEmptyList() {
            when(departmentRepository.findAll()).thenReturn(List.of());

            List<DepartmentDto> result = departmentService.getAll();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnMappedDepartments() {
            OrganizationEntity org = new OrganizationEntity();
            org.setId(1L);

            DepartmentEntity d = new DepartmentEntity();
            d.setId(1L);
            d.setName("IT");
            d.setOrganization(org);

            when(departmentRepository.findAll()).thenReturn(List.of(d));

            List<DepartmentDto> result = departmentService.getAll();

            assertEquals(1, result.size());
            assertEquals("IT", result.get(0).getName());
        }
    }

    // =====================================================
    // getByOrgAndDept()
    // =====================================================
    @Nested
    class GetByOrgAndDeptTests {

        @Test
        void shouldFailWhenOrganizationNotFound() {
            when(organizationRepository.existsById(1L)).thenReturn(false);

            ApiResponse<DepartmentDto> res =
                    departmentService.getByOrgAndDept(1L, 10L);

            assertFalse(res.isSuccess());
            assertEquals("Organization not found", res.getMessage());
        }

        @Test
        void shouldFailWhenDepartmentFromDifferentOrg() {
            OrganizationEntity org = new OrganizationEntity();
            org.setId(2L);

            DepartmentEntity dept = new DepartmentEntity();
            dept.setOrganization(org);

            when(organizationRepository.existsById(1L)).thenReturn(true);
            when(departmentRepository.findById(10L)).thenReturn(Optional.of(dept));

            ApiResponse<DepartmentDto> res =
                    departmentService.getByOrgAndDept(1L, 10L);

            assertFalse(res.isSuccess());
            assertEquals("Department does not belong to organization", res.getMessage());
        }
    }

    // =====================================================
    // update()
    // =====================================================
    @Nested
    class UpdateMethodTests {

        @Test
        void shouldUpdateWhenAuthorized() {
            OrganizationEntity org = new OrganizationEntity();
            org.setId(1L);

            DepartmentEntity dept = new DepartmentEntity();
            dept.setOrganization(org);

            EmployeeEntity emp = new EmployeeEntity();
            emp.setOrganizationId(org);
            emp.setDesignation("ceo");

            when(departmentRepository.findByIdAndStatus(1L, "ACTIVE"))
                    .thenReturn(Optional.of(dept));
            when(employeeRepository.findByIdAndStatus(2L, EmployeeStatus.ACTIVE))
                    .thenReturn(Optional.of(emp));
            when(departmentRepository.save(any())).thenReturn(dept);

            ApiResponse<DepartmentDto> res =
                    departmentService.update(1L, 2L, "NEW");

            assertTrue(res.isSuccess());
            assertEquals("success", res.getMessage());
        }
    }

    // =====================================================
    // deactivate()
    // =====================================================
    @Nested
    class DeactivateMethodTests {

        @Test
        void shouldDeactivateWhenAuthorized() {
            OrganizationEntity org = new OrganizationEntity();
            org.setId(1L);

            DepartmentEntity dept = new DepartmentEntity();
            dept.setOrganization(org);

            EmployeeEntity emp = new EmployeeEntity();
            emp.setOrganizationId(org);
            emp.setDesignation("hr");

            when(departmentRepository.findByIdAndStatus(1L, "ACTIVE"))
                    .thenReturn(Optional.of(dept));
            when(employeeRepository.findByIdAndStatus(2L, EmployeeStatus.ACTIVE))
                    .thenReturn(Optional.of(emp));
            when(departmentRepository.save(any())).thenReturn(dept);

            ApiResponse<DepartmentDto> res =
                    departmentService.deactivate(1L, 2L);

            assertTrue(res.isSuccess());
        }
    }

    // =====================================================
    // assignHod()
    // =====================================================
    @Nested
    class AssignHodTests {

        @Test
        void shouldAssignHodWhenAuthorized() {
            OrganizationEntity org = new OrganizationEntity();
            org.setId(1L);

            DepartmentEntity dept = new DepartmentEntity();
            dept.setOrganization(org);

            EmployeeEntity hod = new EmployeeEntity();
            hod.setOrganizationId(org);

            EmployeeEntity assigner = new EmployeeEntity();
            assigner.setOrganizationId(org);
            assigner.setDesignation("hr");

            when(departmentRepository.findByIdAndStatus(1L, "ACTIVE"))
                    .thenReturn(Optional.of(dept));
            when(employeeRepository.findByIdAndStatus(2L, EmployeeStatus.ACTIVE))
                    .thenReturn(Optional.of(hod));
            when(employeeRepository.findByIdAndStatus(3L, EmployeeStatus.ACTIVE))
                    .thenReturn(Optional.of(assigner));
            when(departmentHodRepository.save(any()))
                    .thenReturn(new DepartmentHodEntity());

            ApiResponse<DepartmentHodDto> res =
                    departmentService.assignHod(1L, 2L, 3L);

            assertTrue(res.isSuccess());
            assertEquals("success", res.getMessage());
        }
    }
}

