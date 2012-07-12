package sge.entidades;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import sge.entidades.Envio;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-07-12T15:54:12")
@StaticMetamodel(MedioEnvio.class)
public class MedioEnvio_ { 

    public static volatile SingularAttribute<MedioEnvio, Integer> id;
    public static volatile SingularAttribute<MedioEnvio, String> direccion;
    public static volatile SingularAttribute<MedioEnvio, String> cuit;
    public static volatile SingularAttribute<MedioEnvio, Boolean> habilitado;
    public static volatile SingularAttribute<MedioEnvio, String> razonSocial;
    public static volatile SingularAttribute<MedioEnvio, String> tel;
    public static volatile SingularAttribute<MedioEnvio, Date> fechaHora;
    public static volatile ListAttribute<MedioEnvio, Envio> envioList;
    public static volatile SingularAttribute<MedioEnvio, Character> clase;

}