package com.prueba.accenture.nequi.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("sucursal")
public class Sucursal {
    @Id
    private Integer id_sucursal;

    @NotNull(message = "El campo 'name' no puede estar vacio")
    @Size(min = 1, max = 20, message = "El campo 'name' debe tener entre 1 y 20 caracteres")
    private String name;

    @Column("id_franquicia")
    private Integer id_franquicia;

}
