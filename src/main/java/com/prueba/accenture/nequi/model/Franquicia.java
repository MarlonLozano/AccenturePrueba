package com.prueba.accenture.nequi.model;

import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Table("franquicia")
public class Franquicia {


    @Id
    private Integer id_franquicia;

    @NotNull(message = "El campo 'name' no puede estar vacio")
    @Size(min = 1, max = 20, message = "El campo 'name' debe tener entre 1 y 20 caracteres")
    private String name;

}
