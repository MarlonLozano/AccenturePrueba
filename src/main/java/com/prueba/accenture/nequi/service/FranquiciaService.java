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

    public Mono<Franquicia> addFranquicia(Franquicia franquicia) {
        return franquiciaRepository.save(franquicia);
    }

    public Flux<Franquicia> getAllFranquicias() {
        return franquiciaRepository.findAll();
    }

    public Mono<Franquicia> getFranquicia(int id) {
        return franquiciaRepository.findById(id);
    }

    public Mono<Franquicia> updateNombreFranquicia(Integer franquiciaId, String nombreFranquicia) {
        return franquiciaRepository.findByIdFranquicia(franquiciaId).switchIfEmpty(Mono.error(new BadRequestException("400","FRANQUICIA NO ENCONTRADA","REVISAR LA FRANQUICIA","MSG-001",400)))
                .flatMap(franquicia ->{
                    log.info("Franquicia encontrada");
                    return franquiciaRepository.updateNombreFranquicia(franquiciaId, nombreFranquicia).switchIfEmpty(Mono.just(franquicia));
                });
    }

    public Flux<ProductoSucursal> getTopStockProductByBranch(Integer idFranquicia) {
        return sucursalRepository.findByIdFranquicia(idFranquicia).switchIfEmpty(Mono.error(new BadRequestException("400","FRANQUICIA NO ENCONTRADA","REVISAR LA FRANQUICIA","MSG-002",400)))
                .flatMap(franquicia -> productoRepository.findBySucursa(franquicia.getId_sucursal()).switchIfEmpty(Mono.error(new BadRequestException("400","SUCURSAL NO ENCONTRADA","REVISAR LA SUCURSAL","MSG-003",400)))
                        .sort((p1, p2) -> p2.getStock().compareTo(p1.getStock()))
                        .take(1)
                        .map(product -> new ProductoSucursal(product, franquicia))
                );
    }


}
