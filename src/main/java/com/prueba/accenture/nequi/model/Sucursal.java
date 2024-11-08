package com.prueba.accenture.nequi.model;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("sucursal")
public class Sucursal {
    @Id
    private Integer id_sucursal;
    private String name;

    @Column("id_franquicia")
    private Integer id_franquicia;

}
