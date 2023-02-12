package br.com.fiap.sportconnection.ecommerce;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchAddressDTO;
import br.com.fiap.sportconnection.ecommerce.entity.AddressEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String CUSTOMER_URI = "/customer";

    @Test
    void contextLoads() {
    }

    void add(CustomerDTO customerDTO) throws Exception {
        this.mockMvc.perform(
                        post(CUSTOMER_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customerDTO))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    @DisplayName("Cadastro de novos clientes")
    void postCustomers() throws Exception {

        Set<AddressEntity> addresses = new LinkedHashSet<>();

        var address_01_patch = AddressEntity
                .builder()
                .streetName("street 01")
                .number("001")
                .neighborhood("St")
                .postalCode("12345")
                .country("Country 1")
                .city("City 1")
                .build();

        addresses.add(address_01_patch);

        var customer_01 = CustomerDTO
                .builder()
                .id(1L)
                .name("Customer 1")
                .birthDate(new SimpleDateFormat("dd-MM-yyyy").parse("01-12-1990"))
                .document("123456789-1")
                .documentType("RG")
                .addresses(addresses)
                .build();

        var customer_02 = CustomerDTO
                .builder()
                .id(2L)
                .name("Customer 2")
                .birthDate(new SimpleDateFormat("dd-MM-yyyy").parse("02-12-1990"))
                .document("123456789-2")
                .documentType("RG")
                .addresses(addresses)
                .build();

        var customer_03 = CustomerDTO
                .builder()
                .id(3L)
                .name("Customer 3")
                .birthDate(new SimpleDateFormat("dd-MM-yyyy").parse("03-12-1990"))
                .document("123456789-3")
                .documentType("RG")
                .addresses(addresses)
                .build();

        add(customer_01);
        add(customer_02);
        add(customer_03);
    }

    @Test
    @Order(2)
    @DisplayName("Cenário de sucesso: Cliente encontrado")
    void getCustomer_OK() throws Exception {
        this.mockMvc.perform(
                        get(CUSTOMER_URI + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound());
    }

    @Test
    @Order(3)
    @DisplayName("Cenário de falha: Cliente não encontrado")
    void getCustomer_NOK() throws Exception {
        this.mockMvc.perform(
                        get(CUSTOMER_URI + "/2000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    @DisplayName("Cliente modificado")
    void putCustomer() throws Exception {
        var customer_03_put = CustomerDTO
                .builder()
                .id(3L)
                .name("Customer 3a")
                .birthDate(new SimpleDateFormat("dd-MM-yyyy").parse("04-12-1990"))
                .document("123456789-4")
                .documentType("RG")
                .build();

        this.mockMvc.perform(
                        put(CUSTOMER_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_03_put))
                )
                .andDo(print())
                .andExpect(status().isCheckpoint());
    }

    @Test
    @Order(5)
    @DisplayName("Cliente removido")
    void removeCustomer() throws Exception {
        this.mockMvc.perform(
                        delete(CUSTOMER_URI + "/2")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    //@Test
    @Order(6)
    @DisplayName("Cenário de sucesso: Cliente encontrado/modificado")
    void patchCustomerAddress_OK() throws Exception {
        Set<AddressEntity> addresses = new LinkedHashSet<>();

        var address_01_patch = AddressEntity
                .builder()
                .id(1L)
                .streetName("street 01")
                .number("001")
                .neighborhood("St")
                .postalCode("12345")
                .country("Country 1")
                .city("City 1")
                .build();

        addresses.add(address_01_patch);

        CustomerPatchAddressDTO customer_address_patch_01 = CustomerPatchAddressDTO
                .builder()
                .addresses(addresses)
                .build();

        this.mockMvc.perform(
                        patch(CUSTOMER_URI + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_address_patch_01))
                )
                .andDo(print())
                .andExpect(status().isCheckpoint());
    }

    //@Test
    @Order(7)
    @DisplayName("Cenário de falha: Cliente não encontrado/modificado (Endereço)")
    void patchCustomerAddress_NOK() throws Exception {
        Set<AddressEntity> addresses = new LinkedHashSet<>();

        var address_01_patch = AddressEntity
                .builder()
                .id(1L)
                .streetName("street 01a")
                .number("0010")
                .neighborhood("St1")
                .postalCode("123456")
                .country("Country 1a")
                .city("City 1a")
                .build();

        addresses.add(address_01_patch);

        CustomerPatchAddressDTO customer_address_patch_01 = CustomerPatchAddressDTO
                .builder()
                .addresses(addresses)
                .build();

        this.mockMvc.perform(
                        patch(CUSTOMER_URI + "/5000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_address_patch_01))
                )
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    @Order(8)
    @DisplayName("Listagem dos clientes")
    void listCustomer() throws Exception {
        this.mockMvc.perform(
                        get(CUSTOMER_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isFound());
    }

}
