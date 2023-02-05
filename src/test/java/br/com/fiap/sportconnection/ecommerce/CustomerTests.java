package br.com.fiap.sportconnection.ecommerce;

import br.com.fiap.sportconnection.ecommerce.dto.CustomerDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchAddressDTO;
import br.com.fiap.sportconnection.ecommerce.dto.CustomerPatchDTO;
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
import java.time.LocalDate;
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
    @Order(2)
    void contextLoads() {
    }

    void add(CustomerDTO customerDTO) throws Exception {
        System.out.println(customerDTO);
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
    void postCustomers() throws Exception {
        Set<AddressEntity> addresses_customer_01 = new LinkedHashSet<>();
        Set<AddressEntity> addresses_customer_02 = new LinkedHashSet<>();
        AddressEntity address_01 = AddressEntity.builder()
                .id(1L)
                .streetName("XPTO")
                .number("123")
                .neighborhood("Rua")
                .postalCode("12345-000")
                .country("Brasil")
                .city("Praia Grande")
                .build();

        AddressEntity address_02 = AddressEntity.builder()
                .id(2L)
                .streetName("XPTO2")
                .number("1230")
                .neighborhood("Rua2")
                .postalCode("22345-000")
                .country("Brasil")
                .city("São Paulo")
                .build();

        AddressEntity address_03 = AddressEntity.builder()
                .id(3L)
                .streetName("XPTO3")
                .number("1234")
                .neighborhood("Rua3")
                .postalCode("55555-000")
                .country("Brasil")
                .city("Santos")
                .build();

        addresses_customer_01.add(address_01);
        //addresses_customer_01.add(address_02);    //FIXME: Não aceita múltiplos endereços... (não captura o "customer_id")
        addresses_customer_02.add(address_03);

        var customer_01 = new CustomerDTO(
                1L,
                "Zanella86",
                //LocalDate.of(1986, 11, 10) ,
                new SimpleDateFormat("dd-MM-yyyy").parse("10-11-1986"),
                "000111222-1",
                "RG",
                addresses_customer_01
        );

        var customer_02 = new CustomerDTO(
                2L,
                "lakagawa",
                //LocalDate.of(1986, 11, 11),
                new SimpleDateFormat("dd-MM-yyyy").parse("11-11-1986"),
                "000222111-2",
                "RG",
                addresses_customer_02
        );

        add(customer_01);
        add(customer_02);
    }

    @Test
    @Order(2)
    @DisplayName("Cenário de sucesso: Cliente encontrado/modificado")
    void patchCustomer_OK() throws Exception {
        Set<AddressEntity> addresses = new LinkedHashSet<>();
        AddressEntity address_01_patch = AddressEntity.builder()
                .streetName("YPTO")
                .number("321")
                .neighborhood("Av")
                .postalCode("54321-000")
                .country("Brasil")
                .city("Rio de Janeiro")
                .build();

        addresses.add(address_01_patch);

        var customer_01_patch = new CustomerPatchDTO(
                "Zanella",
                //LocalDate.of(2000, 11, 10) ,
                new SimpleDateFormat("dd-MM-yyyy").parse("10-11-2000"),
                "000111222-9",
                "RG",
                addresses
        );

        this.mockMvc.perform(
                        patch(CUSTOMER_URI + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_01_patch))
                )
                .andDo(print())
                .andExpect(status().isCheckpoint());
    }

    @Test
    @Order(3)
    @DisplayName("Cenário de sucesso: Cliente encontrado/modificado")
    void patchCustomerAddress_OK() throws Exception {
        Set<AddressEntity> addresses = new LinkedHashSet<>();
        AddressEntity address_01_patch = AddressEntity.builder()
                .streetName("YPTO")
                .number("789")
                .neighborhood("Av")
                .postalCode("54321-000")
                .country("Brasil")
                .city("Rio de Janeiro")
                .build();

        addresses.add(address_01_patch);

        var customer_01_patch = new CustomerPatchAddressDTO(
                addresses
        );

        this.mockMvc.perform(
                        patch(CUSTOMER_URI + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_01_patch))
                )
                .andDo(print())
                .andExpect(status().isCheckpoint());
    }

    @Test
    @Order(4)
    @DisplayName("Cenário de falha: Cliente não encontrado/modificado")
    void patchCustomer_NOK() throws Exception {
        Set<AddressEntity> addresses = new LinkedHashSet<>();
        AddressEntity address_01 = AddressEntity.builder()
                .id(1L)
                .streetName("XPTO")
                .number("123")
                .neighborhood("Rua")
                .postalCode("12345-000")
                .country("Brasil")
                .city("Praia Grande")
                .build();

        addresses.add(address_01);

        var customer_01_patch = new CustomerPatchDTO(
                "Zanella86",
                //LocalDate.of(1986, 11, 10) ,
                new SimpleDateFormat("dd-MM-yyyy").parse("10-11-1986"),
                "000111222-1",
                "RG",
                addresses
        );

        this.mockMvc.perform(
                        patch(CUSTOMER_URI + "/1000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_01_patch))
                )
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    @Order(5)
    @DisplayName("Cenário de falha: Cliente não encontrado/modificado (Endereço)")
    void patchCustomerAddress_NOK() throws Exception {
        Set<AddressEntity> addresses = new LinkedHashSet<>();
        AddressEntity address_01 = AddressEntity.builder()
                .id(1L)
                .streetName("XPTO4")
                .number("456")
                .neighborhood("Praça")
                .postalCode("44444-000")
                .country("Brasil")
                .city("Praia Teste")
                .build();

        addresses.add(address_01);

        var customer_01_patch = new CustomerPatchAddressDTO(
                addresses
        );

        this.mockMvc.perform(
                        patch(CUSTOMER_URI + "/1000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_01_patch))
                )
                .andDo(print())
                .andExpect(status().isNotModified());
    }

    @Test
    @Order(6)
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
    @Order(7)
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
    @Order(8)
    void putCustomer() throws Exception {
        var customer_02_put = new CustomerDTO(
                1L,
                "Zanella86",
                //LocalDate.of(1986, 11, 10) ,
                new SimpleDateFormat("dd-MM-yyyy").parse("10-11-1986"),
                "000111222-1",
                "RG",
                null
        );
        this.mockMvc.perform(
                        put(CUSTOMER_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(customer_02_put))
                )
                .andDo(print())
                .andExpect(status().isCheckpoint());
    }

    @Test
    @Order(9)
    void removeCustomer() throws Exception {
        this.mockMvc.perform(
                        delete(CUSTOMER_URI + "/2")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(10)
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
