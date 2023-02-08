
package polleria.laFamilia.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import polleria.laFamilia.entidades.Pedido;
import polleria.laFamilia.repositorio.RepositorioPedido;

@Controller
@RequestMapping("/pedido")
public class controladorPedido {
    
    @Autowired
    private RepositorioPedido repoPedido;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/historial")
    public String listarPedidos (ModelMap modelo){
        List<Pedido> pedidos = repoPedido.findAll();

        modelo.put("pedidos", pedidos);
        return "historialPedidos";
    } 

}
