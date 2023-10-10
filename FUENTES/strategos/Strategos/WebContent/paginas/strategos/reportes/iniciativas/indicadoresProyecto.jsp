<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Creado por: Gustavo Chaparro (01/09/2013) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<bean:define id="tituloIniciativas">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>
	
	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombreSingular" />
	</bean:define>

		<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionariniciativas.menu.reportes.proyectos.indicadores" arg0="<%= tituloIniciativas %>"/>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			
	
			function cancelar() 
			{
				window.close();						
			}
			
			function generarReporte() 
			{
			 	 if(!<%= session.getAttribute("isAdmin") %> && document.reporteForm.alcance.value == 3 ){
			 		 alert ('Este reporte  solo puede ser ejecutado desde una cuenta Administrador');
			 	 }else{
			 		var url = '&alcance=' + document.reporteForm.alcance.value;						
					url = url + '&filtroNombre=' + document.reporteForm.filtroNombre.value;
					url = url + '&selectHitoricoType=' + document.reporteForm.selectHitoricoType.value;
					url = url + '&orientacion=H';
					url = url + '&tipo='+document.reporteForm.tipo.value;
					url = url + '&estatus='+document.reporteForm.estatus.value;
					url = url + '&ano=' +document.reporteForm.ano.value;
					url = url + '&iniciativaId=' + document.reporteForm.id.value;
					url = url + '&anoInicial=' + document.reporteForm.anoInicial.value;
					url = url + '&mesIni=' + document.reporteForm.mesInicial.value;
					url = url + '&mesFin=' + document.reporteForm.mesFinal.value;
					
					
					
													
					if(document.reporteForm.todosAno.checked==true){
						url = url + '&todos=' + true;
					}else{
						url = url + '&todos=' + false;
					}
					
					if(document.reporteForm.avanceTareas.checked==true){
						url = url + '&avance=' + true;
					}else{
						url = url + '&avance=' + false;
					}
					
					
					abrirReporte('<html:rewrite action="/reportes/iniciativas/indicadorEjecucion"/>?'+url);
					
					
			 	 }
				cancelar();
				
	    	 	
			}
			
			
	

		</script>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/iniciativas/indicador">

			<html:hidden property="nombrePlan" />
			<html:hidden property="id" />
			<html:hidden property="source" />
			<html:hidden property="objetoSeleccionadoId" />
			
			
			<input type='hidden' name='filtroNombre' value='<bean:write name="reporteForm" property="filtro.nombre" />'>
			<input type='hidden' name='selectHitoricoType' value='<bean:write name="reporteForm" property="filtro.historico" />'>
			
			<vgcinterfaz:contenedorForma width="560px" height="520px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>					
					..:: <vgcutil:message key="jsp.gestionariniciativas.menu.reportes.proyectos.indicadores" arg0="<%= tituloIniciativas %>"/>
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="420px" width="500px" nombre="reporteCriterios">

					<%-- Panel: Parametros --%>
					
					
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">
						
						<%-- Título del Panel: Datos Básicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor panelContenedorTabla">
							<!-- Organizacion Seleccionada-->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.organizacion" /> : </td>
								<td colspan="1"><b><bean:write name="reporteForm" property="nombreOrganizacion"/></b></td>
								<td></td>
							</tr>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
									<bean:define id="alcanceObjetivo" toScope="page">
										<bean:write name="reporteForm" property="alcanceObjetivo" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcanceObjetivo %>">
										<vgcutil:message key="jsp.protegerliberar.iniciativa" />
									</html:radio>
								</td>
							</tr>
													
							<tr>
								<td colspan="3">
									<bean:define id="alcanceOrganizacion" toScope="page">
										<bean:write name="reporteForm" property="alcanceOrganizacion" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcanceOrganizacion %>">
										<vgcutil:message key="jsp.protegerliberar.iniciativa.todas" />
									</html:radio>
								</td>
							</tr>
																					
							
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
										&nbsp;
										<vgcutil:message key="jsp.editariniciativa.ficha.anioformulacion" />
										&nbsp;&nbsp;
										<bean:define id="anoCalculo" toScope="page">
											<bean:write name="reporteForm" property="ano" />
										</bean:define>
										<html:select property="ano" value="<%= anoCalculo %>" styleClass="cuadroTexto">
											<%
											for (int i = 1900; i <= 2050; i++) 
											{
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
								<%-- Visible --%>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.editariniciativa.ficha.todos.ano" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									<html:checkbox styleClass="botonSeleccionMultiple" property="todosAno" />
								</td>
								
							</tr>												
																					
							<tr>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.editariniciativa.ficha.tipoproyecto" />
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="cuadroCombinado" name="reporteForm" property='tipo' id="tipo" >																	
										<logic:iterate name="reporteForm" property="tipos" id="tip">
											<bean:define id="tipoProyectoId" toScope="page"><bean:write name='tip' property='tipoProyectoId' /></bean:define>
											<bean:define id="nombre" toScope="page"><bean:write name='tip' property='nombre' /></bean:define>											
											<option value="<%=tipoProyectoId%>" ><%=nombre%></option>											
										</logic:iterate>
										<option value="0" selected>Todos</option>		
									</select>
								
								</td>
							</tr>
							<tr>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.visualizariniciativasplan.columna.estatus" />
									&nbsp;
									&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="cuadroCombinado" name="estatus" id="estatus" property="estatus" width="5px">
											<option value="0" selected><vgcutil:message key="estado.sin.iniciar" /></option>
											<option value="1" selected><vgcutil:message key="estado.sin.iniciar.desfasada" /></option>
											<option value="2" selected><vgcutil:message key="estado.en.ejecucion.sin.retrasos" /></option>
											<option value="3" selected><vgcutil:message key="estado.en.ejecucion.con.retrasos.superables" /></option>
											<option value="4" selected><vgcutil:message key="estado.en.ejecucion.con.retrasos.significativos" /></option>
											<option value="5" selected><vgcutil:message key="estado.concluidas" /></option>
											<option value="6" selected>Cancelado</option>
											<option value="7" selected>Suspendido</option>
											<option value="8" selected>Todos</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<td colspan="3">
										&nbsp;
										<vgcutil:message key="jsp.reportes.plan.meta.titulo.rango" />
										&nbsp;&nbsp;
										<bean:define id="anoCalculo1" toScope="page">
											<bean:write name="reporteForm" property="anoInicial" />
										</bean:define>
										<html:select property="anoInicial" value="<%= anoCalculo1 %>" styleClass="cuadroTexto">
											<%
											for (int i = 1900; i <= 2050; i++) 
											{
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
									<vgcutil:message key="jsp.mostrarvista.desde" />
									&nbsp;
									&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="cuadroCombinado" name="mesInicial" id="mesInicial" property="mesInicial" width="5px">
											<option value="1" selected>1</option>
											<option value="2" >2</option>
											<option value="3" >3</option>
											<option value="4" >4</option>
											<option value="5" >5</option>
											<option value="6" >6</option>
											<option value="7" >7</option>
											<option value="8" >8</option>
											<option value="9" >9</option>
											<option value="10" >10</option>
											<option value="11" >11</option>
											<option value="12" >12</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.mostrarvista.hasta" />
									&nbsp;
									&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<select class="cuadroCombinado" name="mesFinal" id="mesFinal" property="mesFinal" width="5px">
											<option value="1" >1</option>
											<option value="2" >2</option>
											<option value="3" >3</option>
											<option value="4" >4</option>
											<option value="5" >5</option>
											<option value="6" >6</option>
											<option value="7" >7</option>
											<option value="8" >8</option>
											<option value="9" >9</option>
											<option value="10" >10</option>
											<option value="11" >11</option>
											<option value="12" selected>12</option>
									</select>
								</td>
							</tr>
							
							
							<tr>
								<td colspan="3">
									&nbsp;
									<vgcutil:message key="jsp.reporte.iniciativas.indicador.avance" />
									&nbsp;&nbsp;&nbsp;&nbsp;
									<html:checkbox styleClass="botonSeleccionMultiple" property="avanceTareas" />
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


					
				</vgcinterfaz:contenedorPaneles>
				
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
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		
	</tiles:put>
</tiles:insert>