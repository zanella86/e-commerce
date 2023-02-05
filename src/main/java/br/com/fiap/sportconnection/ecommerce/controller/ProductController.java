package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.dto.ProductPatchDTO;
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
        if(product.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return product.get();
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

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.CHECKPOINT)
    public ProductDTO alter(
            @PathVariable("id") Long id,
            @RequestBody ProductPatchDTO productPatchDTO) {
        var productDTO = productService.update(id, productPatchDTO);
        if(productDTO.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_MODIFIED);
        }
        return productDTO.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO add(@RequestBody ProductDTO productDTO) {
        return productService.add(productDTO);
    }

}
