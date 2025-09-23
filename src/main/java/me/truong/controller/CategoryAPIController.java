package me.truong.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import me.truong.entity.Category;
import me.truong.model.Response;
import me.truong.services.ICategoryService;
import me.truong.services.IStorageService;

@RestController
@RequestMapping("/api/category")
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    /**
     * Lấy danh sách category có phân trang
     * /api/category?page=0&size=5&sort=categoryName,asc
     */
    @GetMapping
    public ResponseEntity<?> getAllCategory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "categoryId,asc") String[] sort) {

        // tách sort param: field,direction
        String sortField = sort[0];
        Sort.Direction dir = (sort.length > 1 && sort[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortField));
        Page<Category> result = categoryService.findAll(pageable);

        return ResponseEntity.ok(new Response(true, "Thành công", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        return categoryService.findById(id)
                .<ResponseEntity<?>>map(cat -> ResponseEntity.ok(new Response(true, "Thành công", cat)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, "Không tìm thấy Category", null)));
    }

    @PostMapping
    public ResponseEntity<?> addCategory(
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        if (categoryService.findByCategoryName(categoryName).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Category đã tồn tại", null));
        }

        Category category = new Category();
        category.setCategoryName(categoryName);

        if (icon != null && !icon.isEmpty()) {
            String filename = storageService.getSorageFilename(icon, UUID.randomUUID().toString());
            category.setIcon(filename);
            storageService.store(icon, filename);
        }

        categoryService.save(category);
        return ResponseEntity.ok(new Response(true, "Thêm thành công", category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "icon", required = false) MultipartFile icon) {

        Optional<Category> opt = categoryService.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, "Không tìm thấy Category", null));
        }

        Category category = opt.get();
        category.setCategoryName(categoryName);

        if (icon != null && !icon.isEmpty()) {
            String filename = storageService.getSorageFilename(icon, UUID.randomUUID().toString());
            category.setIcon(filename);
            storageService.store(icon, filename);
        }

        categoryService.save(category);
        return ResponseEntity.ok(new Response(true, "Cập nhật thành công", category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(c -> {
                    categoryService.delete(c);
                    return ResponseEntity.ok(new Response(true, "Xóa thành công", c));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, "Không tìm thấy Category", null)));
    }
}
