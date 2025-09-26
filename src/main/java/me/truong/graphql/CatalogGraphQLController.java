package me.truong.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import me.truong.entity.Category;
import me.truong.entity.Product;
import me.truong.services.ICategoryService;
import me.truong.services.IProductService;

@Controller
public class CatalogGraphQLController {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private IProductService productService;

    @QueryMapping
    public CategoryPage categories(@Argument int page,
                                   @Argument int size,
                                   @Argument String name) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId"));
        Page<Category> pageData = (name == null || name.isEmpty())
                ? categoryService.findAll(pageable)
                : categoryService.findByCategoryNameContaining(name, pageable);
        return new CategoryPage(pageData);
    }

    @QueryMapping
    public Category category(@Argument Long id) {
        return categoryService.findById(id).orElse(null);
    }

    @QueryMapping
    public ProductPage products(@Argument int page,
                                @Argument int size,
                                @Argument Long categoryId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId"));
        Page<Product> pageData = (categoryId == null)
                ? productService.findAll(pageable)
                : productService.findByCategoryId(categoryId, pageable);
        return new ProductPage(pageData);
    }

    @QueryMapping
    public Product product(@Argument Long id) {
        return productService.findById(id).orElse(null);
    }

    // wrapper records
    public record CategoryPage(java.util.List<Category> content, long totalElements, int totalPages) {
        public CategoryPage(Page<Category> p) { this(p.getContent(), p.getTotalElements(), p.getTotalPages()); }
    }
    public record ProductPage(java.util.List<Product> content, long totalElements, int totalPages) {
        public ProductPage(Page<Product> p) { this(p.getContent(), p.getTotalElements(), p.getTotalPages()); }
    }
}