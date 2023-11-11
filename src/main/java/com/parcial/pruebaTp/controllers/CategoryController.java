//package com.parcial.prueba_tp.controllers;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.web.bind.annotation.*;
//
//import com.parcial.prueba_tp.dtos.CategoryDto;
//
//
//@RestController
//@RequestMapping("/api/categories")
//public class CategoryController {
//
//    // traemos el service
//    @Autowired
//    private CategoryService categoryService;
//
//    @GetMapping
//    public ResponseEntity<List<CategoryDto>> getAll() {
//        List<CategoryDto> categoryDtos = categoryService.findAll();
//        return ResponseEntity.ok(categoryDtos);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
//
//        try {
//
//            CategoryDto foundCategory = categoryService.findById(id);
//            return ResponseEntity.ok(foundCategory);
//
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<CategoryDto> add(@RequestBody CategoryDto categoryDto) {
//
//        try {
//            CategoryDto addedCategory = categoryService.save(categoryDto);
//            return ResponseEntity.ok(addedCategory);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
////        if (updatedCategory != null) {
////            return ResponseEntity.ok(updatedCategory);
////        } else {
////            return ResponseEntity.notFound().build();
////        }
//        try {
//
//            CategoryDto updatedCategory = categoryService.update(id, categoryDto);
//            return ResponseEntity.ok(updatedCategory);
//
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<CategoryDto> delete(@PathVariable Long id) {
//
//        try {
//
//            CategoryDto deletedCategory = categoryService.deleteById(id);
//            return ResponseEntity.ok(deletedCategory);
//
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.status(404).build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(204).build();
////            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//    }
//}
//
