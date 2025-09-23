package me.truong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.truong.entity.Product;
import me.truong.services.IProductService;
import me.truong.services.ICategoryService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private IProductService productService;
    
    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String listProducts(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId"));
        Page<Product> productPage;
        
        if (categoryId != null) {
            productPage = productService.findByCategoryId(categoryId, pageable);
        } else {
            productPage = productService.findAll(pageable);
        }
        
        model.addAttribute("productPage", productPage);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoryService.findAll());
        
        // Add page numbers for pagination
        int totalPages = productPage.getTotalPages();
        if (totalPages > 0) {
            int[] pageNumbers = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                pageNumbers[i] = i + 1;
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        return "admin/product/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/admin/products?error=Product not found";
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product/add";
    }
}