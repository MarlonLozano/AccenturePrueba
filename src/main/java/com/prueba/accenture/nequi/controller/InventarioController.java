package com.prueba.accenture.nequi.controller;


import com.prueba.accenture.nequi.exceptions.BadRequestException;
import com.prueba.accenture.nequi.model.*;
import com.prueba.accenture.nequi.service.FranquiciaService;
import com.prueba.accenture.nequi.service.ProductosService;
import com.prueba.accenture.nequi.service.SucursalService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<GeneralResponse> createFranquicia(@RequestBody Franquicia franquicia) {
        GeneralResponse generalResponse = new GeneralResponse();
        return franquiciaService.addFranquicia(franquicia).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("FRANQUICIA "+franquicia.getName()+" CREADA DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PostMapping("/crearSucursal")
    public Mono<GeneralResponse> createSucursal(@RequestBody Sucursal sucursal) {
        GeneralResponse generalResponse = new GeneralResponse();
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
    public Mono<GeneralResponse> createProducto(@RequestBody Producto producto) {
        GeneralResponse generalResponse = new GeneralResponse();
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
    public Mono<GeneralResponse> eliminarProducto(@RequestBody Producto producto) {
        GeneralResponse generalResponse = new GeneralResponse();
        return productosService.deleteProducto(producto.getId_producto(), producto.getId_sucursal()).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("PRODUCTO "+producto.getName()+" EN LA SUCURSAL: "+ producto.getId_sucursal()+ " ELIMINADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PutMapping("/actualizarStock")
    public Mono<GeneralResponse> actualizarStockProducto(@RequestBody Producto producto) {
        GeneralResponse generalResponse = new GeneralResponse();
        return productosService.updateProducto(producto.getId_producto(), producto.getId_sucursal(), producto.getStock()).onErrorResume(BadRequestException.class, err->{
            return Mono.error(new BadRequestException(err.getCode(), err.getMessage(), err.getAction(), err.getMessageId(), err.getCodeHttp()));
        }).flatMap(proccessExit->{
            generalResponse.setCode(200);
            generalResponse.setMessage("PRODUCTO "+producto.getName()+" EN LA SUCURSAL: "+ producto.getId_sucursal()+ " ACTUALIZADO DE MANERA EXITOSA");
            generalResponse.setStatus(200);

            return Mono.just(generalResponse);

        });
    }

    @PutMapping("/actualizarNombreProducto")
    public Mono<GeneralResponse> actualizarNombreProducto(@RequestBody Producto producto) {
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
    public Mono<GeneralResponse> actualizarNombreSucursal(@RequestBody Sucursal sucursal) {
        GeneralResponse generalResponse = new GeneralResponse();
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
    public Mono<GeneralResponse> actualizarNombreFranquicia(@RequestBody Franquicia franquicia) {
        GeneralResponse generalResponse = new GeneralResponse();
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
        return franquiciaService.getTopStockProductByBranch(idFranquicia);
    }
}
