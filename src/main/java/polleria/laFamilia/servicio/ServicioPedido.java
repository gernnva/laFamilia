
package polleria.laFamilia.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import polleria.laFamilia.entidades.Pedido;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.repositorio.RepositorioPedido;



public class ServicioPedido {
    
    @Autowired
    private RepositorioPedido repositorioPedido;
    
    public List<Producto> listarPedidos(){
        return repositorioPedido.findAll();     
    }
    
    public void detallePedido( ) {
        
        Pedido nuevoPedido = new Pedido();
        
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

}
