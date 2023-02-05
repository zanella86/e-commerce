package br.com.fiap.sportconnection.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customer", catalog = "my-ecommerce")
public class CustomerEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Long id;

    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    private String document;

    @Column(name = "document_type")
    private String documentType;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="customerEntity")
    private Set<AddressEntity> addresses = new LinkedHashSet<>();

}
