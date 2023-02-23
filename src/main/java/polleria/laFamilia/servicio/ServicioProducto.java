
package polleria.laFamilia.servicio;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.repositorio.RepositorioProducto;
import polleria.laFamilia.repositorio.RepositorioVentaAux;

@Service
public class ServicioProducto {
    
    @Autowired
    private RepositorioProducto productoRepositorio;
    
    @Autowired
    private RepositorioVentaAux repoVentaAux;
    
    public void validaciones(String nombre, String descripcion, Integer precio, String tipo) throws Exception { 
        if (nombre == null || nombre.isEmpty()) {
        throw new Exception("Es necesario colocar el nombre del producto");
        } 
        if (descripcion == null || descripcion.isEmpty()) {
        throw new Exception("Es necesario colocar la descripcion del producto");
        }
        if (precio < 0 && precio >10000) {
        throw new Exception("El precio no es correcto ");
        }
        if (tipo == null || tipo.isEmpty()) {
        throw new Exception("Es necesario colocar un tipo del producto");
        }
    }
   
    
    public List<Producto> listarProducto(){
         return productoRepositorio.findAll();     
    }
   
    @Transactional() 
    public void agregarProducto(String nombre, String descripcion, Integer stock, Integer precio, String tipo, String habilitado ) throws Exception {

        validaciones(nombre, descripcion ,precio , tipo);

        Producto productoNuevo = new Producto();
        productoNuevo.setNombre(nombre);
        productoNuevo.setDescripcion(descripcion);
        productoNuevo.setStock(stock);
        productoNuevo.setPrecio(precio);
        productoNuevo.setTipo(tipo);
          if (habilitado.equals("si")) {
              productoNuevo.setHabilitado(true);
          } else {
              productoNuevo.setHabilitado(false);
          }


        productoRepositorio.save(productoNuevo);
    }
    
    @Transactional() 
    public void modificarProducto(String id, String nombre, String descripcion, Integer stock, Integer precio, String tipo, String habilitado ) throws Exception {

        validaciones(nombre, descripcion ,precio , tipo);

        Producto productoModificar = productoRepositorio.getById(id);
        
        
        productoModificar.setNombre(nombre);
        productoModificar.setDescripcion(descripcion);
        productoModificar.setStock(stock);
        productoModificar.setPrecio(precio);
        productoModificar.setTipo(tipo);
          if (habilitado.equals("si")) {
              productoModificar.setHabilitado(true);
          } else {
              productoModificar.setHabilitado(false);
          }


        productoRepositorio.save(productoModificar);
    }
    
    public Producto alta(String id) {

        Producto producto = productoRepositorio.getOne(id);

        producto.setHabilitado(true);
        return productoRepositorio.save(producto);
    }

    @Transactional()
    public Producto baja(String id) {

        Producto producto = productoRepositorio.getOne(id);
        
        producto.setHabilitado(false);
        return productoRepositorio.save(producto);
    }
    
    public Producto getOne(String id){
        return productoRepositorio.getOne(id);
    }
   
    @Transactional()
    public void eliminarProducto(String id) throws Exception {
     Optional <Producto> respuesta = productoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            productoRepositorio.deleteById(id);
        }else {
            throw new Exception("No se encontro el ID solicitado");
        }
    }
    
    
    @Transactional
    public Producto getById(String id) throws Exception{
        if (productoRepositorio.getById(id) == null) {
            throw new Exception("No se pudo encontrar el producto");
        }
        return productoRepositorio.getById(id);
    }
    
    
        
        
    
}


