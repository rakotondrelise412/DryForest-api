package org.ong.dryforest.modules.category;


import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.ong.dryforest.modules.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAll(){

        return ResponseEntity.ok(
                categoryService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity <CategoryDTO> getById(
            @PathVariable Long id){
        return ResponseEntity.ok(
                categoryService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity <CategoryDTO> create(
            @RequestBody CategoryDTO categoryDTO){
        return ResponseEntity.ok(
                categoryService.create(categoryDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity <CategoryDTO> update(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO
    ){
        return ResponseEntity.ok(
                categoryService.update(id, categoryDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity <Void> delete(
            @PathVariable Long id){
        categoryService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
