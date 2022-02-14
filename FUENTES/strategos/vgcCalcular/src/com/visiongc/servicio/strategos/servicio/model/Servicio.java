/**
 * 
 */
package com.visiongc.servicio.strategos.servicio.model;

import java.io.Serializable;
import java.util.Date;

import com.visiongc.servicio.web.importar.util.VgcFormatter;

/**
 * @author Kerwin
 *
 */
public class Servicio implements Serializable 
{
	static final long serialVersionUID = 0;

    private Long usuarioId;
	private String fecha;
	private String nombre;
	private Byte estatus;
	private String mensaje;
	private String log;
	
	/** full constructor */
	public Servicio(Long usuarioId, String fecha, String nombre, Byte estatus, String mensaje, String log) 
	{
		this.usuarioId = (usuarioId != null ? usuarioId : 1);
		this.fecha = (fecha != null ? fecha : getFechaExacta());
		this.nombre = nombre;
		this.estatus = (estatus != null ? estatus : ServicioStatus.getServicioStatusIniciado());
		this.mensaje = mensaje;
		this.log = log;
	}

	/** default constructor */
	public Servicio() 
	{
	}
	
    public Long getUsuarioId() 
    {
        return this.usuarioId;
    }

    public void setUsuarioId(Long usuarioId) 
    {
        this.usuarioId = usuarioId;
    }

	public String getFecha() 
	{
		return this.fecha;
	}

	public void setFecha(String fecha) 
	{
		this.fecha = fecha;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Byte getEstatus() 
	{
		return this.estatus;
	}

	public void setEstatus(Byte estatus) 
	{
		this.estatus = ServicioStatus.getServicioStatus(estatus);
	}
	
	public String getMensaje() 
	{
		return this.mensaje;
	}

	public void setMensaje(String mensaje) 
	{
		this.mensaje = mensaje.replace("'", "*");
	}

	public String getLog() 
	{
		return this.log;
	}

	public void setLog(String log) 
	{
		this.log = log.replace("'", "*");
	}
	
	public String getFechaExacta() 
	{
		return VgcFormatter.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss a").replace(" AM", "").replace(" PM", "");
	}
	
	public static class ServicioStatus
	{
		private static final byte SERVICIOSTATUS_SUCCESS = 1;
		private static final byte SERVICIOSTATUS_NOSUCCESS = 2;
		private static final byte SERVICIOSTATUS_ENPROCESO = 3;
		private static final byte SERVICIOSTATUS_CANCELADO = 4;
		private static final byte SERVICIOSTATUS_INICIADO = 5;
		private static final byte SERVICIOSTATUS_VISTO = 6;
		
		private static Byte getServicioStatus(Byte status)
		{
			if (status == SERVICIOSTATUS_SUCCESS)
				return new Byte(SERVICIOSTATUS_SUCCESS);
			else if (status == SERVICIOSTATUS_NOSUCCESS)
				return new Byte(SERVICIOSTATUS_NOSUCCESS);
			else if (status == SERVICIOSTATUS_ENPROCESO)
				return new Byte(SERVICIOSTATUS_ENPROCESO);
			else if (status == SERVICIOSTATUS_CANCELADO)
				return new Byte(SERVICIOSTATUS_CANCELADO);
			else if (status == SERVICIOSTATUS_INICIADO)
				return new Byte(SERVICIOSTATUS_INICIADO);
			else if (status == SERVICIOSTATUS_VISTO)
				return new Byte(SERVICIOSTATUS_VISTO);
			else
				return null;
		}
		
		public static Byte getServicioStatusSuccess() 
		{
			return new Byte(SERVICIOSTATUS_SUCCESS);
		}
		
		public static Byte getServicioStatusNoSuccess() 
		{
			return new Byte(SERVICIOSTATUS_NOSUCCESS);
		}

		public static Byte getServicioStatusEnProceso() 
		{
			return new Byte(SERVICIOSTATUS_ENPROCESO);
		}

		public static Byte getServicioStatusCancelado() 
		{
			return new Byte(SERVICIOSTATUS_CANCELADO);
		}

		public static Byte getServicioStatusIniciado() 
		{
			return new Byte(SERVICIOSTATUS_INICIADO);
		}

		public static Byte getServicioStatusVisto() 
		{
			return new Byte(SERVICIOSTATUS_VISTO);
		}
	}
}
