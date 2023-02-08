
package polleria.laFamilia.servicio;

import java.util.List;
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
    
    @Autowired
    private ServicioProducto productoServicio;
    
    @Transactional() 
    public void agregarItem(String id, Integer cantidad) throws Exception {

        Producto producto = productoRepositorio.getById(id);
        List <VentaAux> ventaAux = ventaAuxRepositorio.findAll();
        int bandera=0;
        
        for(VentaAux buscandoProducto : ventaAux){
            if (buscandoProducto.getProducto().equals(producto.getTipo()+" "+producto.getNombre())) {
                
                buscandoProducto.setCantidad(buscandoProducto.getCantidad()+cantidad);
                buscandoProducto.setPrecio(producto.getPrecio()*buscandoProducto.getCantidad());
                
                producto.setStock((producto.getStock() - cantidad));
                bandera = 1;
    
            } 
        }  
        if (bandera == 0) {
                VentaAux newVentaAux = new VentaAux();

                newVentaAux.setProducto(producto.getTipo() + " " + producto.getNombre());
                newVentaAux.setCantidad(cantidad);
                newVentaAux.setPrecio(producto.getPrecio()*cantidad);

                ventaAuxRepositorio.save(newVentaAux);
                producto.setStock((producto.getStock() - cantidad));
            
        }

    }
    
    public void borrarItem (String id){
        
        List<Producto> producto = productoRepositorio.findAll();
        VentaAux ventaAux = ventaAuxRepositorio.getById(id);
        
        ;
        
        
        
        for(Producto buscandoProducto : producto){
            String armado = buscandoProducto.getTipo()+ " " + buscandoProducto.getNombre();
            if(ventaAux.getProducto().equalsIgnoreCase(armado)){
                buscandoProducto.setStock(buscandoProducto.getStock()+ventaAux.getCantidad());
                
            }
        }
        
        
        ventaAuxRepositorio.delete(ventaAux);
        
    }
    
    @Transactional()
    public void eliminarProducto(String id) throws Exception {
     Optional <VentaAux> respuesta = ventaAuxRepositorio.findById(id);
        if (respuesta.isPresent()) {
            VentaAux item = ventaAuxRepositorio.getById(id);
            ventaAuxRepositorio.delete(item);
        }else {
            throw new Exception("No se encontro el ID solicitado");
        }
    }
    @Transactional 
    public Integer mostrarTotal(){
        
        return ventaAuxRepositorio.totalVentaAux();
    }
    
    public void borrarTodo(){
        ventaAuxRepositorio.deleteAll();
    }
    

            
 }