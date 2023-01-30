package br.com.fiap.sportconnection.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street_name")
    private String streetName;
    private String number;
    private String neighborhood;
    @Column(name = "postal_code")
    private String postalCode;
    private String country;
    private String city;
    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

}
