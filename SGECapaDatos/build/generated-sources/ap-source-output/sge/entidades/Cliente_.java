package sge.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.entidades.LineaEnvio;
import sge.entidades.Localidad;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-10T09:16:07")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Integer> id;
    public static volatile SingularAttribute<Cliente, String> direccion;
    public static volatile SingularAttribute<Cliente, String> telefonoAlternativo;
    public static volatile SingularAttribute<Cliente, Localidad> localidad;
    public static volatile SingularAttribute<Cliente, String> cuit;
    public static volatile SingularAttribute<Cliente, Boolean> habilitado;
    public static volatile SingularAttribute<Cliente, String> razonSocial;
    public static volatile SingularAttribute<Cliente, String> email;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile ListAttribute<Cliente, LineaEnvio> lineaEnvios;

}