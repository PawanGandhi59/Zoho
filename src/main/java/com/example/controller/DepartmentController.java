package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.dto.ApiResponse;
import com.example.dto.DepartmentDto;
import com.example.dto.DepartmentHodDto;
import com.example.dto.EmployeeDto;
import com.example.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentService departmentService;
    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }



    @PostMapping("/create/{employeeId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> create(
    		   @PathVariable Long employeeId,
    	        @RequestBody @Valid DepartmentDto dto) {

        ApiResponse<DepartmentDto> response = departmentService.save(dto,employeeId);
        if(!response.isSuccess()) {return ResponseEntity.badRequest().body(response);}
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getAll() {
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
        Optional<DepartmentDto> dept = departmentService.get(id);
        if(dept.isEmpty()) {return ResponseEntity.notFound().build();}
        return ResponseEntity.ok(dept.get());
    }

    @GetMapping("/organization/{orgId}")
    public ResponseEntity<ApiResponse<List<DepartmentDto>>> getByOrganization(
            @PathVariable Long orgId) {

        ApiResponse<List<DepartmentDto>> byOrg = departmentService.getByOrg(orgId);
        if(!byOrg.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(byOrg);}
        return ResponseEntity.ok(byOrg);
    }

    @GetMapping("/organization/{orgId}/{deptId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> getByOrgAndDept(
            @PathVariable Long orgId,
            @PathVariable Long deptId) {

       ApiResponse<DepartmentDto> byOrgAndDept = departmentService.getByOrgAndDept(orgId, deptId);
       if(!byOrgAndDept.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(byOrgAndDept);}
       return ResponseEntity.ok(byOrgAndDept);
    }



    @PutMapping("/update/{deptId}/{employeeId}/{name}")
    public ResponseEntity<ApiResponse<DepartmentDto>> updateName(
            @PathVariable Long deptId,
            @PathVariable Long employeeId,
            @PathVariable String name) {
    		ApiResponse<DepartmentDto> update = departmentService.update(deptId,employeeId, name);
    		
    		if(!update.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(update);}
    		return ResponseEntity.ok(update);
    }



    @PutMapping("/deactivate/{deptId}/{employeeId}")
    public ResponseEntity<ApiResponse<DepartmentDto>> deactivate(
            @PathVariable Long deptId,
            @PathVariable Long employeeId
    		) {

    	ApiResponse<DepartmentDto> deactivate = departmentService.deactivate(deptId,employeeId);
    	if(!deactivate.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(deactivate);}
    	return ResponseEntity.ok(deactivate);
    }


    @PostMapping("/hod/{departmentId}/{employeeId}/{assignerId}")
    public ResponseEntity<ApiResponse<DepartmentHodDto>> assignHod(
            @PathVariable Long departmentId,
            @PathVariable Long employeeId,
            @PathVariable Long assignerId
    		) {

    	ApiResponse<DepartmentHodDto> assignHod = departmentService.assignHod(departmentId, employeeId,assignerId);
    	if(!assignHod.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(assignHod);}
    	return ResponseEntity.ok(assignHod);
    }

    @GetMapping("/hod/{departmentId}")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> getHods(
            @PathVariable Long departmentId) {

     ApiResponse<List<EmployeeDto>> hods = departmentService.getHods(departmentId);
     if(!hods.isSuccess()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hods);}
     return ResponseEntity.ok(hods);
    }
}
