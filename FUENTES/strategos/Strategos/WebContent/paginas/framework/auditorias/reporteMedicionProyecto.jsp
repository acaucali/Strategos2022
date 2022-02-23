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

		<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="reporte.framework.auditorias.proyecto.detallado.titulo" />
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
			 				
	    	 	
			}
			
			function seleccionarOrganizaciones() 
		    {
				abrirSelectorOrganizaciones('reporteAuditoriaForm', 'nombreOrganizacion', 'organizacionId', null);
			}
	

		</script>
		<jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/framework/auditorias/reporteMedicionProyecto">

			<html:hidden property="source" />
			<html:hidden property="organizacionId" />
			<html:hidden property="nombreOrganizacion" />			
			
		
			
			<vgcinterfaz:contenedorForma width="460px" height="460px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- Título--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message key="reporte.framework.auditorias.proyecto.detallado.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="340px" width="400px" nombre="reporteAuditoriaMediciones">

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
								<td align="right"><b><vgcutil:message key="jsp.editariniciativa.ficha.organizacion" /></b></td>
								<td align="right"><input type="button" style="width:40%" class="cuadroTexto" value="<vgcutil:message key="jsp.seleccionarindicador.seleccionarorganizacion" />" onclick="seleccionarOrganizaciones();"></td>
							</tr>
							
							<tr>
								<td colspan="3"><hr width="100%"></td>
							</tr>
							
							<tr>
								<!-- Encabezado selector de fechas -->
							
								<td align="center" colspan="1"></td>
								<td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.mes" /> </b> </td>
								<td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.ano" /> </b> </td>
							
							</tr>
							
							<!-- Fecha Inicio -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.desde" /> : </td>
								<td>
									<html:select property="mesInicial" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoMeses" value="clave" label="valor" />
									</html:select>
								</td>
								<td>
									<html:select property="anoInicial" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
								
								</td>
							</tr>
							<!-- Fecha Final -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.hasta" /> : </td>
								<td >
									<html:select property="mesFinal" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoMeses" value="clave" label="valor" />
									</html:select>
								</td>
								<td>
									<html:select property="anoFinal" styleClass="cuadroTexto">
										<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
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