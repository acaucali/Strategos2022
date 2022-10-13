<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (27/05/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<bean:define id="tituloIniciativa">
		<bean:write scope="session" name="activarIniciativa" property="nombrePlural" />
	</bean:define>

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarIniciativaForm" property="iniciativaId" value="0">
			<logic:empty name="editarIniciativaForm" property="nombreIniciativaSingular">
				<vgcutil:message key="jsp.editariniciativa.titulo.nuevo" arg0="<%= tituloIniciativa %>"/>
			</logic:empty>
			<logic:notEmpty name="editarIniciativaForm" property="nombreIniciativaSingular">
				<bean:write name="editarIniciativaForm" property="nombreIniciativaSingular" />
			</logic:notEmpty>
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarIniciativaForm" property="iniciativaId" value="0">
			<logic:empty name="editarIniciativaForm" property="nombreIniciativaSingular">
				<vgcutil:message key="jsp.editariniciativa.titulo.modificar" arg0="<%= tituloIniciativa %>"/>
			</logic:empty>
			<logic:notEmpty name="editarIniciativaForm" property="nombreIniciativaSingular">
				<vgcutil:message key="menu.edicion.modificar" />&nbsp;<bean:write name="editarIniciativaForm" property="nombreIniciativaSingular" />
			</logic:notEmpty>
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funci�n JavaScript externa --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>

		<%-- Funci�n JavaScript externa --%>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>

		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/iniciativas/iniciativaJs/iniciativaJs.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>
		

		<bean:define id="bloquearForma" toScope="page">
			<logic:notEmpty name="editarIniciativaForm" property="bloqueado">
				<bean:write name="editarIniciativaForm" property="bloqueado" />
			</logic:notEmpty>
			<logic:empty name="editarIniciativaForm" property="bloqueado">
				false
			</logic:empty>
		</bean:define>

		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script type="text/javascript">

			// Inicializar botones de los cuadros Num�ricos
			inicializarBotonesCuadro('<html:rewrite page="/componentes/cuadroNumerico/updowncontrol.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/downcontrolactivo.gif"/>', '<html:rewrite  page="/componentes/cuadroNumerico/upcontrolactivo.gif"/>');
			var _frecuencia = null;
			var _tipoMedicion = null;
			var fecha = new Date();
			var ano = fecha.getFullYear();
			
			function obtenerAno(){
				document.editarIniciativaForm.anioFormulacion.value=ano;
			}
			
			function validar(forma) 
			{
				if (validateEditarIniciativaForm(forma)) 
				{
					if (document.editarIniciativaForm.iniciativaId.value != null && document.editarIniciativaForm.iniciativaId.value != 0 && _frecuencia != null && _frecuencia != document.editarIniciativaForm.frecuencia.value)
					{
						if (!confirm('<vgcutil:message key="jsp.editariniciativa.eliminarmediciones.confirmar" />'))
							return false;
						else
							document.editarIniciativaForm.eliminarMediciones.value = true;
					}
					
					if (document.editarIniciativaForm.eliminarMediciones.value == "false" && document.editarIniciativaForm.iniciativaId.value != null && document.editarIniciativaForm.iniciativaId.value != 0)
					{
						if (_tipoMedicion == 0 && document.editarIniciativaForm.tipoMedicion[1].checked)
						{
							if (!confirm('<vgcutil:message key="jsp.editariniciativa.eliminarmediciones.confirmar" />'))
								return false;
							else
								document.editarIniciativaForm.eliminarMediciones.value = true;
						}
						else if (_tipoMedicion == 1 && document.editarIniciativaForm.tipoMedicion[0].checked)
						{
							if (!confirm('<vgcutil:message key="jsp.editariniciativa.eliminarmediciones.confirmar" />'))
								return false;
							else
								document.editarIniciativaForm.eliminarMediciones.value = true;
						}
					}

					var url = '?ts=<%= (new java.util.Date()).getTime() %>';
					var selectEstatusType = document.getElementById('selectEstatusType');
					if (selectEstatusType != null)
						url = url + '&selectEstatusType=' + selectEstatusType.value;
					
					var selectTipoProyecto = document.getElementById('selectTipoProyecto');
					if (selectTipoProyecto != null)
						url = url + '&selectTipoProyecto=' + selectTipoProyecto.value;
					
					var desdeInstrumento = document.editarIniciativaForm.desdeInstrumento.value;
					if(desdeInstrumento != null && desdeInstrumento){
						url = url + '&desdeInstrumento=' + desdeInstrumento;
						url = url + '&instrumentoId=' + document.editarIniciativaForm.instrumentoId.value;
					}
					
					window.document.editarIniciativaForm.action = '<html:rewrite action="/iniciativas/guardarIniciativa"/>' + url;
					return true;
				} 
				else 
					return false;
			}
			
			let identificadorTiempoDeEspera;
			
			function cerrarModal(){				
				this.close();
				window.location.reload();
			}
										
			function guardar() 
			{
				<vgcinterfaz:mostrarPanelContenedorJs nombrePanel="datosBasicos" nombreContenedor="editarIniciativa"></vgcinterfaz:mostrarPanelContenedorJs>
				if (validar(document.editarIniciativaForm)) 
				{
					if (document.editarIniciativaForm.alertaZonaAmarilla.value != "")
						document.editarIniciativaForm.hayValorPorcentajeAmarillo.value = true;
					if (document.editarIniciativaForm.alertaZonaVerde.value != "")
						document.editarIniciativaForm.hayValorPorcentajeVerde.value = true;
					
					armarResultadosEspecificos();
					activarBloqueoEspera();
					window.document.editarIniciativaForm.submit();
								
																				
				}				
			}
			
			function cancelar() 
			{	
				var url = '';
				var desdeInstrumento = document.editarIniciativaForm.desdeInstrumento.value;
				if(desdeInstrumento != null && desdeInstrumento){
					url = '?desdeInstrumento=' + desdeInstrumento;
				}
				window.document.editarIniciativaForm.action = '<html:rewrite action="/iniciativas/cancelarGuardarIniciativa"/>' + url;
				window.document.editarIniciativaForm.submit();
			}
			
			
			function ejecutarPorDefecto(e) 
			{
				if (e.keyCode==13) 
					guardar();
			}
			
			function seleccionarResponsableFijarMeta() 
			{
				abrirSelectorResponsables('editarIniciativaForm', 'responsableFijarMeta', 'responsableFijarMetaId', document.editarIniciativaForm.responsableFijarMetaId.value, '<bean:write name="organizacionId" scope="session" />');
			}
			
			function seleccionarResponsableLograrMeta() 
			{
				abrirSelectorResponsables('editarIniciativaForm', 'responsableLograrMeta', 'responsableLograrMetaId', document.editarIniciativaForm.responsableLograrMetaId.value, '<bean:write name="organizacionId" scope="session" />');
			}
			
			function seleccionarResponsableSeguimiento() 
			{
				abrirSelectorResponsables('editarIniciativaForm', 'responsableSeguimiento', 'responsableSeguimientoId', document.editarIniciativaForm.responsableSeguimientoId.value, '<bean:write name="organizacionId" scope="session" />');
			}
			
			function seleccionarResponsableCargarMeta() 
			{
				abrirSelectorResponsables('editarIniciativaForm', 'responsableCargarMeta', 'responsableCargarMetaId', document.editarIniciativaForm.responsableCargarMetaId.value, '<bean:write name="organizacionId" scope="session" />');
			}
			
			function seleccionarResponsableCargarEjecutado() 
			{
				abrirSelectorResponsables('editarIniciativaForm', 'responsableCargarEjecutado', 'responsableCargarEjecutadoId', document.editarIniciativaForm.responsableCargarEjecutadoId.value, '<bean:write name="organizacionId" scope="session" />');
			}
			
			function limpiarResponsableFijarMeta() 
			{
				document.editarIniciativaForm.responsableFijarMetaId.value = "";
				document.editarIniciativaForm.responsableFijarMeta.value = "";
			}
			
			function limpiarResponsableLograrMeta() 
			{
				document.editarIniciativaForm.responsableLograrMetaId.value = "";
				document.editarIniciativaForm.responsableLograrMeta.value = "";
			}
			
			function limpiarResponsableSeguimiento() 
			{
				document.editarIniciativaForm.responsableSeguimientoId.value = "";				
				document.editarIniciativaForm.responsableSeguimiento.value = "";
			}
			
			function limpiarResponsableCargarMeta() 
			{
				document.editarIniciativaForm.responsableCargarMetaId.value = "";
				document.editarIniciativaForm.responsableCargarMeta.value = "";
			}
			
			function limpiarResponsableCargarEjecutado() 
			{
				document.editarIniciativaForm.responsableCargarEjecutadoId.value = "";
				document.editarIniciativaForm.responsableCargarEjecutado.value = "";
			}
			
			var resultadosEspecificos;
			var SEPARADOR = "|+|";
			
			function obtenerPosicionAno(ano) 
			{
				var anoInicial = document.editarIniciativaForm.ano.options[0].value;
				return (ano - anoInicial);
			}
			
			function mostrarResultadoEspecifico() 
			{
				var posicion = obtenerPosicionAno(document.editarIniciativaForm.ano.options[document.editarIniciativaForm.ano.selectedIndex].value);
				document.editarIniciativaForm.resultadoEspecificoIniciativa.value = resultadosEspecificos[posicion];
			}
			
			function guardarResultadoEspecifico() 
			{
				var posicion = obtenerPosicionAno(document.editarIniciativaForm.ano.options[document.editarIniciativaForm.ano.selectedIndex].value);
				resultadosEspecificos[posicion] = document.editarIniciativaForm.resultadoEspecificoIniciativa.value;
			}
			
			function armarResultadosEspecificos() 
			{
				document.editarIniciativaForm.resultadoEspecificoIniciativa.value = '';
				for (var i = 0 ; i < resultadosEspecificos.length ; i++) 
				{
					if (i == 0) 
						document.editarIniciativaForm.resultadoEspecificoIniciativa.value = resultadosEspecificos[i];
					else 
						document.editarIniciativaForm.resultadoEspecificoIniciativa.value = document.editarIniciativaForm.resultadoEspecificoIniciativa.value + SEPARADOR + resultadosEspecificos[i];
				}
			}
			
			function inicializarResultadosEspecificos() 
			{
				resultadosEspecificos = document.editarIniciativaForm.resultadoEspecificoIniciativa.value.split(SEPARADOR);
				mostrarResultadoEspecifico();
			}
			
			function seleccionarOrganizaciones() 
		    {
				abrirSelectorOrganizaciones('editarIniciativaForm', 'organizacionNombre', 'organizacionId', null);
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarIniciativaForm.nombre" />

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/guardarIniciativa" focus="nombre">

			<html:hidden property="iniciativaId" />
			<html:hidden property="planId" />
			<html:hidden property="perspectivaId" />
			<html:hidden property="responsableFijarMetaId" />
			<html:hidden property="responsableLograrMetaId" />
			<html:hidden property="responsableSeguimientoId" />
			<html:hidden property="responsableCargarMetaId" />
			<html:hidden property="responsableCargarEjecutadoId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="organizacionNombre" />
			<html:hidden property="hayValorPorcentajeAmarillo" />
			<html:hidden property="hayValorPorcentajeVerde" />
			<html:hidden property="eliminarMediciones" />
			<html:hidden property="estatusId" />
			<html:hidden property="desdeInstrumento" />
			<html:hidden property="instrumentoId" />
			
			
			

			<vgcinterfaz:contenedorForma width="1040px" height="780px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarIniciativaForm" property="iniciativaId" value="0">
						<logic:empty name="editarIniciativaForm" property="nombreIniciativaSingular">
							<vgcutil:message key="jsp.editariniciativa.titulo.nuevo" arg0="<%= tituloIniciativa %>"/>
						</logic:empty>
						<logic:notEmpty name="editarIniciativaForm" property="nombreIniciativaSingular">
							<bean:write name="editarIniciativaForm" property="nombreIniciativaSingular" />
						</logic:notEmpty>
						
					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarIniciativaForm" property="iniciativaId" value="0">
						<logic:empty name="editarIniciativaForm" property="nombreIniciativaSingular">
							<vgcutil:message key="jsp.editariniciativa.titulo.modificar" arg0="<%= tituloIniciativa %>"/>
						</logic:empty>
						<logic:notEmpty name="editarIniciativaForm" property="nombreIniciativaSingular">
							<vgcutil:message key="menu.edicion.modificar" />&nbsp;<bean:write name="editarIniciativaForm" property="nombreIniciativaSingular" />
						</logic:notEmpty>
					</logic:notEqual>

				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="650px" width="950px" nombre="editarIniciativa">

					<!-- Panel: Datos B�sicos -->
					<vgcinterfaz:panelContenedor anchoPestana="105" nombre="datosBasicos">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.datos.basicos" />
						</vgcinterfaz:panelContenedorTitulo>						
						<table class="bordeFichaDatos" cellpadding="4" cellspacing="10" border="0" >
							<%-- Organizacion --%>
							
							<logic:notEqual name="editarIniciativaForm" property="desdeInstrumento" value="true">
								<tr>
									<td align="right"><b><vgcutil:message key="jsp.editariniciativa.ficha.organizacion" /></b></td>
									<td colspan="3"><bean:write name="editarIniciativaForm" property="organizacionNombre" /></td>
								</tr>
							</logic:notEqual>
							
							<logic:equal name="editarIniciativaForm" property="desdeInstrumento" value="true">
								<tr>
									<td align="right"><b><vgcutil:message key="jsp.editariniciativa.ficha.organizacion" /></b></td>
									<logic:equal name="editarIniciativaForm" property="iniciativaId" value="0">
										<td align="right"><input type="button" style="width:40%" class="cuadroTexto" value="<vgcutil:message key="jsp.seleccionarindicador.seleccionarorganizacion" />" onclick="seleccionarOrganizaciones();"></td>
									</logic:equal>
									<logic:notEqual name="editarIniciativaForm" property="iniciativaId" value="0">										
										<td colspan="3"><bean:write name="editarIniciativaForm" property="organizacionNombre" /></td>
									</logic:notEqual>									
								</tr>								
							</logic:equal>						
							
							<!-- Campo Nombre -->
							<tr>
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.nombre" /></td>
								<td colspan="3"><html:text property="nombre" onkeypress="ejecutarPorDefecto(event)" size="89" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="250" styleClass="cuadroTexto" onkeyup="sincronizarCamposTexto(document.editarIniciativaForm.nombre, document.editarIniciativaForm.nombreLargo, 250, document.editarIniciativaForm.sincronizarNombres.checked)" /><input type="checkbox" name="sincronizarNombres" title="SincronizarNombres"></td>
							</tr>

							<!-- Campo Nombre Largo -->
							<tr>
								<td align=""left""><vgcutil:message key="jsp.editariniciativa.ficha.nombrelargo" /></td>
								<td colspan="3"><html:text property="nombreLargo" size="89" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="300" styleClass="cuadroTexto" onkeyup="sincronizarCamposTexto(document.editarIniciativaForm.nombreLargo, document.editarIniciativaForm.nombre, 300, document.editarIniciativaForm.sincronizarNombres.checked)" /></td>
							</tr>

							<%-- Campo descripci�n --%>
							<tr>
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.descripcion" /></td>
								<td colspan="3"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="descripcion" onkeypress="ejecutarPorDefecto(event)" cols="88" rows="5" styleClass="cuadroTexto" /></td>

							</tr>
							
							<!-- Campos tipo proyecto - a�os formulacion -->
							<tr>
								<!-- Tipo proyecto -->
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.tipoproyecto" /></td>
								<td>
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										<select class="cuadroCombinado" name="selectTipoProyecto" id="selectTipoProyecto" >
											<logic:iterate name="editarIniciativaForm" property="tipos" id="tip">
												<bean:define id="tipoProyectoId" toScope="page"><bean:write name='tip' property='tipoProyectoId' /></bean:define>
												<bean:define id="nombre" toScope="page"><bean:write name='tip' property='nombre' /></bean:define>
												<logic:equal name='editarIniciativaForm' property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm' property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:notEqual>
									<logic:equal name="editarIniciativaForm" property="bloqueado" value="true">
										<select class="cuadroCombinado" name="selectTipoProyecto" id="selectTipoProyecto" disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
											<logic:iterate name="editarIniciativaForm" property="tipos" id="tip">
												<bean:define id="tipoProyectoId" toScope="page"><bean:write name='tip' property='tipoProyectoId' /></bean:define>
												<bean:define id="nombre" toScope="page"><bean:write name='tip' property='nombre' /></bean:define>
												<logic:equal name='editarIniciativaForm' property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm' property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:equal>
								</td>
								
								<!-- A�o formulaci�n -->	
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.anioformulacion" /></td>
								<td colspan="1" >
									<html:text property="anioFormulacion" size="5" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="4" styleClass="cuadroTexto" />

								</td>
								
							</tr>
							
							<!-- Campos frecuencia - estatus-->
							<tr>
								
								<!-- Frecuencia -->
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.frecuencia" /></td>
								<td><html:select property="frecuencia" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" styleClass="cuadroTexto" size="1">
									<html:option value=""></html:option>
									<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
								</html:select></td>
														
								
								<!-- Estatus -->
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.estatus" /></td>
								<td>
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										<select class="cuadroCombinado" name="selectEstatusType" id="selectEstatusType" >
											<logic:iterate name="editarIniciativaForm" property="estatuses" id="est">
												<bean:define id="id" toScope="page"><bean:write name='est' property='id' /></bean:define>
												<bean:define id="nombre" toScope="page"><bean:write name='est' property='nombre' /></bean:define>
												<logic:equal name='editarIniciativaForm' property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm' property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:notEqual>
									<logic:equal name="editarIniciativaForm" property="bloqueado" value="true">
										<select class="cuadroCombinado" name="selectEstatusType" id="selectEstatusType" disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
											<logic:iterate name="editarIniciativaForm" property="estatuses" id="est">
												<bean:define id="id" toScope="page"><bean:write name='est' property='id' /></bean:define>
												<bean:define id="nombre" toScope="page"><bean:write name='est' property='nombre' /></bean:define>
												<logic:equal name='editarIniciativaForm' property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm' property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:equal>
								</td>
								
							</tr>				
							
							<%-- Campo Tipo de Medici�n --%>
							<tr align="left">							 
    							<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.titulo.mediciones" /></td>
    							<td>
    								<html:radio property="tipoMedicion" value="0" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/><vgcutil:message key="jsp.editariniciativa.ficha.tipomedicion.enperiodo" />
    								&nbsp;&nbsp;
    								<html:radio property="tipoMedicion" value="1" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"/><vgcutil:message key="jsp.editariniciativa.ficha.tipomedicion.alperiodo" />
    							</td>																								   															
							</tr>
							
							<%-- Campo Responsable proy - cargo --%>
							<tr>
								<!-- Responsable Proyecto -->								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.reponsableproyecto" /></td>
								
								<td colspan="1"><html:text property="responsableProyecto" onkeypress="ejecutarPorDefecto(event)" size="33" maxlength="150" styleClass="cuadroTexto" />
								
								<!-- Cargo -->
								<td align="left"><vgcutil:message key="jsp.editariniciativa.ficha.cargo" /></td>
								<td><html:text property="cargoResponsable" onkeypress="ejecutarPorDefecto(event)" size="31" maxlength="150" styleClass="cuadroTexto" />			
							</tr>						
																												
							<%-- Campo Organizaciones involucradas --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.organizacionesinvolucradas" /></td>
								<td colspan="3"><html:textarea  property="organizacionesInvolucradas" onkeypress="ejecutarPorDefecto(event)" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>		
											
							<%-- Campo Objetivo Estrategico --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.objetivoestrategico" /></td>
								<td colspan="3"><html:textarea  property="objetivoEstrategico" onkeypress="ejecutarPorDefecto(event)" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Fuente financiacion - monto --%>
							<tr>
								<!-- Fuente FInanciacion -->
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.fuentefinanciacion" /></td>
								<td colspan="1"><html:text property="fuenteFinanciacion" onkeypress="ejecutarPorDefecto(event)" size="33" maxlength="150" styleClass="cuadroTexto" />
								
								<!-- Monto Financiamiento -->
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.monto" /></td>
								<td colspan="1"><html:text property="montoFinanciamiento" onkeypress="" size="31" maxlength="150" styleClass="cuadroTexto" />			
							</tr>	
							<!-- Campo enteEjecutor
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.enteejecutor" /></td>
								<td colspan="3"><html:text property="enteEjecutor" size="63" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>  -->
																																								
						</table>

					</vgcinterfaz:panelContenedor>
					
					<!-- Panel: Datos B�sicos II -->
					<vgcinterfaz:panelContenedor anchoPestana="105" nombre="datosBasicosII">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.datos.basicos.dos" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="bordeFichaDatos" cellpadding="5" cellspacing="4" border="0">											
																												
							<%-- Campo Iniciativa Estrategica--%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.iniciativaestrategica" /></td>
								<td colspan="3"><html:textarea  property="iniciativaEstrategica" onkeypress="" cols="88" rows="1" styleClass="cuadroTexto" /></td>																		
							</tr>		
											
							<%-- Campo Lider Iniciativa --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.lideriniciativa" /></td>
								<td colspan="3"><html:textarea  property="liderIniciativa" onkeypress="" cols="88" rows="1" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Tipo Iniciativa --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.tipoiniciativa" /></td>
								<td colspan="3"><html:textarea  property="tipoIniciativa" onkeypress="" cols="88" rows="1" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Poblacion Beneficiada --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.poblacionbeneficiada" /></td>
								<td colspan="3"><html:textarea  property="poblacionBeneficiada" onkeypress="" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Contexto --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.contexto" /></td>
								<td colspan="3"><html:textarea  property="contexto" onkeypress="" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Definicion problema --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.definicionproblema" /></td>
								<td colspan="3"><html:textarea  property="definicionProblema" onkeypress="" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Alcance --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.alcance" /></td>
								<td colspan="3"><html:textarea  property="alcance" onkeypress="" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>		
							
							<%-- Campo Objetivo General --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.objetivogeneral" /></td>
								<td colspan="3"><html:textarea  property="objetivoGeneral" onkeypress="" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>
							
							<%-- Campo Objetivos Especificos --%>
							<tr>								
								<td align="left" colspan="1"><vgcutil:message key="jsp.editariniciativa.ficha.objetivosespecificos" /></td>
								<td colspan="3"><html:textarea  property="objetivoEspecificos" onkeypress="" cols="88" rows="5" styleClass="cuadroTexto" /></td>																		
							</tr>											
																																										
						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Responsables -->
					<vgcinterfaz:panelContenedor anchoPestana="100" nombre="responsables">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.responsables" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" style="height: 160px; border-spacing: 0px; padding: 3px; boder:0px; border-collapse: collapse;">

							<%-- Responsable de Fijar Meta--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editariniciativa.ficha.responsablefijarmeta" /></td>
								<td>
									<html:text style="width: 600px;" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="responsableFijarMeta" onkeypress="ejecutarPorDefecto(event)" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer" onclick="seleccionarResponsableFijarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.fijar.meta" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableFijarMeta();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>

							<%-- Responsable de Lograr Meta--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editariniciativa.ficha.responsablelograrmeta" /></td>
								<td>
									<html:text style="width: 600px;" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="responsableLograrMeta" onkeypress="ejecutarPorDefecto(event)" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer" onclick="seleccionarResponsableLograrMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.lograr.meta" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableLograrMeta();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>

							<%-- Responsable de Seguimiento--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editariniciativa.ficha.responsableseguimiento" /></td>
								<td>
									<html:text style="width: 600px;" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="responsableSeguimiento" onkeypress="ejecutarPorDefecto(event)" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer" onclick="seleccionarResponsableSeguimiento();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.seguimiento" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableSeguimiento();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>

							<%-- Responsable de Lograr Meta--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editariniciativa.ficha.responsablecargarmeta" /></td>
								<td>
									<html:text style="width: 600px;" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="responsableCargarMeta" onkeypress="ejecutarPorDefecto(event)" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer" onclick="seleccionarResponsableCargarMeta();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.cargar.meta" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableCargarMeta();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>

							<%-- Responsable de Cargar Ejecutado --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message key="jsp.editariniciativa.ficha.responsablecargarejecutado" /></td>
								<td>
									<html:text style="width: 600px;" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="responsableCargarEjecutado" onkeypress="ejecutarPorDefecto(event)" size="40" readonly="true" maxlength="50" styleClass="cuadroTexto" />
									<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer" onclick="seleccionarResponsableCargarEjecutado();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="11" height="11" title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.cargar.ejecutado" />">&nbsp;<img style="cursor: pointer" onclick="limpiarResponsableCargarEjecutado();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual>
								</td>
							</tr>
							</table>
							
							<table class="bordeFichaDatos" cellpadding="8" cellspacing="5" >
							<tr>
								<!-- Campo resultado Global -->
								<td valign="bottom" colspan="1"><b><vgcutil:message key="jsp.editariniciativa.ficha.resultado.global" /></b></td>								
							</tr>
							
							<tr>
								<td valign="top"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="resultado" onkeypress="ejecutarPorDefecto(event)" cols="114" rows="10" styleClass="cuadroTexto" /></td>
							</tr>

							<tr>
							<!-- Campo Resultado Especifico -->
								<td valign="bottom">
									<b><vgcutil:message key="jsp.editariniciativa.ficha.resultado.especifico" />&nbsp;&nbsp;</b>
									<html:select disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="ano" onchange="mostrarResultadoEspecifico();" styleClass="cuadroTexto">
									<html:optionsCollection property="grupoAnos" value="clave" label="valor" />
									</html:select>
								</td>
							</tr>

							<tr>								
								<td valign="top"><html:textarea disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="resultadoEspecificoIniciativa" cols="114" rows="10" styleClass="cuadroTexto" onblur="guardarResultadoEspecifico();" /></td>
							</tr>
						</table>
							
						
					</vgcinterfaz:panelContenedor>

					<!-- Panel: Alertas -->
					<vgcinterfaz:panelContenedor anchoPestana="70" nombre="alertas">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.alertas" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellpadding="3" cellspacing="0" border="0">
							<tr>
								<td>
								<table class="contenedorBotonesSeleccion" width="100%" cellpadding="3" cellspacing="3" border="0">
									<tr>
										<td align="right" width="30%"><vgcutil:message key="jsp.editariniciativa.ficha.alerta.zonaverde" /></td>
										<td>
										<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
											<tr>
												<td valign="middle" align="left">
													<html:text size="5" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="alertaZonaVerde" styleClass="cuadroTexto" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
												</td>
												<td>&nbsp;%</td>
											</tr>
										</table>
										</td>
									</tr>

									<tr>
										<td align="right" width="30%"><vgcutil:message key="jsp.editariniciativa.ficha.alerta.zonaamarilla" /></td>
										<td>
											<table border="0" cellpadding="0" cellspacing="0" class="bordeFichaDatos">
												<tr>
													<td valign="middle" align="left">
														<html:text size="5" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" property="alertaZonaAmarilla" styleClass="cuadroTexto" onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)" onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)" onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
													</td>
													<td>&nbsp;%</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr height="100%">
								<td colspan="2">&nbsp;</td>
							</tr>
						</table>

						<map name="MapControlUpDown1" id="MapControlUpDown1">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZonaVerde')" onmouseout="normalAction('botonZonaVerde')" onmousedown="iniciarConteoContinuo('alertaZonaVerde');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZonaVerde')" onmouseout="normalAction('botonZonaVerde')" onmousedown="iniciarConteoContinuo('alertaZonaVerde');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown2" id="MapControlUpDown2">
							<area shape="rect" coords="0,0,14,11" href="#" onmouseover="upAction('botonZonaAmarilla')" onmouseout="normalAction('botonZonaAmarilla')" onmousedown="iniciarConteoContinuo('alertaZonaAmarilla');aumentoConstante()" onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#" onmouseover="downAction('botonZonaAmarilla')" onmouseout="normalAction('botonZonaAmarilla')" onmousedown="iniciarConteoContinuo('alertaZonaAmarilla');decrementoConstante()" onmouseup="finalizarConteoContinuo()" />
						</map>
					</vgcinterfaz:panelContenedor>					
				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
					<logic:notEqual name="editarIniciativaForm" property="bloqueado" value="true">
						<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarIniciativaForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		<script type="text/javascript">
			inicializarResultadosEspecificos();
			inicializarCheckboxSincronizadorCamposTexto(document.editarIniciativaForm.nombreLargo, document.editarIniciativaForm.nombre, 250, document.editarIniciativaForm.sincronizarNombres);
			_frecuencia = document.editarIniciativaForm.frecuencia.value;
			if (document.editarIniciativaForm.tipoMedicion[0].checked)
				_tipoMedicion = 0;
			else if (document.editarIniciativaForm.tipoMedicion[1].checked)
				_tipoMedicion = 1;
		</script>
	</tiles:put>

</tiles:insert>
