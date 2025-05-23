<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (08/06/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.lista.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script type="text/javascript">

			function seleccionar() 
			{
				if ((document.seleccionarReporteForm.seleccionados.value == null) || (document.seleccionarReporteForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				
				this.opener.document.<bean:write name="seleccionarReporteForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarReporteForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarReporteForm.seleccionados.value;
				<logic:notEmpty name="seleccionarReporteForm" property="nombreCampoValor" scope="session">
					getNombreSeleccionados(document.seleccionarReporteForm.seleccionados.value);
				</logic:notEmpty>			
				<logic:empty name="seleccionarReporteForm" property="nombreCampoValor" scope="session">
					this.opener.<bean:write name="seleccionarReporteForm" property="funcionCierre" scope="session" />;
					this.close();
				</logic:empty>
			}
			
			function getNombreSeleccionados(reporteId)
			{
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/reportes/eliminarReporte" />?funcion=read&Id=' + reporteId, document.seleccionarReporteForm.reporteNombre, 'onGetNombreSeleccionados()');
			}
			
			function onGetNombreSeleccionados()
			{
				<logic:notEmpty name="seleccionarReporteForm" property="nombreCampoValor" scope="session">
					this.opener.document.<bean:write name="seleccionarReporteForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarReporteForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarReporteForm.reporteNombre.value;
					this.close();
				</logic:notEmpty>
			} 
			
			function eliminar()
			{
				if ((document.seleccionarReporteForm.seleccionados.value == null) || (document.seleccionarReporteForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				ajaxSendRequestReceiveInputSincronica('GET', '<html:rewrite action="/reportes/eliminarReporte" />?funcion=eliminar&Id=' + document.seleccionarReporteForm.seleccionados.value, null, 'onEliminar()');
			}
			
			function onEliminar()
			{
				actualizar();
			}
			
			function actualizar()
			{
				document.seleccionarReporteForm.action = '<html:rewrite action="/reportes/listaReporte"/>';
				document.seleccionarReporteForm.submit();
			}

		</script>

		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Representaci�n de la Forma --%>
		<html:form action="/reportes/listaReporte" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="reporteNombre" />
			<input type="hidden" name="actualizar" value="true" />

			<vgcinterfaz:contenedorForma>

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.lista.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Bot�n Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Filtros --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<table width="100%" cellpadding="3" cellspacing="0">

						<%-- Organizaci�n --%>
						<tr class="barraFiltrosForma">
							<td align="right" width="20px">
								<b><vgcutil:message key="jsp.lista.seleccionar.barraherramientas.organizacion" /></b>
							</td>
							<td><bean:write name="seleccionarReporteForm" scope="session" property="rutaCompletaOrganizacion" /></td>
						</tr>

					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Contenedor de Paneles --%>
				<vgcinterfaz:contenedorPaneles height="470" width="470" nombre="panelesSeleccionarReportes" mostrarSelectoresPaneles="false">

					<%-- Panel: Reportes --%>
					<vgcinterfaz:panelContenedor anchoPestana="180px" nombre="panelReportes" mostrarBorde="false">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.lista.panel.reportes.titulo" />
						</vgcinterfaz:panelContenedorTitulo>
						<jsp:include flush="true" page="/paginas/strategos/reportes/listaPrincipalReporte.jsp"></jsp:include>						
					</vgcinterfaz:panelContenedor>
					
				</vgcinterfaz:contenedorPaneles>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:seleccionar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/delete.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:eliminar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.eliminar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:window.close();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>

		</html:form>
	</tiles:put>
</tiles:insert>
