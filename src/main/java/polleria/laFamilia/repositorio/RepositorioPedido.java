package polleria.laFamilia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polleria.laFamilia.entidades.Pedido;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Integer>{
    
}