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
		<vgcutil:message key="jsp.pagina.instrumentos.reporte.proyectos.asociados" />
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
				var url = '&anio=' + document.reporteForm.ano.value;
				if (document.reporteForm.todosAno.checked == true) {
					url = url + '&todos=' + true;
				} else {
					url = url + '&todos=' + false;
				}
				url = url + '&estatus='+ document.reporteForm.estatus.value;
				var cooperativo = document.getElementById('selectCooperante');
				if (cooperativo != null)
					url = url + '&cop=' + cooperativo.value;
				url = url + '&perInicial=' + document.reporteForm.fechaInicial.value
				url = url + '&anioInicial=' + document.reporteForm.anoInicial.value
				url = url + '&perFinal=' + document.reporteForm.fechaFinal.value
				url = url + '&anioFinal=' + document.reporteForm.anoFinal.value
				
				
				abrirReporte('<html:rewrite action="/instrumentos/reporteDetalladoProyectosAsociadosPdf"/>?'+url);
	    	 
		 		cancelar();
			}
			
		
		
		</script>
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/instrumentos/reporteDetalladoProyectosAsociados">
		
		<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
		
		<vgcinterfaz:contenedorForma width="460px" height="340px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
		
		<%-- Título--%>
			<vgcinterfaz:contenedorFormaTitulo>..::					
				<vgcutil:message key="jsp.pagina.instrumentos.reporte.proyectos.asociados" />
			</vgcinterfaz:contenedorFormaTitulo>
			
			
					
						<table class="panelContenedor" cellspacing="3" cellpadding="0" border="0">
							
							<tr>			
								<td align="left"  colspan="2" Valign="top"><b><vgcutil:message key="jsp.editariniciativa.ficha.anioformulacion" /></b></td>						
								<td colspan="3">
									<bean:define id="anoCalculo" toScope="page">
										<bean:write name="reporteForm" property="ano" />
									</bean:define> <html:select property="ano" value="<%=anoCalculo%>"
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
								<%-- Visible --%>
								<td align="left"  colspan="2" Valign="top"><b><vgcutil:message key="jsp.editariniciativa.ficha.todos.ano" /></b></td>
								<td colspan="3">																											
									<html:checkbox styleClass="botonSeleccionMultiple" property="todosAno" />
								</td>
								
							</tr>		
							
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="left"  colspan="2" Valign="top"><b><vgcutil:message key="jsp.pagina.instrumentos.cooperante" /></b></td>
								<td width="20%" >
									<select class="cuadroCombinado" name="reporteForm" id="selectCooperante">
									<option value="0" selected >Todos</option>
									<logic:iterate name="reporteForm" property="cooperantes" id="cop">
									
										<bean:define id="cooperanteId" toScope="page"><bean:write name='cop' property='cooperanteId' /></bean:define>								
										<bean:define id="nombre" toScope="page"><bean:write name='cop' property='nombre' /></bean:define>
									
										<logic:equal name='reporteForm' property='cooperanteId' value='<%=cooperanteId.toString()%>'>
													<option value="<%=cooperanteId%>" selected><%=nombre%></option>
										</logic:equal>
											<logic:notEqual name='reporteForm' property='cooperanteId' value='<%=cooperanteId.toString()%>'>
														<option value="<%=cooperanteId%>"><%=nombre%></option>
											</logic:notEqual>
										</logic:iterate>										
									</select>
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="left"  colspan="2" Valign="top"><b><vgcutil:message key="jsp.pagina.instrumentos.estatus" /></b></td>
								<td><html:select property="estatus" styleClass="cuadroTexto" size="1">
									<html:option value="0">
										Todos
									</html:option>
									<html:option value="1">
										<vgcutil:message key="jsp.pagina.instrumentos.estatus.sinIniciar" />
									</html:option>
									<html:option value="2">
										<vgcutil:message key="jsp.pagina.instrumentos.estatus.ejecucion" />
									</html:option>
									<html:option value="3">
										<vgcutil:message key="jsp.pagina.instrumentos.estatus.cancelado" />
									</html:option>
									<html:option value="4">
										<vgcutil:message key="jsp.pagina.instrumentos.estatus.suspendido" />
									</html:option>
									<html:option value="5">
										<vgcutil:message key="jsp.pagina.instrumentos.estatus.culminado" />
									</html:option>
								
								</html:select></td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							
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