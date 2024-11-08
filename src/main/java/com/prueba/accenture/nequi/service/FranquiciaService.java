package com.prueba.accenture.nequi.service;

import com.prueba.accenture.nequi.exceptions.BadRequestException;
import com.prueba.accenture.nequi.model.Franquicia;
import com.prueba.accenture.nequi.model.ProductoSucursal;
import com.prueba.accenture.nequi.repository.FranquiciaRepository;
import com.prueba.accenture.nequi.repository.ProductoRepository;
import com.prueba.accenture.nequi.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FranquiciaService {

    private final SucursalRepository sucursalRepository;
    private final ProductoRepository productoRepository;
    private FranquiciaRepository franquiciaRepository;

    public FranquiciaService(FranquiciaRepository franquiciaRepository, SucursalRepository sucursalRepository, ProductoRepository productoRepository) {
        this.franquiciaRepository = franquiciaRepository;
        this.sucursalRepository = sucursalRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Crea una nueva franquicia.
     *
     * @param franquicia Objeto `Franquicia` que contiene los detalles de la nueva franquicia a agregar.
     * @return Un `Mono` que emite la franquicia guardada en la base de datos.
     */
    public Mono<Franquicia> addFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }

    /**
     * Actualiza el nombre de una franquicia específica.
     *
     * @param franquiciaId ID de la franquicia que se desea actualizar.
     * @param nombreFranquicia Nuevo nombre para la franquicia.
     * @return Un `Mono` que emite la franquicia actualizada o, si no se encuentra, un error de tipo `BadRequestException`.
     * @throws BadRequestException si la franquicia con el ID proporcionado no existe.
     */
    public Mono<Franquicia> updateNombreFranquicia(Integer franquiciaId, String nombreFranquicia) {
        return franquiciaRepository.findByIdFranquicia(franquiciaId).switchIfEmpty(Mono.error(new BadRequestException("400","FRANQUICIA NO ENCONTRADA","REVISAR LA FRANQUICIA","MSG-001",400)))
                .flatMap(franquicia ->{
                    log.info("Franquicia encontrada");
                    return franquiciaRepository.updateNombreFranquicia(franquiciaId, nombreFranquicia).switchIfEmpty(Mono.just(franquicia));
                });
    }

    /**
     * Obtiene el producto con el mayor stock de una franquicia específica.
     *
     * @param idFranquicia ID de la franquicia para la cual se desea consultar el producto con mayor stock.
     * @return Un `Flux` que emite un único objeto `ProductoSucursal`, que representa el producto con el mayor stock en la sucursal correspondiente de la franquicia.
     * @throws BadRequestException si la franquicia o la sucursal no existen.
     */
    public Flux<ProductoSucursal> getTopStockProductos(Integer idFranquicia) {
        return sucursalRepository.findByIdFranquicia(idFranquicia).switchIfEmpty(Mono.error(new BadRequestException("400","FRANQUICIA NO ENCONTRADA","REVISAR LA FRANQUICIA","MSG-002",400)))
                .flatMap(franquicia -> productoRepository.findBySucursa(franquicia.getId_sucursal()).switchIfEmpty(Mono.error(new BadRequestException("400","SUCURSAL NO ENCONTRADA","REVISAR LA SUCURSAL","MSG-003",400)))
                        .sort((product1, product2) -> product2.getStock().compareTo(product1.getStock()))
                        .take(1)
                        .map(product -> new ProductoSucursal(product, franquicia))
                );
    }


}
