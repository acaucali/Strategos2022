<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Andres Martinez (09/05/2023) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">
		Reporte Detallado Proyectos/Planes de Acción
	</tiles:put>
	
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones JavaScript locales de la pagina Jsp --%>
		<script type="text/javascript">
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() 
			{
				
				var url = 'desdeIniciativas=' + true;
				url = url + '&iniciativaId=' + document.reporteForm.seleccionadoId.value
				url = url + '&perInicial=' + document.reporteForm.fechaInicial.value
				url = url + '&anioInicial=' + document.reporteForm.anoInicial.value
				url = url + '&perFinal=' + document.reporteForm.fechaFinal.value
				url = url + '&anioFinal=' + document.reporteForm.anoFinal.value
				
				
				abrirReporte('<html:rewrite action="/instrumentos/reporteDetalladoProyectosAsociadosPdf"/>?'+url);
	    	 
		 		cancelar();
			}
			
		
		
		</script>
		
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/instrumentos/reporteDetalladoProyectosAsociadosIniciativas">
			<html:hidden property="source" />
			<html:hidden property="seleccionadoId" />
		
			<vgcinterfaz:contenedorForma width="300px" height="180px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					Reporte Detallado Proyectos/Planes de Acción
				</vgcinterfaz:contenedorFormaTitulo>
				
				
				<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
					<!-- Encabezado selector de fechas -->
							<tr>
								<td align="left"  colspan="2" Valign="top"></td>
								<td align="left"  colspan="2" Valign="top">
									<b>Periodo </b>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<b><vgcutil:message key="jsp.reportes.plan.ejecucion.plantilla.ano" /> </b>
									
								</td>
								
							</tr>
							
							<tr>		
								<td align="left"  colspan="2" Valign="top"><b>Desde :</b> </td>
								<td align="left"  colspan="1" Valign="top">		
									&nbsp;
									<select id="fechaInicial"
										class="cuadroTexto" property="fechaInicial">
										<option selected value=1> 1 </option>
										<option value=2> 2 </option>
										<option value=3> 3 </option>
										<option value=4> 4 </option>
									</select>			
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									
									<bean:define id="anoCalculoInicial" toScope="page">
										<bean:write name="reporteForm" property="ano" />
									</bean:define> 
									<html:select property="anoInicial" value="<%=anoCalculoInicial%>"
										styleClass="cuadroTexto">
										<%
											for (int i = 1900; i <= 2050; i++) {
										%>
										<html:option value="<%=String.valueOf(i)%>">
											<%=i%>
										</html:option>
										<%
											}
										%>
									</html:select>													
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="left"  colspan="2" Valign="top"><b>Hasta :</b> </td>
								<td align="left"  colspan="2" Valign="top">	
									&nbsp;
									<select id="fechaFinal"
										class="cuadroTexto" property="fechaFinal">
										<option selected value=1> 1 </option>
										<option value=2> 2 </option>
										<option value=3> 3 </option>
										<option value=4> 4 </option>
									</select>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<bean:define id="anoCalculoFinal" toScope="page">
										<bean:write name="reporteForm" property="ano" />
									</bean:define> 
									<html:select property="anoFinal" value="<%=anoCalculoFinal%>"
										styleClass="cuadroTexto">
										<%
											for (int i = 1900; i <= 2050; i++) {
										%>
										<html:option value="<%=String.valueOf(i)%>">
											<%=i%>
										</html:option>
										<%
											}
										%>
									</html:select>
								</td>
							</tr>							
				</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<%-- Aceptar --%>
					<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarReporte();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;						
					<%-- Cancelar --%>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
					<vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>
	</tiles:put>
</tiles:insert>