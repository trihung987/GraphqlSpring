package me.truong.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import me.truong.entity.Category;
import me.truong.entity.Product;
import me.truong.services.ICategoryService;
import me.truong.services.IProductService;

@Controller
public class QueryResolver {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    // Migrated to CatalogGraphQLController for better pagination format
    // @QueryMapping
    // public Page<Product> products(
    //         @Argument int page,
    //         @Argument int size,
    //         @Argument String sort,
    //         @Argument String direction,
    //         @Argument Long categoryId) {
    //     
    //     Sort.Direction dir = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
    //     Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
    //     
    //     if (categoryId != null) {
    //         return productService.findByCategoryId(categoryId, pageable);
    //     } else {
    //         return productService.findAll(pageable);
    //     }
    // }

    // @QueryMapping
    // public Product product(@Argument Long id) {
    //     return productService.findById(id).orElse(null);
    // }

    // @QueryMapping
    // public Page<Category> categories(
    //         @Argument int page,
    //         @Argument int size,
    //         @Argument String sort,
    //         @Argument String direction) {
    //     
    //     Sort.Direction dir = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
    //     Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sort));
    //     
    //     return categoryService.findAll(pageable);
    // }

    // @QueryMapping
    // public Category category(@Argument Long id) {
    //     return categoryService.findById(id).orElse(null);
    // }
}