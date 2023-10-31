<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Andres Martinez (26/10/2023) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">
	<%-- Titulo --%>
	<tiles:put name="title" type="String">
		Reporte Resumido de Explicaciones
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
	<%-- Función JavaScript externa --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>
	<%-- Función JavaScript externa --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
				
		<%-- Funciones JavaScript locales de la pagina Jsp --%>
		<script type="text/javascript">
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() 
			{
				
				var url = 'fechaDesde=' + document.reporteForm.fechaDesde.value
				url = url + '&fechaHasta=' + document.reporteForm.fechaHasta.value
				url = url + '&objetoId=' + document.reporteForm.objetoSeleccionadoId.value
				url = url + '&objetoKey=' + document.reporteForm.objetoKey.value
				if (document.reporteForm.todosAno.checked == true) {
					url = url + '&todos=' + true;
				} else {
					url = url + '&todos=' + false;
				}
				
				
				if (document.reporteForm.tipoReporte[0].checked)
					abrirReporte('<html:rewrite action="/reportes/explicaciones/resumidaExplicacionPdf"/>?'+url);
				else if (document.reporteForm.tipoReporte[1].checked)
					abrirReporte('<html:rewrite action="/reportes/explicaciones/resumidaExplicacionXls"/>?'+url);
									    	 
		 		cancelar();
			}				
			
			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}
			
			function seleccionarFechaInicio() 
			{			
				mostrarCalendarioConFuncionCierre('document.reporteForm.fechaDesde' , document.reporteForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
			
			function seleccionarFechaTerminacion() 
			{
				mostrarCalendarioConFuncionCierre('document.reporteForm.fechaHasta' , document.reporteForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
					
			
		</script>
		
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/explicaciones/resumida">
		
		<html:hidden property="objetoSeleccionadoId" />
		<html:hidden property="objetoKey" />
		<vgcinterfaz:contenedorForma width="420px" height="250px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="jsp.gestionariniciativas.menu.reportes.datos.basicos" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="140px" width="350px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="100px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor panelContenedorTabla">
							<tr>
								<%-- Visible --%>
								<td colspan="left"> <vgcutil:message
										key="jsp.editariniciativa.ficha.todos.ano" />
								</td>
								<td>
									<html:checkbox styleClass="botonSeleccionMultiple" property="todosAno" />																	
								</td>

							</tr>
							<tr>
								<td align="left"><vgcutil:message
										key="jsp.pagina.instrumentos.fecha" /></td>
								<td><html:text  property="fechaDesde"
										onkeypress="ejecutarPorDefecto(event)" size="30"
										maxlength="10" styleClass="cuadroTexto" /> <span
									style="padding: 2px"> <img style="cursor: pointer"
										onclick="seleccionarFechaInicio();"
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>"
										border="0" width="10" height="10"
										title="<vgcutil:message 
										key="boton.calendario.alt" />"
										
										>
								</span></td>

							</tr>
							<tr>
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.fechafin" /></td>

								<td><html:text property="fechaHasta"
										onkeypress="ejecutarPorDefecto(event)" size="30"
										maxlength="10" styleClass="cuadroTexto" /> <span
									style="padding: 2px"> <img style="cursor: pointer"
										onclick="seleccionarFechaTerminacion();"
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>"
										border="0" width="10" height="10"
										title="<vgcutil:message 
											key="boton.calendario.alt" />">
								</span></td>
							</tr>							
						</table>
					</vgcinterfaz:panelContenedor>

					
				<%-- Panel: Salida --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="salida">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="panelContenedor panelContenedorTabla">
							
							
							<tr>
								<td colspan="3">
									<html:radio property="tipoReporte" value="1" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.pdf" />&nbsp;&nbsp;&nbsp;
									<html:radio property="tipoReporte" value="2" /><vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.excel" />
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>
						</table>
					</vgcinterfaz:panelContenedor>
					
					<%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
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
				</vgcinterfaz:contenedorPaneles>				
			</vgcinterfaz:contenedorForma>
		</html:form>		
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		
	</tiles:put>
</tiles:insert>
