package org.ong.dryforest.modules.category;


import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.category.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Page<CategoryDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return categoryService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public CategoryDTO getById(
            @PathVariable Long id){
        return categoryService.getById(id);
    }

    @PostMapping
    public CategoryDTO create(
            @RequestBody CategoryDTO categoryDTO){
        return categoryService.create(categoryDTO);
    }

    @PutMapping("/{id}")
    public CategoryDTO update(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO){
        return categoryService.update(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }

}
