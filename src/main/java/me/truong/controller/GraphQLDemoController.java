package me.truong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import me.truong.services.ICategoryService;
import me.truong.services.IProductService;

@Controller
@RequestMapping("/admin/graphql")
public class GraphQLDemoController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/products/add")
    public String addProductGraphQL(Model model) {
        return "admin/product/add-graphql";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductGraphQL(@PathVariable Long id, Model model) {
        productService.findById(id).ifPresent(product -> model.addAttribute("product", product));
        return "admin/product/add-graphql";
    }
}