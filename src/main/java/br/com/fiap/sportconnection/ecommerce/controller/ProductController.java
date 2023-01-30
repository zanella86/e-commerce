package br.com.fiap.sportconnection.ecommerce.controller;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import br.com.fiap.sportconnection.ecommerce.entity.Product;
import br.com.fiap.sportconnection.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        var product = productService.get(id);
        if(product.isPresent()){
            //return new ResponseEntity<>(new ObjectMapper().convertValue(product, ProductDTO.class), HttpStatus.FOUND);
            return new ResponseEntity<>(product, HttpStatus.FOUND);     //FIXME: O retorno deve ser o DTO
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> getList() {
        var list = productService.list()
                .stream()
                .map(p -> new ObjectMapper().convertValue(p, ProductDTO.class))
                .collect(Collectors.toList());

        if(list.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        var product = productService.get(id);
        if(product.isPresent()){
            productService.remove(product.get());
            //return new ResponseEntity<>(new ObjectMapper().convertValue(product, ProductDTO.class), HttpStatus.OK);
            return new ResponseEntity<>(product, HttpStatus.OK);    //FIXME: O retorno deve ser o DTO
        }
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductDTO product) {
        productService.update(new ObjectMapper().convertValue(product, Product.class));
        return new ResponseEntity<>(product, HttpStatus.OK);    // TODO: Implementar retorno quando der erro "HttpStatus.NOT_MODIFIED"
    }

    @PatchMapping
    public ResponseEntity<?> alter(@RequestBody ProductDTO product) {
        productService.update(new ObjectMapper().convertValue(product, Product.class));
        return new ResponseEntity<>(product, HttpStatus.OK);    // TODO: Implementar retorno quando der erro "HttpStatus.NOT_MODIFIED"
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody ProductDTO product) {
        productService.add(new ObjectMapper().convertValue(product, Product.class));
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

}
