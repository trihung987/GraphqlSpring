package me.truong.controller;

import java.util.Date;
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
import me.truong.entity.Product;
import me.truong.model.Response;
import me.truong.services.ICategoryService;
import me.truong.services.IProductService;
import me.truong.services.IStorageService;

@RestController
@RequestMapping("/api/product")
public class ProductAPIController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    /**
     * Lấy danh sách sản phẩm có phân trang.
     * Có thể truyền ?categoryId=1 để lọc theo Category.
     * /api/product?page=0&size=10&sort=productId,desc
     */
    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "productId,asc") String[] sort,
            @RequestParam(required = false) Long categoryId) {

        String sortField = sort[0];
        Sort.Direction dir = (sort.length > 1 && sort[1].equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortField));

        Page<Product> result;
        if (categoryId != null) {
            // custom query trong service/repo nếu muốn lọc theo category
            result = productService.findByCategoryId(categoryId, pageable);
        } else {
            result = productService.findAll(pageable);
        }
        return ResponseEntity.ok(new Response(true, "Thành công", result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return productService.findById(id)
                .<ResponseEntity<?>>map(p -> ResponseEntity.ok(new Response(true, "Thành công", p)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, "Không tìm thấy sản phẩm", null)));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(
            @RequestParam String productName,
            @RequestParam int quantity,
            @RequestParam double unitPrice,
            @RequestParam String description,
            @RequestParam double discount,
            @RequestParam short status,
            @RequestParam Long categoryId,
            @RequestParam(value = "images", required = false) MultipartFile imagesFile) {

        if (productService.findByProductName(productName).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Sản phẩm đã tồn tại", null));
        }

        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Không tìm thấy Category", null));
        }

        Product product = new Product();
        product.setProductName(productName);
        product.setQuantity(quantity);
        product.setUnitPrice(unitPrice);
        product.setDescription(description);
        product.setDiscount(discount);
        product.setStatus(status);
        product.setCreateDate(new Date());
        product.setCategory(category.get());

        if (imagesFile != null && !imagesFile.isEmpty()) {
            String fileName = storageService.getSorageFilename(imagesFile, UUID.randomUUID().toString());
            product.setImages(fileName);
            storageService.store(imagesFile, fileName);
        }

        productService.save(product);
        return ResponseEntity.ok(new Response(true, "Thêm sản phẩm thành công", product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam String productName,
            @RequestParam int quantity,
            @RequestParam double unitPrice,
            @RequestParam String description,
            @RequestParam double discount,
            @RequestParam short status,
            @RequestParam Long categoryId,
            @RequestParam(value = "images", required = false) MultipartFile imagesFile) {

        Optional<Product> optProduct = productService.findById(id);
        if (optProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, "Không tìm thấy sản phẩm", null));
        }

        Optional<Category> category = categoryService.findById(categoryId);
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Không tìm thấy Category", null));
        }

        Product product = optProduct.get();
        product.setProductName(productName);
        product.setQuantity(quantity);
        product.setUnitPrice(unitPrice);
        product.setDescription(description);
        product.setDiscount(discount);
        product.setStatus(status);
        product.setCategory(category.get());

        if (imagesFile != null && !imagesFile.isEmpty()) {
            String fileName = storageService.getSorageFilename(imagesFile, UUID.randomUUID().toString());
            product.setImages(fileName);
            storageService.store(imagesFile, fileName);
        }

        productService.save(product);
        return ResponseEntity.ok(new Response(true, "Cập nhật sản phẩm thành công", product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        return productService.findById(id)
                .map(p -> {
                    productService.delete(p);
                    return ResponseEntity.ok(new Response(true, "Xóa sản phẩm thành công", p));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response(false, "Không tìm thấy sản phẩm", null)));
    }
}
