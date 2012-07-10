package sge.metadata;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.metadata.ReporteMetadata;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-10T09:16:10")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, Integer> id;
    public static volatile SingularAttribute<Grupo, Integer> orden;
    public static volatile SingularAttribute<Grupo, Boolean> habilitado;
    public static volatile SingularAttribute<Grupo, String> descripcion;
    public static volatile ListAttribute<Grupo, ReporteMetadata> reporteMetadataList;

}