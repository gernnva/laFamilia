
package polleria.laFamilia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.entidades.VentaAux;
import polleria.laFamilia.repositorio.RepositorioProducto;
import polleria.laFamilia.repositorio.RepositorioVentaAux;


public class ServicioVentaAux {
    
    @Autowired
    private RepositorioProducto productoRepositorio;
    
    @Autowired
    private RepositorioVentaAux ventaAuxRepositorio;
    
    @Transactional() 
    public void agregarItem(String id, String nombre, String descripcion, Integer stock, Integer precio, String tipo, String habilitado ) throws Exception {

        Producto producto = productoRepositorio.getById(id);
        VentaAux ventaAux = new VentaAux();
        
        ventaAux.setProducto(producto.getNombre());
        ventaAux.setCantidad(1);
        ventaAux.setPrecio(producto.getPrecio());
        
        ventaAuxRepositorio.save(ventaAux);
        
        /*
        productoRepositorio.save(productoModificar);
        */
    }
}

    
