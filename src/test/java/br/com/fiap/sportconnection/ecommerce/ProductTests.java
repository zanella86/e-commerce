package br.com.fiap.sportconnection.ecommerce;

import br.com.fiap.sportconnection.ecommerce.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String PRODUCT_URI = "/product";

    @Test
    void contextLoads() {
    }

    void add(ProductDTO productDTO) throws Exception {
        this.mockMvc.perform(
                        post(PRODUCT_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(productDTO))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    @DisplayName("Cadastro de novos produtos")
    void postProducts() throws Exception {
        var product_smartphone = new ProductDTO(
                1L,
                "A123",
                "Smartphone",
                "64GB",
                10,
                BigDecimal.valueOf(1200.99)
        );
        var product_notebook = new ProductDTO(
                2L,
                "Model_A456",
                "Notebook",
                "Processor CORE i5",
                5,
                BigDecimal.valueOf(2499.00)
        );
        var product_monitor = new ProductDTO(
                3L,
                "M135",
                "Monitor LED",
                "17''",
                8,
                BigDecimal.valueOf(799.00)
        );

        add(product_smartphone);
        add(product_notebook);
        add(product_monitor);
    }

    @Test
    @Order(2)
    @DisplayName("Cen??rio de sucesso: Produto encontrado")
    void getProduct_OK() throws Exception {
        this.mockMvc.perform(
                        get(PRODUCT_URI + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    @Order(3)
    @DisplayName("Cen??rio de falha: Produto n??o encontrado")
    void getProduct_NOK() throws Exception {
        this.mockMvc.perform(
                        get(PRODUCT_URI + "/2000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    @DisplayName("Produto modificado")
    void putProduct() throws Exception {
        var product_monitor_put = ProductDTO
                .builder()
                .id(3L)
                .code("AOC999")
                .name("Monitor LED 4K")
                .description("19''")
                .stockQuantity(50)
                .unityPrice(BigDecimal.valueOf(1799.99))
                .build();

        this.mockMvc.perform(
                        put(PRODUCT_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(product_monitor_put))
                )
                .andDo(print())
                .andExpect(status().isCheckpoint());
    }

    @Test
    @Order(5)
    @DisplayName("Produto removido")
    void removeProduct() throws Exception {
        this.mockMvc.perform(
                        delete(PRODUCT_URI + "/2")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @DisplayName("Listagem dos produtos")
    void listProducts() throws Exception {
        this.mockMvc.perform(
                        get(PRODUCT_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound());
    }

}
