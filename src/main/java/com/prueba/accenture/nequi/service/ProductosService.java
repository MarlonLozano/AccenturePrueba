package com.prueba.accenture.nequi.service;

import com.prueba.accenture.nequi.exceptions.BadRequestException;
import com.prueba.accenture.nequi.model.Producto;
import com.prueba.accenture.nequi.repository.ProductoRepository;
import com.prueba.accenture.nequi.repository.SucursalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductosService {

    private ProductoRepository productoRepository;
    private SucursalRepository sucursalRepository;

    public ProductosService(ProductoRepository productoRepository, SucursalRepository sucursalRepository) {
        this.productoRepository = productoRepository;
        this.sucursalRepository = sucursalRepository;
    }

    public Mono<Producto> addProducto(Producto producto) {
        return sucursalRepository.findByIdSucursal(producto.getId_sucursal()).switchIfEmpty(Mono.error(new BadRequestException("400","SUCURSAL NO ENCONTRADA","REVISAR DATOS DE LA SUCURSAL","MSG-001",400)))
                .flatMap(sucursal ->{
                    log.info("Encontro la Sucursal");
                    return productoRepository.save(producto);
        });
    }

    public Mono<Producto> updateProducto(Integer idProducto, Integer idSucursal, Integer newStock) {
        return productoRepository.findByProductoAndIdSucursal(idProducto, idSucursal).switchIfEmpty(Mono.error(new BadRequestException("400","PRODUCTO NO ENCONTRADO","REVISAR LA REFERENCIA DEL PRODCUTO","MSG-002",400)))
                .flatMap(producto ->{
                    log.info("Encontro informacion del producto");
                    return productoRepository.updateProductoBySucursal(idSucursal,idProducto, newStock).switchIfEmpty(Mono.just(producto));
                });
    }

    public Mono<Producto> updateNameProducto(Integer idProducto, Integer idSucursal, String nameProduct) {
        return productoRepository.findByProductoAndIdSucursal(idProducto, idSucursal).switchIfEmpty(Mono.error(new BadRequestException("400","PRODUCTO NO ENCONTRADO","REVISAR LA REFERENCIA DEL PRODCUTO","MSG-002",400)))
                .flatMap(producto ->{
                    log.info("Encontro informacion del producto");
                    return productoRepository.updateProductName(idProducto, nameProduct).switchIfEmpty(Mono.just(producto));
                });
    }

    public Mono<Producto> deleteProducto(Integer idProducto, Integer idSucursal) {
        return productoRepository.findByProductoAndIdSucursal(idProducto, idSucursal).switchIfEmpty(Mono.error(new BadRequestException("400","PRODUCTO NO ENCONTRADO EN LA SUCURSAL","REVISAR LA REFERENCIA DEL PRODCUTO","MSG-002",400)))
                .flatMap(producto ->{
                    log.info("Encontro informacion del producto");
                    return productoRepository.deleteProductoBySucursal(idSucursal,idProducto).switchIfEmpty(Mono.just(producto));
                });
    }
}
