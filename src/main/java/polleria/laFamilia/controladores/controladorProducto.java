package polleria.laFamilia.controladores;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.entidades.Usuario;
import polleria.laFamilia.entidades.VentaAux;
import polleria.laFamilia.servicio.ServicioProducto;
import polleria.laFamilia.repositorio.RepositorioProducto;
import polleria.laFamilia.repositorio.RepositorioVentaAux;
import polleria.laFamilia.servicio.ServicioPedido;
import polleria.laFamilia.servicio.ServicioUsuario;
import polleria.laFamilia.servicio.ServicioVentaAux;

@Controller
@RequestMapping("/")
public class controladorProducto {

    @Autowired
    private ServicioUsuario usuarioServicio;

    @Autowired
    private RepositorioProducto repoProducto;

    @Autowired
    private ServicioProducto servProducto;

    @Autowired
    private RepositorioVentaAux repoVentaAux;

    @Autowired
    private ServicioVentaAux servVentaAux;

    @Autowired
    private ServicioPedido servPedido;

    @GetMapping("/")
    public String login(@RequestParam(required = false) String exitoReg, @RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {

        if (exitoReg != null) {
            model.put("exitoReg", "Usuario cargado Correctamente");
        }

        if (error != null) {
            model.put("error", "Usuario o Contraseña incorrectos");

        }
        if (logout != null) {
            model.put("logout", "Desconectado correctamente");

        }

        return "login";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/panel")
    public String adminScreen(@RequestParam(required = false) String login, ModelMap model) {
        if (login != null) {
            model.put("exito", "Logueado con exito");
        }
        return "panelScreen";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/agregar")
    public String agregarProducto() {

        return "productos";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/agregar")
    public String productoAgregado(ModelMap modelo, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Integer stock,
            @RequestParam Integer precio, @RequestParam String tipo, @RequestParam String habilitado) {
        try {

            servProducto.agregarProducto(nombre, descripcion, stock, precio, tipo, habilitado);
        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "productos";
            // si hay algun error podriamos poner un return y que derive a otra pagina por ahora no envia a ningun 
        }
        return "productos";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/listar")
    public String listarProductos(ModelMap modelo) {
        List<Producto> productos = repoProducto.findAll();
        List<VentaAux> ventaAuxs = repoVentaAux.findAll();
        Integer totalVentaAux = repoVentaAux.totalVentaAux();

        modelo.put("ventaAuxTotal", totalVentaAux);
        modelo.put("ventaAuxs", ventaAuxs);
        modelo.put("productos", productos);
        return "pantallaProductos";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.addAttribute("producto", servProducto.getOne(id));

        return "productos_modificar";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/modificar/{id}")
    public String productoModificado(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String descripcion, @RequestParam Integer stock,
            @RequestParam Integer precio, @RequestParam String tipo, @RequestParam String habilitado) {
        try {

            servProducto.modificarProducto(id, nombre, descripcion, stock, precio, tipo, habilitado);

            return "redirect:/listar";

        } catch (Exception ex) {
            modelo.put("error", ex.getMessage());
            return "productos";
            // si hay algun error podriamos poner un return y que derive a otra pagina por ahora no envia a ningun 
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
            servProducto.alta(id);
            return "redirect:/listar";
        } catch (Exception ex) {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {

        try {
            servProducto.baja(id);
            return "redirect:/listar";
        } catch (Exception ex) {
            return "redirect:/";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/ventaAux")
    public String sumaProducto(@RequestParam String id, @RequestParam Integer cantidad) {

        try {
            servVentaAux.agregarItem(id, cantidad);
        } catch (Exception e) {

        }

        return "redirect:/listar";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/sacarVentaAux/{id}")
    public String sacarProducto(@PathVariable String id) {

        try {

            servVentaAux.borrarItem(id);

        } catch (Exception e) {

        }

        return "redirect:/listar";

    }

    // REGISTRAR UN USUARIO
    @GetMapping("/registro")
    public String registrarse(@RequestParam(required = false) String exitoReg, ModelMap model) {

        return "registro";
    }

    @PostMapping("/registrar")
    public String guardarUsuario(ModelMap model, @RequestParam(required = false) String exitoReg, @RequestParam String email, @RequestParam String pw1, @RequestParam String pw2) {

        try {
            Usuario u = usuarioServicio.crearUsuario(email, pw1, pw2);
            model.put("exitoReg", "Usuario cargado Correctamente");

        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("Email no puede estar vacio")) {
                model.put("registroError", e.getMessage());
            }
            if (e.getMessage().equalsIgnoreCase("Ya existe un usuario con ese email")) {
                model.put("registroError", e.getMessage());
            }
            if (e.getMessage().equalsIgnoreCase("Las contraseñas no coinciden")) {
                model.put("registroError", e.getMessage());
            }
            return "registro";

        }

        return "login";
    }
    // MODIFICAR CONTRASEÑAS DE UN USUARIO

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/modificarContrasenas")
    public String modificarContrasenas(ModelMap model, HttpSession session) {

        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        model.put("usuarioLogueado", logueado);

        return "modificarContrasena";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/contrasenaModificada")
    public String contrasenaModificada(HttpSession session, ModelMap model, @RequestParam String id, @RequestParam String email, @RequestParam String actualPW, @RequestParam String newPW1, @RequestParam String newPW2) throws Exception {

        try {
            usuarioServicio.modificarContrasena(id, email, actualPW, newPW1, newPW2);

        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("La nueva contraseña no coincide")) {
                model.put("actualizarContrasenaError", e.getMessage());
            }
            if (e.getMessage().equalsIgnoreCase("La contraseña actual no coincide")) {
                model.put("actualizarContrasenaError", e.getMessage());
            }
            Usuario logueado = (Usuario) session.getAttribute("usuariosession");
            model.put("usuarioLogueado", logueado);
    
            return "modificarContrasena";
        }
        model.put("modificacionOK", "Se actualizo la contraseña correctamente");
        session.invalidate();
        SecurityContextHolder.clearContext(); 
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/cargarVenta")
    public String cargarVenta(@RequestParam String clienteNombre, @RequestParam String clienteDomicilio) {

        try {
            servPedido.agregarPedido(clienteNombre, clienteDomicilio);
        } catch (Exception e) {

        }

        return "redirect:/pedido/historial";

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/limpiarPedido")
    public String limpiarPedido (){
        servVentaAux.borrarTodo();
        System.out.println("llsls");
        return "redirect:/listar";
    }

}
