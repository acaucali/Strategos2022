/**
 * 
 */
package com.visiongc.servicio.strategos.message.model;

import java.io.Serializable;

/**
 * @author Kerwin
 *
 */
public class Message  implements Serializable 
{
	static final long serialVersionUID = 0;

    private Long usuarioId;
	private String fecha;
	private Byte estatus;
	private String mensaje;
	private Byte tipo;
	private String fuente;

	/** full constructor */
	public Message(Long usuarioId, String fecha, Byte estatus, String mensaje, Byte tipo, String fuente) 
	{
		this.usuarioId = usuarioId;
		this.fecha = fecha;
		this.estatus = estatus;
		this.mensaje = mensaje;
		this.tipo = tipo;
		this.fuente = fuente;
	}

	/** default constructor */
	public Message() 
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

	public Byte getEstatus() 
	{
		return this.estatus;
	}

	public void setEstatus(Byte estatus) 
	{
		this.estatus = MessageStatus.getStatus(estatus);
	}
	
	public String getMensaje() 
	{
		return this.mensaje;
	}

	public void setMensaje(String mensaje) 
	{
		this.mensaje = mensaje.replace("'", "*");
	}
	
	public Byte getTipo() 
	{
		return this.tipo;
	}

	public void setTipo(Byte tipo) 
	{
		this.tipo = MessageType.getType(tipo);;
	}

	public String getFuente() 
	{
		return this.fuente;
	}

	public void setFuente(String fuente) 
	{
		this.fuente = fuente;
	}
	
	public static class MessageStatus
	{
		private static final byte STATUS_PENDIENTE = 1;
		private static final byte STATUS_PROCESADO = 2;
		
		private static Byte getStatus(Byte status)
		{
			if (status == STATUS_PENDIENTE)
				return new Byte(STATUS_PENDIENTE);
			else if (status == STATUS_PROCESADO)
				return new Byte(STATUS_PROCESADO);
			else
				return null;
		}
		
		public static Byte getStatusPendiente() 
		{
			return new Byte(STATUS_PENDIENTE);
		}
		
		public static Byte getStatusProcesado() 
		{
			return new Byte(STATUS_PROCESADO);
		}
	}
	
	public static class MessageType
	{
		private static final byte TYPE_ALERTA = 0;
		
		private static Byte getType(Byte status)
		{
			if (status == TYPE_ALERTA)
				return new Byte(TYPE_ALERTA);
			else
				return null;
		}
		
		public static Byte getTypeAlerta() 
		{
			return new Byte(TYPE_ALERTA);
		}
	}
}
