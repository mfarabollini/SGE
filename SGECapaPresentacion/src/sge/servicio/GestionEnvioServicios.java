/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.servicio;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sge.clases.ReporteException;
import sge.dao.*;
import sge.entidades.*;
import sge.clases.ReporteFactory;
import sge.exception.ConectividadException;
import sge.interfaces.IReporte;
import sge.metadata.Grupo;

/**
 *
 * @author Propietario
 */
public class  GestionEnvioServicios {
    private static List<Parametro> parametros;
    
    public static void iniciarParametros() throws ConectividadException{
        IParametroDAO paramDAO = new ParametroDAO();
        
        parametros = paramDAO.buscarTodos();
    }
    public static List<Parametro> getParametros() {
        return parametros;
    }
    
    public static List<?> buscarEntidadPorRazonSocial(String aClase, String rSocial,boolean habilitado){
        List<?> aList = null;
        String nombre = "sge.dao." + aClase + "DAO";

        Class<?> clase;
        try {
            clase = Class.forName(nombre);    
            Object iClass = clase.newInstance();
            Method  method = clase.getDeclaredMethod ("buscarPorRazonSocial", String.class,boolean.class);    
  	    Object[] mainArgs = {rSocial,habilitado};	  
            aList = (List<?>) method.invoke(iClass, mainArgs);               
            //aList = (List<?>) method.invoke(iClass, rSocial,habilitado);            
                   
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return aList;
    }
    public static MedioEnvio buscarMedioEnvioPorCodigo(Integer codigo,boolean habilitado)throws ConectividadException {
        MedioEnvio aMedio;
        IMedioEnvioDAO medioDAO = new MedioEnvioDAO();
        aMedio = medioDAO.buscarPorCodigo(codigo,habilitado);
        return aMedio;
    }
    public static List<MedioEnvio> buscarMediosEnvioPorRazonSocial(String razonSocial,boolean habilitado) throws ConectividadException{
        List<MedioEnvio> mediosList=null;
        IMedioEnvioDAO medioDAO = new MedioEnvioDAO();
        mediosList = medioDAO.buscarPorRazonSocial(razonSocial,habilitado);
        return mediosList;
    }
    
    public static Cliente buscarClientePorCodigo(Integer codigo,boolean habilitado) throws ConectividadException{
        Cliente aCliente;
        Parametro param = getParametro("SHOHAB");
        boolean habLoc = Boolean.valueOf( param.getDetalle().get(0).getValor());
        
        if(!habLoc){
            habilitado=false;
        }
        
        IClienteDAO medioDAO = new ClienteDAO();
        aCliente = medioDAO.buscarPorCodigo(codigo,habilitado);
        return aCliente;
    }
    public static List<Cliente> buscarClientesPorRazonSocial(String razonSocial,boolean habilitado) throws ConectividadException{
        List<Cliente> clientesList = null;
        Parametro param = getParametro("SHOHAB");
        boolean habLoc = Boolean.valueOf( param.getDetalle().get(0).getValor());
        
        if(!habLoc){
            habilitado=false;
        }
        
        IClienteDAO clienteDAO = new ClienteDAO();
        clientesList = clienteDAO.buscarPorRazonSocial(razonSocial,habilitado);
        return clientesList;
    }
    public static boolean guardarEnvio(Envio aEnvio) throws Exception{        
        IEnvioDAO enviosDao = new EnvioDAO();
        enviosDao.persist(aEnvio);
        boolean resultado = imprimirEtiquetasLoc(aEnvio.getLineaEnvioList());
        
        return resultado;
    }
    
    public static List<Envio> buscarEnvio(Date fechaEnvio,Cliente aCliente) throws ConectividadException{
        List<Envio> enviosList = null;
        IEnvioDAO enviosDao = new EnvioDAO();
        if(aCliente != null){
            enviosList = enviosDao.buscarEnvioPorCliente(fechaEnvio, aCliente);
        }else{
            enviosList = enviosDao.buscarEnvioPorFecha(fechaEnvio);
        }
          
        return enviosList;
    }
    public static boolean imprimirEtiquetas(List<LineaEnvio> lineas ) throws ConectividadException, ReporteException{
        boolean resutado=true;
        
        resutado = imprimirEtiquetasLoc(lineas);
        
        return resutado;
    }
    
    public static Envio buscarEnvioPorCodigoBarra(String codigo) throws ConectividadException{
        Envio aEnvio = null;
        IEnvioDAO enviosDao = new EnvioDAO();        
        aEnvio = enviosDao.buscarEnvioPorCodigoBarra(codigo);
        return aEnvio;
    }
 
    
    public static boolean registrarDespacho(Envio aEnvio)throws ConectividadException{
        IEnvioDAO enviosDao = new EnvioDAO();        
        boolean resultado=false;
        
        resultado =enviosDao.registrarDespacho(aEnvio);
        
        return resultado;
    }
    public static boolean guardarCliente(Cliente aCliente) throws ConectividadException{        
        boolean resultado = false;
        IClienteDAO clienteDAO = new ClienteDAO();
        String cuit = aCliente.getCuit();
        
        Cliente aClienteLoc = clienteDAO.buscarPorCuit(cuit);
        
        if(aClienteLoc==null){
            clienteDAO.persist(aCliente);
            resultado=true;
        }
        return resultado;
    }
        
    public static boolean actualizarCliente(Cliente aCliente) throws ConectividadException{        
        IClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.actualizarCliente(aCliente);
        return true;
    }
    
    public static List<Provincia> buscarProvincia() throws ConectividadException{        
        List<Provincia> provinciaList = null;
        IProvinciaDAO provinciaDAO = new ProvinciaDAO();
        provinciaList = provinciaDAO.buscarTodas();
        return provinciaList;
    }
    
    public static Provincia buscarProvinciaNombre(String nomProv) throws ConectividadException{        
        Provincia provincia = null;
        IProvinciaDAO provinciaDAO = new ProvinciaDAO();
        provincia = provinciaDAO.buscarPorNombre(nomProv);
        return provincia;
    }

    public static Parametro getParametro(String pCodKey) {
        Parametro aParam = null;
        for (Iterator<Parametro> it = parametros.iterator(); it.hasNext();) {
            Parametro parametro = it.next();
            if(parametro.getCodKey().equals(pCodKey)){
                aParam= parametro;
                break;
            }
        }
        return aParam;
    }
    
    public static Envio buscarEnvioPorNroGuia(String codigo) throws ConectividadException{  
        Envio aEnvio = null;
        IEnvioDAO enviosDao = new EnvioDAO();        
        aEnvio = enviosDao.buscarEnvioPorNroGuia(codigo);
        return aEnvio;
    }
    public static List<Envio> buscarEnviosCliente(Cliente aCliente) throws ConectividadException{  
        List<Envio> aEnvio = null;
        IEnvioDAO enviosDao = new EnvioDAO();        
        aEnvio = enviosDao.buscarEnviosCliente(aCliente);
        return aEnvio;
    }
    public static boolean registrarConfirmacion(Envio aEnvio,String codigoBarra) throws ConectividadException{
        IEnvioDAO enviosDao = new EnvioDAO();        
        return enviosDao.registrarConfirmacion(aEnvio,codigoBarra);
        
    }
    public static boolean guardarTransporte(MedioEnvio aMedio) throws ConectividadException{        
        boolean resultado = false;
        IMedioEnvioDAO medioEnvioDAO = new MedioEnvioDAO();
      
        MedioEnvio aMedioLoc = medioEnvioDAO.buscarPorCuit(aMedio.getCuit());
        if(aMedioLoc==null){
            medioEnvioDAO.persist(aMedio);
            resultado = true;
        }
        return resultado;
    }
        
    public static boolean actualizarTransporte(MedioEnvio aMedio) throws ConectividadException{        
        IMedioEnvioDAO medioEnvioDAO = new MedioEnvioDAO();
        medioEnvioDAO.actualizarMedio(aMedio);
        return true;
    }
    private static boolean imprimirEtiquetasLoc(List<LineaEnvio> lineas) throws ReporteException, ConectividadException{
        boolean resutado=true;
        
        Iterator iter = lineas.iterator();
        IReporte aReporte = ReporteFactory.getReporte("ETIQ");
        Parametro oParam = getParametro("IMPMUL");
        boolean impresionesMultiples = Boolean.parseBoolean(oParam.getDetalle().get(0).getValor());
        
        while(iter.hasNext()){
            LineaEnvio linea = (LineaEnvio) iter.next();
            
            aReporte.addParametro(linea.getEnvio().getIdenvio().toString());
            aReporte.addParametro(linea.getId().toString());
            if(impresionesMultiples){
                aReporte.setNroCopias(linea.getCantBultos());
            }
            
            aReporte.enviar(IReporte.DestinoReporte.IMPRESORA);
            
            Etiqueta etiqueta = new Etiqueta();
            etiqueta.setFechaImpresion(GregorianCalendar.getInstance().getTime());
            etiqueta.setLinea(linea);
            IEtiquetaDAO etiquetaDAO = new EtiquetaDAO();
            etiquetaDAO.persist(etiqueta);
            
        }
        
        return resutado;
    }

    public static Envio buscarEnvioPorFactura(String nroFactura) throws ConectividadException {
        boolean resultado=false;
        Envio aEnvio = null;
        IEnvioDAO enviosDao = new EnvioDAO(); 
        aEnvio = enviosDao.buscarEnvioPorFactura(nroFactura);
                
        return aEnvio;
    }

    public static List<Grupo> buscarGruposReporte() {
       return  ReporteFactory.buscarMetadataReporte();
    }
    public static IReporte buscarReporte(String codKey){
        return  ReporteFactory.getReporte(codKey);
    }
        public static Envio buscarEnvioPorCodigo(Integer idEnvio) throws ConectividadException{
        Envio aEnvio = null;
        IEnvioDAO enviosDao = new EnvioDAO();        
        aEnvio = enviosDao.buscarEnvioPorCodigo(idEnvio);
        return aEnvio;
    }
    
}
