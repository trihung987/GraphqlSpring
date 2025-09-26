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
@RequestMapping("/")
public class DefaultController {


    @GetMapping
    public String listProducts() {
         
        return "redirect:/admin/categories";
    }

   
}