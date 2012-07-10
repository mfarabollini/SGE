package sge.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.entidades.Cliente;
import sge.entidades.Envio;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-10T09:16:07")
@StaticMetamodel(LineaEnvio.class)
public class LineaEnvio_ { 

    public static volatile SingularAttribute<LineaEnvio, String> codigoDeBarra;
    public static volatile SingularAttribute<LineaEnvio, String> observaciones;
    public static volatile SingularAttribute<LineaEnvio, Date> fechaConfirmacion;
    public static volatile SingularAttribute<LineaEnvio, Integer> id;
    public static volatile SingularAttribute<LineaEnvio, Envio> envio;
    public static volatile SingularAttribute<LineaEnvio, Cliente> cliente;
    public static volatile SingularAttribute<LineaEnvio, Date> fechaEntrega;
    public static volatile SingularAttribute<LineaEnvio, Integer> cantDevuelta;
    public static volatile SingularAttribute<LineaEnvio, Integer> cantBultos;
    public static volatile SingularAttribute<LineaEnvio, String> nroGuiaTransporte;
    public static volatile SingularAttribute<LineaEnvio, Integer> numeroLinea;
    public static volatile SingularAttribute<LineaEnvio, String> motivoDevolucion;
    public static volatile SingularAttribute<LineaEnvio, String> nroFactura;

}