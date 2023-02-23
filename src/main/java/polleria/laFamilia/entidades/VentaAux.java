
package polleria.laFamilia.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class VentaAux {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String producto;
    private Integer cantidad;
    private Integer precio;

    
    public VentaAux() {
    }

    public VentaAux(String id, String producto, Integer cantidad, Integer precio) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto: " + producto + ", Cantidad: " + cantidad + "\n";
    }
    
    
    
    
    
    
    
}  