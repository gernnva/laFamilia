package polleria.laFamilia.entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import polleria.laFamilia.enums.EstadoPedido;



@Entity
public class Pedido{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer numPedido;
    
    private String cliente;
    private String Domicilio;
    
    // variable para saber quien tomo el pedido
    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;
    
    @Temporal(TemporalType.DATE)
    private Date creadoFecha;
    
    @Temporal(TemporalType.TIME)
    private Date creadoHora;

    public Date getCreadoFecha() {
        return creadoFecha;
    }

    public void setCreadoFecha(Date creadoFecha) {
        this.creadoFecha = creadoFecha;
    }

    public Date getCreadoHora() {
        return creadoHora;
    }

    public void setCreadoHora(Date creadoHora) {
        this.creadoHora = creadoHora;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date entregado;
    
    private boolean pagado;
    
    private String productos;
    
    private Integer totalPedido;

    public Pedido() {
    }

    public Pedido(String cliente, String Domicilio, EstadoPedido estadoPedido, Date creadoFecha, Date creadoHora, Date entregado, boolean pagado, String productos, Integer totalPedido) {
        this.cliente = cliente;
        this.Domicilio = Domicilio;
        this.estadoPedido = estadoPedido;
        this.creadoFecha = creadoFecha;
        this.creadoHora = creadoHora;
        this.entregado = entregado;
        this.pagado = pagado;
        this.productos = productos;
        this.totalPedido = totalPedido;
    }

    public Integer getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(Integer numPedido) {
        this.numPedido = numPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getDomicilio() {
        return Domicilio;
    }

    public void setDomicilio(String Domicilio) {
        this.Domicilio = Domicilio;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getEntregado() {
        return entregado;
    }

    public void setEntregado(Date entregado) {
        this.entregado = entregado;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public Integer getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Integer totalPedido) {
        this.totalPedido = totalPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" + "numPedido=" + numPedido + ", cliente=" + cliente + ", Domicilio=" + Domicilio + ", estadoPedido=" + estadoPedido + ", creadoFecha=" + creadoFecha + ", creadoHora=" + creadoHora + ", entregado=" + entregado + ", pagado=" + pagado + ", productos=" + productos + ", totalPedido=" + totalPedido + '}';
    }


    
    
    
    
    
    
    
    
}