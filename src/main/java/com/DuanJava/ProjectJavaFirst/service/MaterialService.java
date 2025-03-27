package com.DuanJava.ProjectJavaFirst.service;

import com.DuanJava.ProjectJavaFirst.entity.Material;
import com.DuanJava.ProjectJavaFirst.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public Material getMaterialById(Long id) {
        return materialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Material not found"));
    }

    public Material addMaterial(Material material) {
        return materialRepository.save(material);
    }

    public Material updateMaterial(Long id, Material updatedMaterial) {
        Material existingMaterial = getMaterialById(id);
        existingMaterial.setTitle(updatedMaterial.getTitle());
        existingMaterial.setType(updatedMaterial.getType());
        existingMaterial.setUrl(updatedMaterial.getUrl());
        return materialRepository.save(existingMaterial);
    }

    public void deleteMaterial(Long id) {
        materialRepository.deleteById(id);
    }
}
