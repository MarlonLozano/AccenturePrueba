package com.prueba.accenture.nequi.controller;


import com.prueba.accenture.nequi.exceptions.BadRequestException;
import com.prueba.accenture.nequi.model.*;
import com.prueba.accenture.nequi.service.FranquiciaService;
import com.prueba.accenture.nequi.service.ProductosService;
import com.prueba.accenture.nequi.service.SucursalService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private FranquiciaService franquiciaService;
    private SucursalService sucursalService;
    private ProductosService productosService;

    public InventarioController(FranquiciaService franquiciaService, SucursalService sucursalService, ProductosService productosService) {
        this.franquiciaService = franquiciaService;
        this.sucursalService = sucursalService;
        this.productosService = productosService;
    }

    @PostMapping("/crearFranquicia")
    public Mono<GeneralResponse> createFranquicia(@Valid @RequestBody Franquicia franquicia) throws BadRequestException {
        log.info("======INICIO PROCESO DE CREAR FRANQUICIA======");
        GeneralResponse generalResponse = new GeneralResponse();
        this.validateDataFranquicia(franquicia);
        return franquiciaService.addFranquicia(franquicia).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("FRANQUICIA "+franquicia.getName()+" CREADA DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PostMapping("/crearSucursal")
    public Mono<GeneralResponse> createSucursal(@Valid @RequestBody Sucursal sucursal) throws BadRequestException {
        log.info("======INICIO PROCESO DE CREAR SUCURSAL======");
        GeneralResponse generalResponse = new GeneralResponse();
        this.validateDataSucursal(sucursal);
        return sucursalService.addSucursal(sucursal).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("SUCURSAL "+sucursal.getName()+" EN LA FRANQUICIA: "+ sucursal.getId_franquicia()+ " CREADA DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PostMapping("/crearProducto")
    public Mono<GeneralResponse> createProducto(@Valid @RequestBody Producto producto) throws BadRequestException {
        log.info("======INICIO PROCESO DE CREAR PRODUCTO======");
        GeneralResponse generalResponse = new GeneralResponse();
        this.validateDataProducto(producto);
        return productosService.addProducto(producto).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("PRODUCTO "+producto.getName()+" EN LA SUCURSAL: "+ producto.getId_sucursal()+ " CREADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @DeleteMapping("/eliminarProducto")
    public Mono<GeneralResponse> eliminarProducto(@Valid @RequestBody Producto producto) throws BadRequestException {
        log.info("======INICIO PROCESO DE ELIMINAR PRODUCTO======");
        GeneralResponse generalResponse = new GeneralResponse();
        return productosService.deleteProducto(producto.getId_producto(), producto.getId_sucursal()).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("PRODUCTO "+producto.getId_producto()+" EN LA SUCURSAL: "+ producto.getId_sucursal()+ " ELIMINADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PutMapping("/actualizarStock")
    public Mono<GeneralResponse> actualizarStockProducto(@Valid @RequestBody Producto producto) throws BadRequestException {
        log.info("======INICIO PROCESO DE ACTUALIZAR PRODUCTO======");
        GeneralResponse generalResponse = new GeneralResponse();
        return productosService.updateProducto(producto.getId_producto(), producto.getId_sucursal(), producto.getStock()).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("PRODUCTO "+producto.getId_producto()+" EN LA SUCURSAL: "+ producto.getId_sucursal()+ " ACTUALIZADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PutMapping("/actualizarNombreProducto")
    public Mono<GeneralResponse> actualizarNombreProducto(@Valid @RequestBody Producto producto) throws BadRequestException {
        log.info("======INICIO PROCESO DE ACTUALIZAR NOMBRE DE PRODUCTO======");
        GeneralResponse generalResponse = new GeneralResponse();
        return productosService.updateNameProducto(producto.getId_producto(), producto.getId_sucursal(), producto.getName()).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("PRODUCTO "+producto.getName()+" EN LA SUCURSAL: "+ producto.getId_sucursal()+ " ACTUALIZADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PutMapping("/actualizarNombreSucursal")
    public Mono<GeneralResponse> actualizarNombreSucursal(@Valid @RequestBody Sucursal sucursal) throws BadRequestException {
        log.info("======INICIO PROCESO DE ACTUALIZAR NOMBRE DE SUCURSAL======");
        GeneralResponse generalResponse = new GeneralResponse();
        this.validateDataSucursal(sucursal);
        return sucursalService.updateSucursalName(sucursal).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("SUCURSAL "+sucursal.getName()+" ACTUALIZADA DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PutMapping("/actualizarNombreFranquicia")
    public Mono<GeneralResponse> actualizarNombreFranquicia(@Valid @RequestBody Franquicia franquicia) throws BadRequestException {
        log.info("======INICIO PROCESO DE ACTUALIZAR NOMBRE DE FRANQUICIA======");
        GeneralResponse generalResponse = new GeneralResponse();
        this.validateDataFranquicia(franquicia);
        return franquiciaService.updateNombreFranquicia(franquicia.getId_franquicia(),franquicia.getName()).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("FRANQUICIA "+franquicia.getName()+" ACTUALIZADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @GetMapping("/topProductos/{idFranquicia}")
    public Flux<ProductoSucursal> getTopStockProductByBranch(@PathVariable Integer idFranquicia) {
        log.info("======INICIO PROCESO DE CONSULTA DE PRODUCTOS======");
        return franquiciaService.getTopStockProductos(idFranquicia);
    }

    public void validateDataFranquicia(Franquicia franquicia) throws BadRequestException {
        if(franquicia.getName().isBlank()||franquicia.getName().isEmpty()){
            throw new BadRequestException("400","CAMPO 'name' NO PUEDE SER VACIO","VALIDAR LOS DATOS DE ENTRADA","VAL-001",400);
        }

    }

    public void validateDataSucursal(Sucursal sucursal) throws BadRequestException {
        if(sucursal.getName().isBlank()||sucursal.getName().isEmpty()){
            throw new BadRequestException("400","CAMPO 'name' NO PUEDE SER VACIO","VALIDAR LOS DATOS DE ENTRADA","VAL-001",400);
        }

    }

    public void validateDataProducto(Producto producto) throws BadRequestException {
        if(producto.getName().isBlank()||producto.getName().isEmpty()){
            throw new BadRequestException("400","CAMPO 'name' NO PUEDE SER VACIO","VALIDAR LOS DATOS DE ENTRADA","VAL-001",400);
        }

        if(producto.getStock().equals(0)){
            throw new BadRequestException("400","CAMPO 'stock' DEBE SER MAYOR A 0","VALIDAR LOS DATOS DE ENTRADA","VAL-001",400);
        }

    }
}
