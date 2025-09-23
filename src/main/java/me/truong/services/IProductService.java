package me.truong.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import me.truong.entity.Product;

public interface IProductService {

    <S extends Product> S save(S entity);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    Page<Product> findAll(Pageable pageable);

    List<Product> findAll(Sort sort);

    List<Product> findAllById(Iterable<Long> ids);

    long count();

    void deleteById(Long id);

    void delete(Product entity);

    Optional<Product> findByProductName(String name);

    List<Product> findByProductNameContaining(String name);

    Page<Product> findByProductNameContaining(String name, Pageable pageable);
    
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
