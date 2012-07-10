/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.dao;

import java.util.List;
import sge.entidades.Cliente;

/**
 *
 * @author Propietario
 */
public interface IClienteDAO extends  IDao<Integer, Cliente> {
    Cliente buscarPorCodigo(Integer codigo,boolean habilitado);    
    List<Cliente> buscarPorRazonSocial(String rsocial,boolean habilitado);
    Boolean actualizarCliente(Cliente aCliente); //Marcos
    Cliente buscarPorCuit(String cuit);
}
