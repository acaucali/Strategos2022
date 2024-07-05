<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Creado por: Andres Martinez (13/06/2024) --%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">
	<%-- T�tulo --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message
			key="menu.evaluacion.reportes.dependencias.omisivas" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script type="text/javascript">
			function cancelar() {
				window.close();
			}

			function generarReporte() {
				var url = 'alcance=' + document.reporteForm.alcance.value;
				url = url + '&anio=' + document.reporteForm.ano.value;

				abrirReporte('<html:rewrite action="/reportes/reporteDependenciasOmisivasXls"/>?'
						+ url
						+ '&organizacionId=<bean:write name="organizacionId" scope="session" />');

				cancelar();
			}
		</script>
		
		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/reportes/reporteDependenciasOmisivas">
			<vgcinterfaz:contenedorForma width="460px" height="270px"
				bodyAlign="center" bodyValign="middle" bodyCellpadding="20">
				
				<%-- T�tulo--%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
					<vgcutil:message
						key="menu.evaluacion.reportes.dependencias.omisivas" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Paneles --%>
				<vgcinterfaz:contenedorPaneles height="150px" width="400px" nombre="reporteCriterios">
					<%-- Panel: Selector --%>
					<vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">	
						<%-- T�tulo del Panel: Datos B�sicos --%>
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
						</vgcinterfaz:panelContenedorTitulo>
						
						<table class="panelContenedor panelContenedorTabla">
							<tr>
								<td colspan="3">
									<bean:define id="alcanceSubOrganizacion" toScope="page">
										<bean:write name="reporteForm" property="alcanceSubOrganizacion" />
									</bean:define>
									<html:radio property="alcance" value="4">
										<vgcutil:message key="jsp.protegerliberar.pororganizacion" />
									</html:radio>
								</td>
							</tr>
							
							<tr>
								<td colspan="3">
									<bean:define id="alcanceOrganizacion" toScope="page">
										<bean:write name="reporteForm" property="alcanceOrganizacion" />
									</bean:define>
									<html:radio property="alcance" value="<%= alcanceOrganizacion %>">
										<vgcutil:message key="jsp.protegerliberar.pororganizaciontodas" />
									</html:radio>
								</td>
							</tr>
							
							<tr>
								<td colspan="3">
									&nbsp;
								</td>
							</tr>			
							
							<tr>
								<td colspan="3">&nbsp; A�o
									&nbsp;&nbsp; <bean:define id="anoCalculo" toScope="page">
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