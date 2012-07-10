package sge.metadata;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.metadata.Grupo;
import sge.metadata.ParamReporte;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-10T09:16:10")
@StaticMetamodel(ReporteMetadata.class)
public class ReporteMetadata_ { 

    public static volatile SingularAttribute<ReporteMetadata, Integer> id;
    public static volatile SingularAttribute<ReporteMetadata, Grupo> grupo;
    public static volatile SingularAttribute<ReporteMetadata, String> keyreporte;
    public static volatile SingularAttribute<ReporteMetadata, Boolean> habilitado;
    public static volatile ListAttribute<ReporteMetadata, ParamReporte> paramReporteList;
    public static volatile SingularAttribute<ReporteMetadata, String> nombrereporte;
    public static volatile SingularAttribute<ReporteMetadata, String> reporte;

}