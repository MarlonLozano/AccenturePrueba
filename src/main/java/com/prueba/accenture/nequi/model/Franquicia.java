package com.prueba.accenture.nequi.model;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("franquicia")
public class Franquicia {

    @Id
    private Integer id_franquicia;
    private String name;

}
