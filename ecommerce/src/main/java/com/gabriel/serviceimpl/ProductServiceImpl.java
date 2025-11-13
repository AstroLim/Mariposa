package com.gabriel.serviceimpl;

import com.gabriel.entity.RiceProductData;
import com.gabriel.model.Product;
import com.gabriel.model.ProductCategory;
import com.gabriel.model.ProductVariation;
import com.gabriel.model.ProductWithVariations;
import com.gabriel.repository.RiceProductDataRepository;
import com.gabriel.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    RiceProductDataRepository riceProductDataRepository;


    public List<Product> getAllProducts() {
        List<RiceProductData> riceProductDataRecords = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        riceProductDataRepository.findAll().forEach(riceProductDataRecords::add);
        Iterator<RiceProductData> it = riceProductDataRecords.iterator();

        while(it.hasNext()) {
            RiceProductData riceProductData = it.next();
            Product product = convertRiceProductToProduct(riceProductData);
            products.add(product);
        }
        return products;
    }
    @Override
    public List<ProductCategory> listProductCategories()
    {
        Map<String,List<Product>> mappedProduct = getCategoryMappedProducts();
        List<ProductCategory> productCategories = new ArrayList<>();
        for(String categoryName: mappedProduct.keySet()){
            ProductCategory productCategory =  new ProductCategory();
            productCategory.setCategoryName(categoryName);
            productCategory.setProducts(mappedProduct.get(categoryName));
            productCategories.add(productCategory);
        }
        return productCategories;
    }
    @Override
    public Map<String,List<Product>> getCategoryMappedProducts()
    {
        // Group rice products by name, with variations stored as separate products
        Map<String,List<Product>> mapProducts = new HashMap<String,List<Product>>();

        List<RiceProductData> riceProductDataRecords = new ArrayList<>();
        List<Product> products;

        riceProductDataRepository.findAll().forEach(riceProductDataRecords::add);
        Iterator<RiceProductData> it = riceProductDataRecords.iterator();

        while(it.hasNext()) {
            RiceProductData riceProductData = it.next();
            // Use riceName as category name (grouping all quantities of the same rice variety)
            String categoryName = riceProductData.getRiceName();

            if(mapProducts.containsKey(categoryName)){
                products = mapProducts.get(categoryName);
            }
            else {
                products = new ArrayList<Product>();
                mapProducts.put(categoryName, products);
            }
            Product product = convertRiceProductToProduct(riceProductData);
            products.add(product);
        }
        return mapProducts;
    }
    
    /**
     * Get products grouped by name with variations
     * This method returns products grouped by rice name, with each group containing variations (quantities)
     */
    public List<ProductWithVariations> getProductsWithVariations() {
        Map<String, ProductWithVariations> productMap = new LinkedHashMap<>();
        
        List<RiceProductData> riceProductDataRecords = new ArrayList<>();
        riceProductDataRepository.findAll().forEach(riceProductDataRecords::add);
        
        for (RiceProductData riceProductData : riceProductDataRecords) {
            String riceName = riceProductData.getRiceName();
            
            if (!productMap.containsKey(riceName)) {
                ProductWithVariations productWithVariations = new ProductWithVariations();
                productWithVariations.setName(riceName);
                productWithVariations.setDescription(riceProductData.getRiceDescription() != null ? 
                    riceProductData.getRiceDescription() : "");
                productWithVariations.setImageFile(riceProductData.getRiceImageFile() != null ? 
                    riceProductData.getRiceImageFile() : "");
                productWithVariations.setVariations(new ArrayList<>());
                productMap.put(riceName, productWithVariations);
            }
            
            // Handle null or invalid price values
            Double priceValue = 0.0;
            if (riceProductData.getRicePrice() != null) {
                priceValue = riceProductData.getRicePrice().doubleValue();
            }
            
            ProductVariation variation = new ProductVariation(
                riceProductData.getRiceID(),
                riceProductData.getRiceQuantity(),
                priceValue
            );
            productMap.get(riceName).getVariations().add(variation);
        }
        
        return new ArrayList<>(productMap.values());
    }

    @Override
    public Product[] getAll() {
            List<RiceProductData> riceProductsData = new ArrayList<>();
            List<Product> products = new ArrayList<>();
            riceProductDataRepository.findAll().forEach(riceProductsData::add);
            Iterator<RiceProductData> it = riceProductsData.iterator();
            while(it.hasNext()) {
                RiceProductData riceProductData = it.next();
                Product product = convertRiceProductToProduct(riceProductData);
                products.add(product);
            }
            Product[] array = new Product[products.size()];
            for  (int i=0; i<products.size(); i++){
                array[i] = products.get(i);
            }
            return array;
        }
    @Override
    public Product get(Integer id) {
        log.info(" Input id >> "+  Integer.toString(id) );
        Product product = null;
        Optional<RiceProductData> optional = riceProductDataRepository.findById(id);
        if(optional.isPresent()) {
            log.info(" Is present >> ");
            RiceProductData riceProductData = optional.get();
            product = convertRiceProductToProduct(riceProductData);
        }
        else {
            log.info(" Failed >> unable to locate id: " +  Integer.toString(id)  );
        }
        return product;
    }
        @Override
        public Product create(Product product) {
            log.info(" add:Input " + product.toString());
            RiceProductData riceProductData = convertProductToRiceProduct(product);
            RiceProductData savedRiceProductData = riceProductDataRepository.save(riceProductData);
            log.info(" add:Input {}", riceProductData.toString());
            return convertRiceProductToProduct(savedRiceProductData);
        }

        @Override
        public Product update(Product product) {
            Optional<RiceProductData> optional = riceProductDataRepository.findById(product.getId());
            if(optional.isPresent()){
                RiceProductData riceProductData = convertProductToRiceProduct(product);
                riceProductData.setRiceID(product.getId());
                RiceProductData updatedRiceProductData = riceProductDataRepository.save(riceProductData);
                return convertRiceProductToProduct(updatedRiceProductData);
            }
            else {
                log.error("Product record with id: {} do not exist",product.getId());
            }
            return null;
        }
    @Override
    public void delete(Integer id) {
        log.info(" Input >> {}",id);
        Optional<RiceProductData> optional = riceProductDataRepository.findById(id);
        if( optional.isPresent()) {
            riceProductDataRepository.delete(optional.get());
            log.info(" Successfully deleted Product record with id: {}",id);
        }
        else {
            log.error(" Unable to locate product with id: {}", id);
        }
    }
    
    /**
     * Convert RiceProductData to Product model
     */
    private Product convertRiceProductToProduct(RiceProductData riceProductData) {
        Product product = new Product();
        product.setId(riceProductData.getRiceID());
        // Combine riceName with quantity for unique product display
        product.setName(riceProductData.getRiceName() + " - " + riceProductData.getRiceQuantity());
        product.setDescription(riceProductData.getRiceDescription() != null ? riceProductData.getRiceDescription() : "");
        product.setCategoryName(riceProductData.getRiceName()); // Use riceName as category (groups all quantities together)
        product.setUnitOfMeasure(riceProductData.getRiceQuantity());
        product.setPrice(riceProductData.getRicePrice() != null ? 
            riceProductData.getRicePrice().toString() : "0.00");
        product.setImageFile(riceProductData.getRiceImageFile() != null ? 
            riceProductData.getRiceImageFile() : "");
        return product;
    }
    
    /**
     * Convert Product model to RiceProductData
     */
    private RiceProductData convertProductToRiceProduct(Product product) {
        RiceProductData riceProductData = new RiceProductData();
        // Use categoryName as it contains the original riceName without quantity
        // If categoryName is not set, extract from product name (remove " - quantity" part)
        String riceName = product.getCategoryName() != null && !product.getCategoryName().isEmpty() 
            ? product.getCategoryName() 
            : product.getName().replaceAll(" - \\d+kg$", "");
        riceProductData.setRiceName(riceName);
        riceProductData.setRiceDescription(product.getDescription());
        riceProductData.setRiceQuantity(product.getUnitOfMeasure());
        try {
            riceProductData.setRicePrice(new java.math.BigDecimal(product.getPrice()));
        } catch (NumberFormatException e) {
            riceProductData.setRicePrice(java.math.BigDecimal.ZERO);
        }
        riceProductData.setRiceImageFile(product.getImageFile() != null ? product.getImageFile() : "");
        return riceProductData;
    }
}
