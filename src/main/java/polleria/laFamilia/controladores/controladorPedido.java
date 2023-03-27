
package polleria.laFamilia.controladores;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import polleria.laFamilia.entidades.Pedido;
import polleria.laFamilia.enums.EstadoPedido;
import polleria.laFamilia.repositorio.RepositorioPedido;
import polleria.laFamilia.servicio.ServicioPedido;

@Controller
@RequestMapping("/pedido")
public class controladorPedido {
    
    @Autowired
    private RepositorioPedido repoPedido;
    
    @Autowired
    private ServicioPedido servPedido;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/historial")
    public String listarPedidos (ModelMap modelo){
        List<Pedido> pedidos = repoPedido.findAll();
        Integer totalPedidos = repoPedido.totalVentasPedidos();
        
        Collections.reverse(pedidos);

        modelo.put("totalHistorialPedido", totalPedidos);
        modelo.put("pedidos", pedidos);
        return "historialPedidos";
    }
    
    @GetMapping("/estadoPedido/{id}")
    public String modificarEstado(@PathVariable Integer id, ModelMap modelo) {
        
        Pedido pedido = repoPedido.getOne(id);
        modelo.put("estadoPedido", pedido);
        modelo.put("estadoDelEnvio", EstadoPedido.values());
       
        return "estadoPedido";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/estadoPedido/{id}")
    public String modificadoEstado(@PathVariable Integer id, @RequestParam EstadoPedido estadoPedido) {
        
        Date fechaPedido = (repoPedido.getById(id)).getCreadoFecha();
        String cliente = repoPedido.getById(id).getCliente();     

        try {
            
            servPedido.modificarEstado(id, estadoPedido);
            return "redirect:/pedido/porFechaYCliente" + "?fecha=" + fechaPedido + "&cliente=" + (URLEncoder.encode(cliente, StandardCharsets.UTF_8.toString()));
            
        } catch (Exception e) {
            
        }
               
        return "redirect:/pedido/pedidosPorFecha" + "?fechaPedido=" + fechaPedido;
        
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/pagado/{id}")
    public String pagado(@PathVariable Integer id) {

        try {
            servPedido.pagado(id);
            Date fechaPedido = repoPedido.getById(id).getCreadoFecha();
            String cliente = repoPedido.getById(id).getCliente();      
            return "redirect:/pedido/porFechaYCliente" + "?fecha=" + fechaPedido + "&cliente=" + (URLEncoder.encode(cliente, StandardCharsets.UTF_8.toString())) ;
        } catch (Exception ex) {
            return "redirect:/";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/pedidosPorFecha")
    public String obtenerPedidosPorFecha(@RequestParam("fechaPedido") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaSeleccionada, Model model) {
        
        List<Pedido> pedidos = repoPedido.buscarPorFecha(fechaSeleccionada);
        Integer acumulador = 0;
        
        for (Pedido pedido : pedidos) {
            acumulador = pedido.getTotalPedido() + acumulador;
   
        }
         
        Collections.reverse(pedidos);
        
        model.addAttribute("totalHistorialPedido", acumulador);
        model.addAttribute("pedidos", pedidos);
        return "historialPedidos";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/pedidosPorCliente")
    public String obtenerPedidoPorCliente (@RequestParam String cliente, ModelMap model) {
        
        List<Pedido> pedidos = repoPedido.buscarPorCliente(cliente);
        Integer acumulador = 0;
        
        for (Pedido pedido : pedidos) {
            acumulador = pedido.getTotalPedido() + acumulador;
   
        }
         
        Collections.reverse(pedidos);
        
        model.addAttribute("totalHistorialPedido", acumulador);
        model.addAttribute("pedidos", pedidos);
        return "historialPedidos";
        
        
    }
    
    @GetMapping("/porFechaYCliente")
    public String buscarPorFechaYCliente(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha, @RequestParam("cliente") String cliente, ModelMap model) {
    
        List<Pedido> pedidos = repoPedido.buscarPorFechaYCliente(fecha, cliente);
        
        
        Integer acumulador = 0;
        
        for (Pedido pedido : pedidos) {
            acumulador = pedido.getTotalPedido() + acumulador;
   
        }
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("totalHistorialPedido", acumulador);
        
        return "historialPedidos";
        // return repoPedido.buscarPorFechaYCliente(fecha, cliente);
    }
}
