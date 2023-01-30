package br.com.fiap.sportconnection.ecommerce.entity;

import com.fasterxml.jackson.databind.deser.SettableAnyProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="costumer", catalog = "my-ecommerce")
public class Costumer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="id", unique = true, nullable = false)
    private Long id;
    private String name;
    @Column(name = "birth_date")
    private Date birthDate;
    private String document;
    @Column(name = "document_type")
    private String documentType;
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="costumer")
    private Set<Address> addresses = new LinkedHashSet<Address>();

}
