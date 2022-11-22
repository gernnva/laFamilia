
package polleria.laFamilia.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.entidades.VentaAux;
import polleria.laFamilia.servicio.ServicioProducto;
import polleria.laFamilia.repositorio.RepositorioProducto;
import polleria.laFamilia.repositorio.RepositorioVentaAux;
import polleria.laFamilia.servicio.ServicioVentaAux;


@Controller
@RequestMapping("/")
public class controladorProducto {
    
    @Autowired
    private RepositorioProducto repoProducto;
    
    @Autowired
    private ServicioProducto servProducto;
    
    @Autowired
    private RepositorioVentaAux repoVentaAux;
    
    @Autowired
    private ServicioVentaAux servVentaAux;
    
   
    @GetMapping("/")
    public String agregarProducto(){
        // modelo.addAttribute("productos", new Producto());
        return "productos";
    }
    
    @PostMapping("/agregar")
    public String productoAgregado(ModelMap modelo, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Integer stock,
            @RequestParam Integer precio, @RequestParam String tipo, @RequestParam String habilitado){
        try {
            // modelo.addAttribute("productos", new Producto());
            
            servProducto.agregarProducto(nombre, descripcion, stock, precio, tipo, habilitado);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "productos";
            // si hay algun error podriamos poner un return y que derive a otra pagina por ahora no envia a ningun 
        }
        return "productos";
    }
    @GetMapping("/listar")
    public String listarProductos (ModelMap modelo){
        List<Producto> productos = repoProducto.findAll();
        List<VentaAux> ventaAuxs = repoVentaAux.findAll();
        modelo.put("ventaAuxs", ventaAuxs);
        modelo.put("productos", productos);
        return "pantallaProductos";
    }
    
    @GetMapping("/modificar/{id}") //PATHVARIABLE
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("producto", servProducto.getOne(id));

        return "productos_modificar";
    }
    
    @PostMapping("/modificar/{id}")
    public String productoModificado(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Integer stock,
            @RequestParam Integer precio, @RequestParam String tipo, @RequestParam String habilitado){
        try {
            // modelo.addAttribute("productos", new Producto());
            
            servProducto.modificarProducto(id, nombre, descripcion, stock, precio, tipo, habilitado);
            
            return "redirect:/listar";
            
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "productos";
            // si hay algun error podriamos poner un return y que derive a otra pagina por ahora no envia a ningun 
        }
        
    }
    
    
    
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            servProducto.alta(id);
            return "redirect:/listar";
        } catch (Exception ex) {
            return "redirect:/";
        }
    }
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            servProducto.baja(id);
            return "redirect:/listar";
        } catch (Exception ex) {
            return "redirect:/";
        }
    }
    

    @GetMapping("/ventaAux/{id}")
    public String sumarProducto(@PathVariable String id) {
        
        try {
            servVentaAux.agregarItem(id);
        } catch (Exception e) {
            
        }
               
        return "redirect:/listar";
        
    }
    
    @GetMapping("/sacarVentaAux/{id}")
    public String sacarProducto(@PathVariable Integer id) {
        
        try {
            servVentaAux.borrarItem(id);
        } catch (Exception e) {
            
        }
               
        return "redirect:/listar";
    
    
    
    
    
    
}


