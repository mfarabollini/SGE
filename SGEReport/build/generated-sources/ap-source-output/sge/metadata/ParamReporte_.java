package sge.metadata;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.metadata.ParamReportePK;
import sge.metadata.ReporteMetadata;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-12T15:54:16")
@StaticMetamodel(ParamReporte.class)
public class ParamReporte_ { 

    public static volatile SingularAttribute<ParamReporte, ParamReportePK> paramReportePK;
    public static volatile SingularAttribute<ParamReporte, Integer> orden;
    public static volatile SingularAttribute<ParamReporte, ReporteMetadata> reporteMetadata;
    public static volatile SingularAttribute<ParamReporte, String> descripcion;
    public static volatile SingularAttribute<ParamReporte, Boolean> entidad;
    public static volatile SingularAttribute<ParamReporte, String> clase;
    public static volatile SingularAttribute<ParamReporte, Boolean> obligatorio;

}