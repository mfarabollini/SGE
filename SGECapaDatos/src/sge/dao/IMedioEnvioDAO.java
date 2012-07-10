/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import sge.entidades.MedioEnvio;

/**
 *
 * @author Propietario
 */
public interface IMedioEnvioDAO extends  IDao<Integer, MedioEnvio>  {
    MedioEnvio buscarPorCodigo(Integer codigo, boolean habilitado);    
    List<MedioEnvio> buscarPorRazonSocial(String rsocial,boolean habilitado);
    Boolean actualizarMedio(MedioEnvio aMedio); //Marcos
    MedioEnvio buscarPorCuit(String cuit);
}
