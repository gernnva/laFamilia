
package polleria.laFamilia.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import polleria.laFamilia.entidades.Pedido;
import polleria.laFamilia.entidades.VentaAux;
import polleria.laFamilia.enums.EstadoPedido;
import polleria.laFamilia.repositorio.RepositorioPedido;
import polleria.laFamilia.repositorio.RepositorioVentaAux;

@Service
public class ServicioPedido {
    
    @Autowired
    private RepositorioPedido repoPedido;
    
    @Autowired
    private RepositorioVentaAux repoVentaAux;
    
    
    
    
    public List<Pedido> listarPedidos(){
        return repoPedido.findAll();     
    }
    
    public void agregarPedido(String cliente, String domicilio){
        
        Pedido newPedido = new Pedido();
        
        
        List<VentaAux> productos = repoVentaAux.findAll();
        ArrayList<String> productosPedidos = new ArrayList();
        
        for (VentaAux producto : productos) {
            productosPedidos.add(producto.toString());
            
        }
             
        newPedido.setCliente(cliente);
        newPedido.setCreadoFecha(new Date() );
        newPedido.setCreadoHora(new Date());
        newPedido.setDomicilio(domicilio);
        newPedido.setEstadoPedido(EstadoPedido.EN_PREPARACION);
        newPedido.setPagado(false);
        newPedido.setProductos(String.join(", ", productosPedidos));
        newPedido.setTotalPedido(repoVentaAux.totalVentaAux());
        
        repoPedido.save(newPedido);
        
        
        
        repoVentaAux.deleteAll();
        
        
    }
    public Integer totalVentasPedidos (){
        return repoPedido.totalVentasPedidos();
        
    }
    @Transactional
    public void modificarEstado (Integer id, EstadoPedido estadoPedido) {
        Pedido pedido = repoPedido.getReferenceById(id);
        
        pedido.setEstadoPedido(estadoPedido);
        
        if (estadoPedido == EstadoPedido.ENTREGADO) {
            pedido.setEntregado(new Date());
            
        }
        
    }
    @Transactional
    public void pagado(Integer id) {

        Pedido pedido = repoPedido.getOne(id);
        if (pedido.isPagado() == true) {
            pedido.setPagado(false);
        }else {
            pedido.setPagado(true);
        }
        
    }

    @Transactional()
    public void noPagado(Integer id) {

        Pedido pedido = repoPedido.getOne(id);
        pedido.setPagado(false);
    }

}


