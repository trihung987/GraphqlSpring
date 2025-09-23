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

import me.truong.entity.Category;
import me.truong.services.ICategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public String listCategories(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("categoryId"));
        Page<Category> categoryPage;
        
        if (name.isEmpty()) {
            categoryPage = categoryService.findAll(pageable);
        } else {
            categoryPage = categoryService.findByCategoryNameContaining(name, pageable);
        }
        
        model.addAttribute("categoryPage", categoryPage);
        model.addAttribute("name", name);
        
        // Add page numbers for pagination
        int totalPages = categoryPage.getTotalPages();
        if (totalPages > 0) {
            int[] pageNumbers = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                pageNumbers[i] = i + 1;
            }
            model.addAttribute("pageNumbers", pageNumbers);
        }
        
        return "admin/category/list";
    }

    @GetMapping("/searchpaginated")
    public String searchPaginated(Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "") String name) {
        return listCategories(model, page, size, name);
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id).orElse(null);
        if (category == null) {
            return "redirect:/admin/categories?error=Category not found";
        }
        model.addAttribute("category", category);
        return "admin/category/add";
    }
}