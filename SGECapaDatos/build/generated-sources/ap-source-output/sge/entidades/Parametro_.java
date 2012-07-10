package sge.entidades;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.entidades.DetalleParametro;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-10T09:16:07")
@StaticMetamodel(Parametro.class)
public class Parametro_ { 

    public static volatile SingularAttribute<Parametro, Long> id;
    public static volatile SingularAttribute<Parametro, Boolean> habilitado;
    public static volatile SingularAttribute<Parametro, String> codKey;
    public static volatile SingularAttribute<Parametro, String> descripcion;
    public static volatile ListAttribute<Parametro, DetalleParametro> detalle;

}