package br.com.ithappens.service.processarPedido;

import br.com.ithappens.model.PedidoEstoque;
import br.com.ithappens.service.processarPedido.interfaces.IProcessarPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class ProcessarPedidoFactory {

    @Autowired
    private List<IProcessarPedido> strategyProcessarPedido;

    public IProcessarPedido definirEstrategia(PedidoEstoque pedidoEstoque){
        for(IProcessarPedido iProcessar : strategyProcessarPedido){
            ProcessarPedidoAbstract aProcessar = (ProcessarPedidoAbstract) iProcessar;
            if(aProcessar.deveProcessar(pedidoEstoque.getIsSaida())){
                ((ProcessarPedidoAbstract) iProcessar).pedidoEstoque = pedidoEstoque;
                return iProcessar;
            }
        }

        throw new ValidationException("Pedido de tipo não identificado!");
    }


}
