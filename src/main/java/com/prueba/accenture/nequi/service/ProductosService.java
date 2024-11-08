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

    /**
     * Agrega un nuevo producto a una sucursal específica.
     *
     * @param producto Objeto `Producto` que contiene los detalles del producto a agregar.
     * @return Un `Mono` que emite el producto guardado en la base de datos.
     * @throws BadRequestException si la sucursal asociada al producto no existe.
     */
    public Mono<Producto> addProducto(Producto producto) {
        return sucursalRepository.findByIdSucursal(producto.getId_sucursal()).switchIfEmpty(Mono.error(new BadRequestException("400","SUCURSAL NO ENCONTRADA","REVISAR DATOS DE LA SUCURSAL","MSG-001",400)))
                .flatMap(sucursal ->{
                    log.info("Encontro la Sucursal");
                    return productoRepository.save(producto);
        });
    }

    /**
     * Actualiza el stock de un producto específico para una sucursal.
     *
     * @param idProducto ID del producto que se desea actualizar.
     * @param idSucursal ID de la sucursal donde se encuentra el producto.
     * @param newStock Nuevo valor de stock para el producto.
     * @return Un `Mono` que emite el producto con el stock actualizado. Si el producto no se encuentra, lanza un `BadRequestException`.
     * @throws BadRequestException si no se encuentra el producto con el ID y sucursal especificados.
     */
    public Mono<Producto> updateProducto(Integer idProducto, Integer idSucursal, Integer newStock) {
        return productoRepository.findByProductoAndIdSucursal(idProducto, idSucursal).switchIfEmpty(Mono.error(new BadRequestException("400","PRODUCTO NO ENCONTRADO","REVISAR LA REFERENCIA DEL PRODCUTO","MSG-002",400)))
                .flatMap(producto ->{
                    log.info("Encontro informacion del producto");
                    return productoRepository.updateProductoBySucursal(idSucursal,idProducto, newStock).switchIfEmpty(Mono.just(producto));
                });
    }

    /**
     * Actualiza el nombre de un producto.
     *
     * @param idProducto ID del producto que se desea actualizar.
     * @param idSucursal ID de la sucursal donde se encuentra el producto.
     * @param nameProduct Nuevo nombre para el producto.
     * @return Un `Mono` que emite el producto con el nombre actualizado. Si el producto no se encuentra, lanza un `BadRequestException`.
     * @throws BadRequestException si no se encuentra el producto con el ID y sucursal especificados.
     */
    public Mono<Producto> updateNameProducto(Integer idProducto, Integer idSucursal, String nameProduct) {
        return productoRepository.findByProductoAndIdSucursal(idProducto, idSucursal)
                .switchIfEmpty(Mono.error(new BadRequestException("400","PRODUCTO NO ENCONTRADO","REVISAR LA REFERENCIA DEL PRODCUTO","MSG-002",400)))
                .flatMap(producto ->{
                    log.info("Encontro informacion del producto");
                    return productoRepository.updateProductName(idProducto, nameProduct).switchIfEmpty(Mono.just(producto));
                });
    }

    /**
     * Elimina un producto.
     *
     * @param idProducto ID del producto que se desea eliminar.
     * @param idSucursal ID de la sucursal donde se encuentra el producto.
     * @return Un `Mono` que emite el producto eliminado si existe, o lanza un `BadRequestException` si el producto no se encuentra en la sucursal especificada.
     * @throws BadRequestException si no se encuentra el producto con el ID y sucursal especificados.
     */
    public Mono<Producto> deleteProducto(Integer idProducto, Integer idSucursal) {
        return productoRepository.findByProductoAndIdSucursal(idProducto, idSucursal).switchIfEmpty(Mono.error(new BadRequestException("400","PRODUCTO NO ENCONTRADO EN LA SUCURSAL","REVISAR LA REFERENCIA DEL PRODCUTO","MSG-002",400)))
                .flatMap(producto ->{
                    log.info("Encontro informacion del producto");
                    return productoRepository.deleteProductoBySucursal(idSucursal,idProducto).switchIfEmpty(Mono.just(producto));
                });
    }
}
