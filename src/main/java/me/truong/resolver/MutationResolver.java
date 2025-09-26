package me.truong.resolver;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import me.truong.dto.CategoryInput;
import me.truong.dto.CategoryUpdateInput;
import me.truong.dto.ProductInput;
import me.truong.dto.ProductUpdateInput;
import me.truong.entity.Category;
import me.truong.entity.Product;
import me.truong.services.ICategoryService;
import me.truong.services.IProductService;
import me.truong.services.IStorageService;

@Controller
public class MutationResolver {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IStorageService storageService;

    @MutationMapping
    public Product createProduct(@Argument ProductInput input, @Argument MultipartFile image) {
        if (productService.findByProductName(input.getProductName()).isPresent()) {
            throw new RuntimeException("Sản phẩm đã tồn tại");
        }

        Optional<Category> categoryOpt = categoryService.findById(input.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy Category");
        }

        Product product = new Product();
        product.setProductName(input.getProductName());
        product.setQuantity(input.getQuantity());
        product.setUnitPrice(input.getUnitPrice());
        product.setDescription(input.getDescription());
        product.setDiscount(input.getDiscount());
        product.setStatus(input.getStatus());
        product.setCreateDate(new Date());
        product.setCategory(categoryOpt.get());

        if (image != null && !image.isEmpty()) {
            String fileName = storageService.getSorageFilename(image, UUID.randomUUID().toString());
            product.setImages(fileName);
            storageService.store(image, fileName);
        }

        return productService.save(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductUpdateInput input, @Argument MultipartFile image) {
        Optional<Product> productOpt = productService.findById(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }

        Product product = productOpt.get();
        
        if (input.getProductName() != null) {
            product.setProductName(input.getProductName());
        }
        if (input.getQuantity() != null) {
            product.setQuantity(input.getQuantity());
        }
        if (input.getUnitPrice() != null) {
            product.setUnitPrice(input.getUnitPrice());
        }
        if (input.getDescription() != null) {
            product.setDescription(input.getDescription());
        }
        if (input.getDiscount() != null) {
            product.setDiscount(input.getDiscount());
        }
        if (input.getStatus() != null) {
            product.setStatus(input.getStatus());
        }
        if (input.getCategoryId() != null) {
            Optional<Category> categoryOpt = categoryService.findById(input.getCategoryId());
            if (categoryOpt.isEmpty()) {
                throw new RuntimeException("Không tìm thấy Category");
            }
            product.setCategory(categoryOpt.get());
        }

        if (image != null && !image.isEmpty()) {
            String fileName = storageService.getSorageFilename(image, UUID.randomUUID().toString());
            product.setImages(fileName);
            storageService.store(image, fileName);
        }

        return productService.save(product);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        Optional<Product> productOpt = productService.findById(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        
        productService.delete(productOpt.get());
        return true;
    }

    @MutationMapping
    public Category createCategory(@Argument CategoryInput input, @Argument MultipartFile icon) {
        if (categoryService.findByCategoryName(input.getCategoryName()).isPresent()) {
            throw new RuntimeException("Category đã tồn tại");
        }

        Category category = new Category();
        category.setCategoryName(input.getCategoryName());

        if (icon != null && !icon.isEmpty()) {
            String filename = storageService.getSorageFilename(icon, UUID.randomUUID().toString());
            category.setIcon(filename);
            storageService.store(icon, filename);
        }

        return categoryService.save(category);
    }

    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument CategoryUpdateInput input, @Argument MultipartFile icon) {
        Optional<Category> categoryOpt = categoryService.findById(id);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy Category");
        }

        Category category = categoryOpt.get();
        
        if (input.getCategoryName() != null) {
            category.setCategoryName(input.getCategoryName());
        }

        if (icon != null && !icon.isEmpty()) {
            String filename = storageService.getSorageFilename(icon, UUID.randomUUID().toString());
            category.setIcon(filename);
            storageService.store(icon, filename);
        }

        return categoryService.save(category);
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        Optional<Category> categoryOpt = categoryService.findById(id);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Không tìm thấy Category");
        }
        
        categoryService.delete(categoryOpt.get());
        return true;
    }
}