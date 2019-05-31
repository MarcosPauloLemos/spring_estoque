package br.com.ithappens.controller;

import br.com.ithappens.service.interfaces.IPedidoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private IPedidoEstoqueService pedidoEstoqueService;

    @PostMapping(value="/criarPedido")
    private ResponseEntity criarPedido(@RequestParam("filialId") Long filialId,
                                       @RequestParam("codigoCliente") Long codigoCliente,
                                       @RequestParam("isSaida") Boolean isSaida){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoEstoqueService.criarPedidoEstoque(filialId,codigoCliente,isSaida));
    }

    @PostMapping(value="/criarItemPedido")
    private ResponseEntity criarItemPedido(@RequestParam("pedidoId") Long pedidoId,
                                           @RequestParam("produtoId") Long produtoId,
                                           @RequestParam("quantidade") BigDecimal quantidade){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoEstoqueService.criarItemPedido(pedidoId,produtoId,quantidade));
    }

    @GetMapping(value = "/recuperarPedido")
    private ResponseEntity recuperarPedido(@RequestParam("pedidoId") Long pedidoId){
        return ResponseEntity.ok().body(pedidoEstoqueService.recuperarPedidoCompleto(pedidoId));
    }

    @PutMapping(value = "/cancelarPedido")
    private ResponseEntity cancelarPedido(@RequestParam("pedidoId") Long pedidoId){
        pedidoEstoqueService.cancelarPedido(pedidoId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping(value = "/processarPedido")
    private ResponseEntity processarPedido(@RequestParam("pedidoId") Long pedidoId){
        pedidoEstoqueService.processarPedido(pedidoId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
