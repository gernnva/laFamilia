
package polleria.laFamilia.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import polleria.laFamilia.entidades.Producto;
import polleria.laFamilia.entidades.Usuario;
import polleria.laFamilia.repositorio.RepositorioUsuario;


@Controller
@RequestMapping("/usuario")
public class controladorUsuario {
    
    @Autowired
    private RepositorioUsuario repoUsuario;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/listar")
    public String listarProductos(ModelMap modelo) {
        
        List<Usuario> usuarios = repoUsuario.findAll();

        modelo.put("usuarios", usuarios);
        return "usuarios";
    }
    
    

}
