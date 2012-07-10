/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sge.clases;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
//import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import sge.interfaces.IReporte;
import sge.metadata.Grupo;

/**
 *
 * @author Propietario
 */
public class ReporteFactory {
    static{
        EntityManagerFactory emf =Persistence.createEntityManagerFactory("SGEReportPU");
        em = emf.createEntityManager();
        
    }
    private static EntityManager em ;
   // private final static Logger log = Logger.getLogger(ReporteFactory.class);
    
    public ReporteFactory(){
        InputStream stream = getClass().getResourceAsStream("/META-INF/persistence.xml");
    }
    public static IReporte getReporte(String key){
        Connection conn= null;
        IReporte aReporte=null;
        
        try {
            conn = getConexion();
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReporteFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(ReporteFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Reporte> reportes = buscarReportePorKey(key);    
       
        if(reportes.size()>0){
            aReporte = reportes.get(0);       
            aReporte.setConeccion(conn);
        }           
  
        return aReporte;
        
    }
    public static void persist(Object object) {
        
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    private static Connection getConexion() throws ClassNotFoundException, SQLException{
        String driver=null;
        String servidor=null;
        String user=null;
        String password=null;
        
        InputStream stream = ReporteFactory.class.getResourceAsStream("/META-INF/conexion.properties");
        
	if (stream == null) {
	//File not found! (Manage the problem)
	}
	Properties props = new java.util.Properties();
        try {
            props.load(stream);
            PropertyConfigurator.configure(props);
            driver = props.getProperty("driver");
            servidor = props.getProperty("servidor");
            user = props.getProperty("usuario");
            password = props.getProperty("password");
            
        } catch (IOException ex) {
              java.util.logging.Logger.getLogger(ReporteFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
	
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(servidor,user,password);
        
        return conn;

    }
    private static List<Reporte> buscarReportePorKey(String sKey){
        
        Query q = em.createQuery(            
        "SELECT e FROM Reporte e WHERE  UPPER(e.keyReporte) LIKE :skey");
        q.setParameter("skey",  sKey.toUpperCase()   );
        return q.getResultList();
    }
    public static List<Grupo> buscarMetadataReporte(){
        
        Query q = em.createQuery(            
        "SELECT e FROM Grupo e ORDER BY e.orden");
        return q.getResultList();
    }
   
}


  