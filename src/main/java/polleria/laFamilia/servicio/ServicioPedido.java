
package polleria.laFamilia.servicio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        newPedido.setCreado(new Date());
        newPedido.setDomicilio(domicilio);
        newPedido.setEstadoPedido(EstadoPedido.EN_PREPARACION);
        newPedido.setPagado(false);
        newPedido.setProductos(String.join(", ", productosPedidos));
        newPedido.setTotalPedido(repoVentaAux.totalVentaAux());
        
        repoPedido.save(newPedido);
        
        
        
        repoVentaAux.deleteAll();
        
        
    }
    
//    public void detallePedido( ) {
        
 //       Pedido nuevoPedido = new Pedido();
        
//        Double preciop = precio.doubleValue();
//        productoNuevo.setNombre(nombre);
//        productoNuevo.setDescripcion(descripcion);
//        productoNuevo.setStock(stock);
//        productoNuevo.setPrecio(preciop);
//        productoNuevo.setTipo(tipo);
//          if (habilitado.equals("si")) {
//              productoNuevo.setHabilitado(true);
//          } else {
//              productoNuevo.setHabilitado(false);
//          }
//
//
//        productoRepositorio.save(productoNuevo);
    }


