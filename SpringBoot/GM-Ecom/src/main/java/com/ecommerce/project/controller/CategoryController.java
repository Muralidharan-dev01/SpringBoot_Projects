package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConfig;
import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    //@Autowired -->field injection is also used for this
    CategoryService categoryService; 

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

   // @GetMapping("/public/categories")
   //public ResponseEntity<List<Category>> getAllCategories(){
   //return categoryService.getAllcategory();
   // return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
   // }
    @RequestMapping(value = "/public/categories",method = RequestMethod.GET)
     public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                              @RequestParam(name = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                              @RequestParam(name = "sortBy",defaultValue =AppConstants.SORT_CATEGORIES_BY,required = false) String sortBy,
                                                              @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_ORDER,required = false) String sortOrder){
        CategoryResponse categoryResponse =categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortOrder);
         return new ResponseEntity<CategoryResponse>(categoryResponse,HttpStatus.OK);
    }
   @PostMapping("/public/categories")
   public ResponseEntity<CategoryDTO> setCategory(@Valid @RequestBody CategoryDTO categoryDTO)
   {   CategoryDTO savedcategoryDTO=categoryService.createCategory(categoryDTO);
      // return"Category added Successfully!";
       return new ResponseEntity<>(savedcategoryDTO,HttpStatus.CREATED);
   }
   @DeleteMapping("/admin/categories/{categoryId}")
   public ResponseEntity <CategoryDTO> deleteCategory(@PathVariable Long categoryId)
   {     CategoryDTO status=categoryService.deleteCategory(categoryId);
       return new ResponseEntity<>(status,HttpStatus.OK);
//       try{
//       String status= categoryService.deleteCategory(categoryId);
//        //return new ResponseEntity<>(status, HttpStatus.OK);
//       //return ResponseEntity.ok(status);//status===>variable
//       return ResponseEntity.status(HttpStatus.OK).body(status); //.status===>Method
//   } catch (ResponseStatusException e) {
//       return new ResponseEntity<>(e.getReason(),e.getStatusCode());}
   }
   @PutMapping("/public/categories/{categoryId}")
   public ResponseEntity<CategoryDTO> updateCategory(@Valid  @RequestBody CategoryDTO categoryDTO,
                                                @PathVariable Long categoryId) {
        CategoryDTO savedCategory=categoryService.updateCategory(categoryDTO,categoryId);
       //return new ResponseEntity<>("Category with Category Id"+categoryId+
               // "and Category "+category.getCategoryName()+"has been updated!!!", HttpStatus.OK);
        return new ResponseEntity<>(savedCategory,HttpStatus.OK);
//     try {
//         Category savedCategory= categoryService.updateCategory(category, categoryId);
//         return new ResponseEntity<>("Category with Category Id"+categoryId+
//                 "and Category "+category.getCategoryName()+"has been updated!!!", HttpStatus.OK);
//
//     }catch (ResponseStatusException e)
//     {
//         return new ResponseEntity<>(e.getReason(),e.getStatusCode());
//     }
   }

}
