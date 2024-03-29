package polleria.laFamilia.servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import polleria.laFamilia.entidades.Usuario;
import polleria.laFamilia.enums.Role;
import polleria.laFamilia.repositorio.RepositorioUsuario;

@Service
public class ServicioUsuario implements UserDetailsService {
    
    @Autowired
    private RepositorioUsuario usuarioRepositorio;
    

    
    @Transactional
    public Usuario crearUsuario(String email, String pw1, String pw2) throws Exception{

        validar(email,pw1,pw2);
        Usuario usuario= new Usuario();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setEmail(email);
        usuario.setPassword(encoder.encode(pw1));
        usuario.setRole(Role.USER);
        
        return usuarioRepositorio.save(usuario);
        
    }
    @Transactional
    public void modificarContrasena(String id, String email,String pwActual, String newPW1, String newPW2) throws Exception{
        
        Usuario usuario = usuarioRepositorio.getOne(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(pwActual, usuario.getPassword())) {
            if (newPW1.equals(newPW2)) {
                usuario.setPassword(encoder.encode(newPW1));
                
            } else {
                throw new Exception("La nueva contraseña no coincide");
            }

        } else  {
            throw new Exception("La contraseña actual no coincide");
            
        }

        
    }
    @Transactional
    public Usuario findByEmail(String email){
        return usuarioRepositorio.findByEmail(email);
    }
    
    @Transactional
    public void cambiarRol(String id) throws Exception{
    
    	Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRole().equals(Role.USER)) {
    			
    		usuario.setRole(Role.ADMIN);
    		
    		}else if(usuario.getRole().equals(Role.ADMIN)) {
    			usuario.setRole(Role.USER);
    		}
    	}
    }
    
    public void validar(String email, String pw1, String pw2) throws Exception{
        if (email == null || email.isEmpty()) {
            throw new Exception("Email no puede estar vacio");
        }
        if (usuarioRepositorio.findByEmail(email)!= null) {
            throw new Exception("Ya existe un usuario con ese email");
        }
        if (pw1 == null || pw2 == null || pw1.isEmpty() || pw2.isEmpty()) {
            throw new Exception("Las contraseñas no pueden estar vacias");
        }
        if (!pw1.equals(pw2)) {
            throw new Exception("Las contraseñas no coinciden");
        }
        
        
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRole());//ROLE_ADMIN O ROLE_USER
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);
            return user;

        } else {
            return null;
        }
    }


}
