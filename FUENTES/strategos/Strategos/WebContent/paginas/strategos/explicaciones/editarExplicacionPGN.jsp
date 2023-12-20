<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Andres Martinez (14/11/2023) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">				
		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarExplicacionPGNForm" property="explicacionId" value="0">
			<vgcutil:message key="jsp.editarexplicacion.titulo.nuevo" />
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarExplicacionPGNForm" property="explicacionId" value="0">
			<vgcutil:message key="jsp.editarexplicacion.titulo.modificar" />
		</logic:notEqual>		
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
		
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			function cancelar() 
			{			
				window.document.editarExplicacionPGNForm.action = '<html:rewrite action="/explicaciones/cancelarGuardarExplicacionPGN"/>';
				window.document.editarExplicacionPGNForm.submit();
			}
			
			function ejecutarPorDefecto(e) {
				if(e.keyCode==13) {
					guardar();
				}
			}
			
			function guardar() {				
				if (validar(document.editarExplicacionPGNForm)) {
					window.document.editarExplicacionPGNForm.submit();					
				}	
			}
			
			function validar(forma) {
				if(document.editarExplicacionPGNForm.fecha.value == null  || document.editarExplicacionPGNForm.fecha.value == ""){
					alert("Debe ingresar fecha");
				}else{
				if (validateEditarExplicacionPGNForm(forma)) {
					window.document.editarExplicacionPGNForm.action = '<html:rewrite action="/explicaciones/guardarExplicacionPGN"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
					return true;
				} else 
					return false;
				}
			}
			
			function seleccionarFecha(nombre) 
			{				
				var field = eval(nombre);
				mostrarCalendario(nombre, field.value, '<vgcutil:message key="formato.fecha.corta" />');				
			}	
			
			function limpiarFecha(nombre) 
			{
				var field = eval(nombre);
				field.value = "";
			}
		</script>		
		
		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarExplicacionPGNForm.titulo" />
		
		
		<html:form action="/explicaciones/guardarExplicacionPGN" focus="titulo" enctype="multipart/form-data" method="POST">
		
			<html:hidden property="explicacionId" />
			<bean:define id="altoForma" value="560px" />
		
			<vgcinterfaz:contenedorForma width="780px" height="<%=altoForma%>">
				<vgcinterfaz:contenedorFormaTitulo>..::
				<%-- Para el caso de Nuevo --%>
				<logic:equal name="editarExplicacionPGNForm" property="explicacionId" value="0">
					<vgcutil:message key="jsp.editarexplicacion.titulo.nuevo" />
				</logic:equal>
		
				<%-- Para el caso de Modificar --%>
				<logic:notEqual name="editarExplicacionPGNForm" property="explicacionId" value="0">
					<vgcutil:message key="jsp.editarexplicacion.titulo.modificar" />
				</logic:notEqual>	
				</vgcinterfaz:contenedorFormaTitulo>
				
				
				<%-- Ficha de datos --%>
				<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="left" height="100%">
					<tr>
						<td align="left"><b><vgcutil:message key="jsp.editarexplicacion.ficha.organizacion" /></b></td>
						<td><bean:write name="editarExplicacionPGNForm" property="nombreOrganizacion" /></td>
					</tr>
					<tr>
						<td align="left"><b><vgcutil:message key="jsp.editarexplicacion.ficha.objeto" /></b></td>
						<td><bean:write name="editarExplicacionPGNForm" property="nombreObjetoKey" /> (<bean:write name="editarExplicacionPGNForm" property="nombreObjetoKey" />)</td>
					</tr>
					<tr>
						<td align="left"><vgcutil:message key="jsp.editarexplicacion.ficha.autor" /></td>
						<td><bean:write name="editarExplicacionPGNForm" property="nombreUsuarioCreado" /> (<bean:write name="editarExplicacionPGNForm" property="creado" />)</td>
					</tr>
					<tr>
						<td align="left"><vgcutil:message key="jsp.editarexplicacion.ficha.titulo" /></td>
						<td><html:text property="titulo" onkeypress="ejecutarPorDefecto(event)" size="58" maxlength="250" styleClass="cuadroTexto" /></td>
					</tr>
					
					<tr>
						<td align="left"><vgcutil:message key="jsp.editarexplicacion.ficha.fecha" /></td>
						<td>
							
							<html:text  property="fecha" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />
							<logic:notEqual name="editarExplicacionPGNForm" property="bloqueado" value="true">
								<img style="cursor: pointer" onclick="seleccionarFecha('document.editarExplicacionPGNForm.fecha');" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">
								<img style="cursor: pointer" onclick="limpiarFecha('document.editarExplicacionPGNForm.fecha');" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</logic:notEqual>							
						</td>
					</tr>		
					<tr>
						<td>
							&nbsp;						
						</td>
					</tr>								
				</table>
				
				<table class="panelContenedor" cellpadding="1" cellspacing="10" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="left"><vgcutil:message key="jsp.editarexplicacion.ficha.cumpliendo.fechas" /></td>						
						
					</tr>
					<tr>
						<td >
							
							<html:radio name="editarExplicacionPGNForm" property="cumpliendoFechas" value="true" >
								<vgcutil:message key="jsp.configuracion.sistema.iniciativas.administracion.publica.ficha.si" />
							</html:radio>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio name="editarExplicacionPGNForm" property="cumpliendoFechas" value="false">
								<vgcutil:message key="jsp.configuracion.sistema.iniciativas.administracion.publica.ficha.no" />
							</html:radio>
							
						</td>
						
					</tr>
					
				</table>
				<table class="panelContenedor" cellpadding="1" cellspacing="10" border="0">
					<tr>
						<td align="left" colspan="2" valign="top"><vgcutil:message key="jsp.editarexplicacion.ficha.cumpliendo.fechas.explicacion" /></td>	
					</tr>
					<tr>						
						<td><html:textarea property="explicacionFechas" onkeypress="ejecutarPorDefecto(event)" cols="100" rows="4" styleClass="cuadroTexto" /></td>
					</tr>
				</table>
				
				<%-- Ficha de datos --%>
				<table class="panelContenedor" cellpadding="1" cellspacing="10" border="0">

					<%-- Campos de la Ficha de Datos --%>
					<tr>
						<td align="left"  ><vgcutil:message key="jsp.editarexplicacion.ficha.recibido" /></td>												
					</tr>
					<tr>
						<td >
							
							
							<html:radio name="editarExplicacionPGNForm" property="recibido" value="true" >
								<vgcutil:message key="jsp.configuracion.sistema.iniciativas.administracion.publica.ficha.si" />
							</html:radio>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<html:radio name="editarExplicacionPGNForm" property="recibido" value="false">
								<vgcutil:message key="jsp.configuracion.sistema.iniciativas.administracion.publica.ficha.no" />
							</html:radio>
							
						</td>
						
					</tr>
					
				</table>
				<table class="panelContenedor" cellpadding="1" cellspacing="10" border="0">
					<tr>
						<td align="left" colspan="2" valign="top"><vgcutil:message key="jsp.editarexplicacion.ficha.recibido.explicacion" /></td>	
					</tr>
					<tr>						
						<td><html:textarea property="explicacionRecibido" onkeypress="ejecutarPorDefecto(event)" cols="100" rows="4" styleClass="cuadroTexto" /></td>
					</tr>
				</table>
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
			
			</vgcinterfaz:contenedorForma>
		</html:form>
	<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarExplicacionPGNForm" dynamicJavascript="true" staticJavascript="false" />
		<script language="Javascript1.1" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
	</tiles:put>

</tiles:insert>