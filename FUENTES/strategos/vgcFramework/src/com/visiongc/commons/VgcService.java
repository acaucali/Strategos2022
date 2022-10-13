package com.visiongc.commons;

import java.io.Serializable;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.framework.model.Enviroment;
import com.visiongc.framework.model.Licencia;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.model.Usuario;

/**
 * Interface base con m�todos comunes que deben implementar todos los Servicios
 * de Negocio
 * 
 *  (27/01/2012)
 * 
 * @author Kerwin Arias
 * @version 1.0.1 -
 */
public interface VgcService 
{
	/**
	 * M�todo que lee o instancia un Objeto Persistente
	 * 
	 * @param clase:
	 *            Clase del Objeto Persistente
	 * @param objetoId:
	 *            Id del Objeto Persistente
	 * @return Objeto Persistente
	 */
	public Object load(Class<?> clase, Long objetoId);

	/**
	 * M�todo que lee o instancia un Objeto Persistente
	 * 
	 * @param clase:
	 *            Clase del Objeto Persistente
	 * @param objetoId:
	 *            Id Compuesto del Objeto Persistente
	 * @return Objeto Persistente
	 */
	public Object load(Class<?> clase, Serializable objetoId);

	/**
	 * M�todo que intenta bloquear un conjunto de Objetos relacionados con un
	 * Objeto que ser� creado
	 * 
	 * @param instancia:
	 *            Valor que identifica la sesi�n de conexi�n con el sistema en
	 *            la cual se crear� el Objeto
	 * @param idRelacionados:
	 *            Arreglo de Id�s de Objetos relacionados
	 * @return true si el bloqueo fue exitoso
	 */
	public boolean lockForInsert(String instancia, Object idRelacionados[]);

	/**
	 * M�todo que intenta bloquear un conjunto de Objetos que van a ser
	 * utilizados
	 * 
	 * @param instancia
	 * @param idRelacionados
	 * @return
	 */
	public boolean lockForUse(String instancia, Object idsToUse[]);

	public boolean lockForUse(String instancia, Object objetoId);

	/**
	 * 
	 * M�todo que desbloquea un objeto bloqueado y luego bloquea un objeto nuevo
	 * 
	 * Creado por: Kerwin Arias (26/06/2012)
	 * 
	 * @param instancia
	 * @param actualObjetoId
	 * @param nuevoObjetoId
	 * @return
	 */
	public boolean lockForUse(String instancia, Object actualObjetoId, Object nuevoObjetoId);

	/**
	 * 
	 * M�todo que intenta bloquear para uso un objeto y verifica que el objeto
	 * no haya sido borrado
	 * 
	 * Creado por: Kerwin Arias (26/06/2012)
	 * 
	 * @param instancia
	 * @param claseObjeto
	 * @param nombreCampoClave
	 * @param objetoId
	 * @return true, si el intento de bloqueo fue exitoso o false si el objeto
	 *         ya no existe
	 */
	public Object loadAndLockForUse(String instancia, Class<?> claseObjeto, String nombreCampoClave, Long objetoId) throws Exception;

	/**
	 * M�todo que intenta bloquear un Objeto que va a ser modificado
	 * 
	 * @param instancia:
	 *            Valor que identifica la sesi�n de conexi�n con el sistema en
	 *            la cual se modificar� el Objeto
	 * @param objetoId:
	 *            Id del objeto que se intentar� modificar
	 * @return true si el bloqueo fue exitoso
	 */
	public boolean lockForUpdate(String instancia, Object objetoId);

	/**
	 * M�todo que intenta bloquear un Objeto que va a ser modificado y sus
	 * Objetos relacionados
	 * 
	 * @param instancia:
	 *            Valor que identifica la sesi�n de conexi�n con el sistema en
	 *            la cual se modificar� el Objeto
	 * @param objetoId:
	 *            Id del objeto que se intentar� modificar
	 * @param idRelacionados:
	 *            Arreglo de Id�s de Objetos relacionados
	 * @return true si el bloqueo fue exitoso
	 */
	public boolean lockForUpdate(String instancia, Object objetoId, Object idRelacionados[]);

	/**
	 * M�todo que intenta bloquear un Objeto que va a ser eliminado
	 * 
	 * @param instancia:
	 *            Valor que identifica la sesi�n en la cual se intentar�
	 *            eliminar el objeto
	 * @param objetoId:
	 *            Id del Objeto que se intentar� eliminar
	 * @return true si el bloqueo fue exitoso
	 */
	public boolean lockForDelete(String instancia, Object objetoId);

	/**
	 * M�todo que desbloquea todos los Objetos bloqueados en una sesi�n de
	 * conexi�n con el sistema
	 * 
	 * @param instancia
	 * @return true si el desbloqueo fue exitoso
	 */
	public boolean unlockObject(String instancia);

	/**
	 * M�todo que desbloquea un Objeto bloqueado en una sesi�n de conexi�n con
	 * el sistema
	 * 
	 * @param instancia:
	 *            Valor que identifica la sesi�n en la cual se intentar�
	 *            desbloquear el Objeto
	 * @param objetoId:
	 *            Id del objeto que se intentar� desbloquear
	 * @return true si el desbloqueo fue exitoso
	 */
	public boolean unlockObject(String instancia, Object objetoId);

	/**
	 * 
	 * M�todo que env�a un correo electr�nico configurado desde la aplicacion
	 * 
	 * @param to
	 * @param subject
	 * @param body
	 * @return
	 */
	public int enviarCorreo(String to, String subject, String body, String adjunto, String file);
	
	/**
	 * 
	 * M�todo que env�a un correo electr�nico
	 * 
	 * @param from
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param body
	 * @return
	 */
	public int sendMail(String from, String to, String cc, String bcc, String subject, String body);

	/**
	 * 
	 * M�todo que guarda en persistencia el valor solo lectura de un objeto
	 * persistente
	 * 
	 * @param objeto
	 * @param soloLectura
	 * @param fieldNames
	 * @param fieldValues
	 * @return
	 */
	public boolean saveSoloLectura(Object objeto, boolean soloLectura, String fieldNames[], Object fieldValues[]);

	/**
	 * 
	 * Creado por: Kerwin Arias (27/01/2012)
	 * 
	 * @return Sesion de Persistencia
	 */
	public VgcPersistenceSession getPersistenceSession();

	/**
	 * M�todo que cierra el Servicio
	 * 
	 */
	public void close();
	
	/**
	 * M�todo que chequea el producto
	 * 
	 */
	public Licencia getCheckProducto(Sistema sistema, HttpServletRequest request) throws Exception;
	
	/**
	 * M�todo que setea el producto
	 * 
	 */
	public int setProducto(Sistema sistema, HttpServletRequest request) throws Exception;
	
	/**
	 * M�todo que obtiene un campo blob
	 * 
	 */
	public Blob readBlob(String tabla, String campo, String where, String condicion) throws Exception;
	
	/**
	 * M�todo que obtiene el tipo de Enviroment del sistema
	 * 
	 */
	public Enviroment getEnvironment();
	
	/**
	 * M�todo que obtiene el folder donde se guarda todas los archivo de configuracion del sistema
	 * 
	 */
	public String getFolderMasterConfiguration();
	
	/**
	 * M�todo que obtiene la licencia del sistema
	 * 
	 */
	public Licencia readProducto(Sistema sistema) throws Exception;
	
	/**
	 * M�todo que obtiene la configuracion principal
	 * 
	 */
	public int getMasterConfiguracion(HttpServletRequest req);
	
	public void registrarAuditoriaEvento(Object objeto, Usuario usuario, byte tipoEvento);
}
