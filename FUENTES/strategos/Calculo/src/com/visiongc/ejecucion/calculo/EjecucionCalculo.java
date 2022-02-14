package com.visiongc.ejecucion.calculo;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;

import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.commons.persistence.hibernate.VgcHibernateSessionFactory;
import com.visiongc.commons.persistence.hibernate.util.VgcHibernateDtdEntityResolver;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.util.FrameworkConnection;

public class EjecucionCalculo {

	public static void main(String[] args) throws FileNotFoundException {
		
		/*
		try{
            
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();
            
            
            JobDetail job = JobBuilder.newJob(TrabajoCalculo.class)
					.withIdentity("Job1", "group1").build();
            
            
            Date startTime = DateBuilder.nextGivenSecondDate(null, 10);
            
            CronTrigger crontrigger = TriggerBuilder
					.newTrigger()
					.withIdentity("Trigger", "group1")
					.startAt(startTime)
					.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
					.build();
            
            scheduler.start();
            scheduler.scheduleJob(job, crontrigger);
          
            
            
        }catch (SchedulerException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
		*/
		
		try {
			
			FrameworkConnection framework = new FrameworkConnection();
			
			ArrayList elementos= new ArrayList();
			
			String ruta= "C:/Vision/context-url.xml";
			
			File archivo = new File(ruta);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
	        Document document = documentBuilder.parse(archivo);
	        document.getDocumentElement().normalize();
	        
	        System.out.println("Elemento raiz:" + document.getDocumentElement().getNodeName());
	        NodeList listaEmpleados = document.getElementsByTagName("context");
	        
	        Element element = null;
	        
	        
	        for (int temp = 0; temp < listaEmpleados.getLength(); temp++) {
                Node nodo = listaEmpleados.item(temp);
                
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    element = (Element) nodo;
                      
                }
            }
	        
            
            System.out.println("username: " + element.getElementsByTagName("username").item(0).getTextContent());
            System.out.println("password: " + element.getElementsByTagName("password").item(0).getTextContent());
            System.out.println("driver: " + element.getElementsByTagName("driver").item(0).getTextContent());
            System.out.println("url: " + element.getElementsByTagName("url").item(0).getTextContent());
            System.out.println("name: " + element.getElementsByTagName("name").item(0).getTextContent());
         
		 
	       Boolean coneccion= false;
	        
	       
	       String url=element.getElementsByTagName("url").item(0).getTextContent();
	       String driver=element.getElementsByTagName("driver").item(0).getTextContent();
	       String username=element.getElementsByTagName("username").item(0).getTextContent();
	       String password=element.getElementsByTagName("password").item(0).getTextContent();
	       
	       
	       /*
	       coneccion= framework.testConnection(element.getElementsByTagName("url").item(0).getTextContent()
	    		   , element.getElementsByTagName("driver").item(0).getTextContent()
	    		   , element.getElementsByTagName("username").item(0).getTextContent()
	    		   , element.getElementsByTagName("password").item(0).getTextContent());
	       
	       System.out.println(coneccion);
	       */
	       	       
	       
	       

	       UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	    	
	    	 System.out.println("");
	    	
	    	usuariosService.close();
	   	
	       
	       
	    	
	       
	       /*
	        * 
	        * 
	        * 
	       
	       UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();
	    	
	    	 System.out.println("");
	    	
	    	usuariosService.close();
	       
	       
	       calculoIndicadoresForm.setAlcance(null);
	       calculoIndicadoresForm.setAno(ano);
	       calculoIndicadoresForm.setAnoFin(anoFin);
	       calculoIndicadoresForm.setBloqueado(bloqueado);
	       calculoIndicadoresForm.setCalculado(calculado);
	       calculoIndicadoresForm.setClaseId(claseId);
	       calculoIndicadoresForm.setCreado(creado);
	       calculoIndicadoresForm.setCreadoUsuarioNombre(creadoUsuarioNombre);
	       calculoIndicadoresForm.setEnEdicion(enEdicion);
	       calculoIndicadoresForm.setFrecuencia(frecuencia);
	       calculoIndicadoresForm.setIndicadorId(indicadorId);
	       calculoIndicadoresForm.setIniciativaId(iniciativaId);
	       calculoIndicadoresForm.setMesFinal(mesFinal);
	       calculoIndicadoresForm.setMesInicial(mesInicial);
	       calculoIndicadoresForm.setModificado(modificado);
	       calculoIndicadoresForm.setModificadoUsuarioNombre(modificadoUsuarioNombre);
	       calculoIndicadoresForm.setNombreIndicador(nombreIndicador);
	       calculoIndicadoresForm.setOrganizacionId(organizacionId);
	       calculoIndicadoresForm.setOrigen(origen);
	       calculoIndicadoresForm.setPerspectivaId(perspectivaId);
	       calculoIndicadoresForm.setPeriodosCero(periodosCero);
	       calculoIndicadoresForm.setPlanId(planId);
	       calculoIndicadoresForm.setPorNombre(porNombre);
	       calculoIndicadoresForm.setReportarErrores(reportarErrores);
	       calculoIndicadoresForm.setRespuesta(respuesta);
	       calculoIndicadoresForm.setSeleccionandoIndicadores(seleccionandoIndicadores);
	       calculoIndicadoresForm.setSerieId(serieId);
	       calculoIndicadoresForm.setStatus(status);
	       
	       */
	       
	       //enviar la info y ejecutar calculo
	       
	       
		}catch (Exception e) {
            e.printStackTrace();
        }
	
		
		
       
	}
}
 