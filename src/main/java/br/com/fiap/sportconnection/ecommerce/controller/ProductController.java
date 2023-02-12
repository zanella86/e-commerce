package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ProductDTO getById(@PathVariable("id") Long id) {
        var product = productService.get(id);
        return product.orElseThrow(() ->  new ResponseStatusException(HttpStatus.NO_CONTENT));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<ProductDTO> getList() {
        var list = productService.list();
        if(list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return list;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        productService.remove(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public ProductDTO update(@RequestBody ProductDTO productDTO) {
        return productService.update(productDTO);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO add(@RequestBody ProductDTO productDTO) {
        return productService.add(productDTO);
    }

}
