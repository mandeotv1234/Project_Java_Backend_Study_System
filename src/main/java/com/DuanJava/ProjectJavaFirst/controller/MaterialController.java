package com.DuanJava.ProjectJavaFirst.controller;

import com.DuanJava.ProjectJavaFirst.entity.Material;
import com.DuanJava.ProjectJavaFirst.service.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
public class MaterialController {

    private final MaterialService materialService;

    // Lấy danh sách tất cả tài liệu
    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {
        return ResponseEntity.ok(materialService.getAllMaterials());
    }

    // Lấy tài liệu theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Material> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(materialService.getMaterialById(id));
    }

    // Thêm tài liệu vào bài giảng
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/add")
    public ResponseEntity<Material> addMaterial(@RequestBody Material material) {
        return ResponseEntity.ok(materialService.addMaterial(material));
    }

    // Cập nhật tài liệu
    @PreAuthorize("hasRole('TEACHER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Material> updateMaterial(@PathVariable Long id, @RequestBody Material material) {
        return ResponseEntity.ok(materialService.updateMaterial(id, material));
    }

    // Xóa tài liệu
    @PreAuthorize("hasRole('TEACHER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.noContent().build();
    }
}
