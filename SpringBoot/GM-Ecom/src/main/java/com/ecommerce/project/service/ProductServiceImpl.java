package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import com.ecommerce.project.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {


        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));
        boolean ifProductNotPresent=true;
        List<Product> products=category.getProducts();
        for(int i=0;i<products.size();i++)
        {
            if(products.get(i).getProductName().equals(productDTO.getProductName())){
                ifProductNotPresent=false;
                break;
            }
        }
        if(ifProductNotPresent){
        Product product=modelMapper.map(productDTO,Product.class);
        product.setProductImage("default.png");
        product.setCategory(category);
        Double specialPrice= product.getPrice()-(product.getDiscount()*0.01*product.getPrice());
        product.setSpecialPrice(specialPrice);

        Product savedProduct=productRepository.save(product);
            return modelMapper.map(savedProduct,ProductDTO.class);
        }
        else
        {
            throw new APIException("Product Already exists!!!");
        }


    }

    @Override
    public ProductResponse getAllProduct(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder= sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findAll(pageDetails);
       List<Product> products=pageProducts.getContent();

        //List<Product> products=productRepository.findAll();

        List <ProductDTO> productDTO=products.stream().
                map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
                //.collect(Collectors.toList());

        if(products.isEmpty())
        {
            throw new APIException("No Products Exist !!");
        }
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category= categoryRepository.findById(categoryId).
                orElseThrow(()->new ResourceNotFoundException("Category","CategoryId",categoryId));

        Sort sortByAndOrder= sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageDetails=PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findByCategoryOrderByPriceAsc(category,pageDetails);

        //List<Product> products=productRepository.findByCategoryOrderByPriceAsc(category);
        List<Product>products=pageProducts.getContent();
        if(products.size()==0)
        {
            throw new APIException("NO products exists for the Category:"+category.getCategoryName());
        }
        List <ProductDTO> productDTO=products.stream().
                map(product -> modelMapper.map(product,ProductDTO.class)).toList();
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTO);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());
        return productResponse;
    }

    @Override
    public ProductResponse getProductsBYKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy,String sortOrder ) {

     Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")
                        ?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

     Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
     Page<Product> pageProducts=productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%",pageDetails);

     //List<Product>products=productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");

      List<Product> products=pageProducts.getContent();
      if(products.size()==0)
      {
          throw new APIException("Products not found with the keyword:"+keyword);
      }
     List<ProductDTO> productDTOS=products.stream()
             .map(product -> modelMapper.map(product,ProductDTO.class)).toList();

     ProductResponse productResponse=new ProductResponse();
     productResponse.setContent(productDTOS);
     return productResponse;

    }

    @Override
    public ProductDTO updateProduct(Long productId,  ProductDTO productDTO) {
        //Get Product from DB
        Product productFromDb=productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));
        Product product=modelMapper.map(productDTO,Product.class);
        //Update the product
        productFromDb.setProductName(product.getProductName());
        productFromDb.setProductDescription(product.getProductDescription());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        Double specialPrice= product.getPrice()-(product.getDiscount()*0.01*product.getPrice());
        productFromDb.setSpecialPrice(specialPrice);

        //Save to DB
        Product savedProduct =productRepository.save(productFromDb);
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {

        Product deleteProduct=productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));

        productRepository.delete(deleteProduct);

        return modelMapper.map(deleteProduct,ProductDTO.class);

    }

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {

        //Get product from the DB
        Product product=productRepository.findById(productId).
                orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));

        //Upload Image to Server & Get the file name from the server
        //String path="images/"; -->goes to app properties
        String fileName=fileService.uploadImage(path,image);

        //Update the new file name to the product
        product.setProductImage(fileName);
        //save the product to DB
        productRepository.save(product);

        //return the  Dto
        return modelMapper.map(product,ProductDTO.class);
    }

//    private String uploadImage(String path, MultipartFile image) throws IOException {
//
//        //File name of original file
//         String oGFileName=image.getOriginalFilename();
//
//        //Rename the file unique-->Generate the unique FileName
//        String randomId=UUID.randomUUID().toString();
//        //String randomId= UUID.randomUUID().toString().concat(oGFileName.substring(oGFileName.lastIndexOf('.')));
//        String fileName=randomId.concat(oGFileName.substring(oGFileName.lastIndexOf('.')));
//
//        String filePath=path+ File.separator+fileName;
//        //Check if path exists or Create
//        File folder= new File(path);
//
//        if(!folder.exists())
//            folder.mkdir();
//
//        //upload the file to server
//        Files.copy(image.getInputStream(), Paths.get(filePath));
//
//       return fileName;
//    }
}
