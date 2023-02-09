package polleria.laFamilia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import polleria.laFamilia.entidades.Pedido;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Integer>{
    
    @Query("SELECT SUM(p.totalPedido) FROM Pedido p")
    public Integer totalVentasPedidos ();
    
    //hacer el servicio que sume los totales
    
}