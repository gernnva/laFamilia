package polleria.laFamilia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import polleria.laFamilia.entidades.VentaAux;

@Repository
public interface RepositorioVentaAux extends JpaRepository<VentaAux, Integer>{
    
    @Query("SELECT SUM(a.precio) FROM VentaAux a")
    public Integer totalVentaAux ();

    
    
}
