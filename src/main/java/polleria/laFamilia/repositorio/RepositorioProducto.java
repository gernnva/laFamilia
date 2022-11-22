package polleria.laFamilia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polleria.laFamilia.entidades.Producto;

@Repository
public interface RepositorioProducto extends JpaRepository<Producto, String>{
    
}