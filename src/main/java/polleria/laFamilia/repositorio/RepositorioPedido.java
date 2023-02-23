package polleria.laFamilia.repositorio;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import polleria.laFamilia.entidades.Pedido;

@Repository
public interface RepositorioPedido extends JpaRepository<Pedido, Integer>{
    
    @Query("SELECT SUM(p.totalPedido) FROM Pedido p")
    public Integer totalVentasPedidos ();
    
    @Query("SELECT p FROM Pedido p WHERE p.creadoFecha = :creadoFecha")
    List<Pedido> buscarPorFecha(@Param("creadoFecha") Date creadoFecha);
    
    @Query("SELECT p FROM Pedido p WHERE p.cliente = :cliente")
    List<Pedido> buscarPorCliente(@Param("cliente") String cliente);
    
    
}