package sge.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.entidades.LineaEnvio;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-10T09:16:07")
@StaticMetamodel(Etiqueta.class)
public class Etiqueta_ { 

    public static volatile SingularAttribute<Etiqueta, Integer> id;
    public static volatile SingularAttribute<Etiqueta, Date> fechaImpresion;
    public static volatile SingularAttribute<Etiqueta, LineaEnvio> lineaenvio;

}