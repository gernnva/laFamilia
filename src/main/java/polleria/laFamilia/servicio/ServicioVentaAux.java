
package polleria.laFamilia.servicio;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.entidades.VentaAux;
import polleria.laFamilia.repositorio.RepositorioProducto;
import polleria.laFamilia.repositorio.RepositorioVentaAux;

@Service
public class ServicioVentaAux {
    
    @Autowired
    private RepositorioProducto productoRepositorio;
    
    @Autowired
    private RepositorioVentaAux ventaAuxRepositorio;
    
    @Transactional() 
    public void agregarItem(String id) throws Exception {

        Producto producto = productoRepositorio.getById(id);
        VentaAux ventaAux = new VentaAux();
        
        ventaAux.setProducto(producto.getNombre());
        ventaAux.setCantidad(1);
        ventaAux.setPrecio(producto.getPrecio());
        
        ventaAuxRepositorio.save(ventaAux);

    }
    
    public void borrarItem (Integer id){
        
        VentaAux ventaAux = ventaAuxRepositorio.getById(id);
        
        ventaAuxRepositorio.delete(ventaAux);
        
    }
    
    @Transactional()
    public void eliminarProducto(Integer id) throws Exception {
     Optional <VentaAux> respuesta = ventaAuxRepositorio.findById(id);
        if (respuesta.isPresent()) {
            VentaAux item = ventaAuxRepositorio.getById(id);
            ventaAuxRepositorio.delete(item);
        }else {
            throw new Exception("No se encontro el ID solicitado");
        }
    }
    

    
    
}

    
