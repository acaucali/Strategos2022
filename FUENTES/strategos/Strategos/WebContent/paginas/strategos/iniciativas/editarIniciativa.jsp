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
		<bean:write scope="session" name="activarIniciativa"
			property="nombrePlural" />
	</bean:define>

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">

		<%-- Para el caso de Nuevo --%>
		<logic:equal name="editarIniciativaForm" property="iniciativaId"
			value="0">
			<logic:empty name="editarIniciativaForm"
				property="nombreIniciativaSingular">
				<vgcutil:message key="jsp.editariniciativa.titulo.nuevo"
					arg0="<%= tituloIniciativa %>" />
			</logic:empty>
			<logic:notEmpty name="editarIniciativaForm"
				property="nombreIniciativaSingular">
				<bean:write name="editarIniciativaForm"
					property="nombreIniciativaSingular" />
			</logic:notEmpty>
		</logic:equal>

		<%-- Para el caso de Modificar --%>
		<logic:notEqual name="editarIniciativaForm" property="iniciativaId"
			value="0">
			<logic:empty name="editarIniciativaForm"
				property="nombreIniciativaSingular">
				<vgcutil:message key="jsp.editariniciativa.titulo.modificar"
					arg0="<%= tituloIniciativa %>" />
			</logic:empty>
			<logic:notEmpty name="editarIniciativaForm"
				property="nombreIniciativaSingular">
				<vgcutil:message key="menu.edicion.modificar" />&nbsp;<bean:write
					name="editarIniciativaForm" property="nombreIniciativaSingular" />
			</logic:notEmpty>
		</logic:notEqual>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funci�n JavaScript externa --%>
		<jsp:include page="/componentes/fichaDatos/fichaDatosJs.jsp"></jsp:include>

		<%-- Funci�n JavaScript externa --%>
		<jsp:include page="/paginas/strategos/responsables/responsablesJs.jsp"></jsp:include>

		<%-- Funci�n JavaScript externa --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<script type="text/javascript"
			src="<html:rewrite page='/paginas/strategos/iniciativas/iniciativaJs/iniciativaJs.js'/>"></script>
		<script type="text/javascript"
			src="<html:rewrite  page='/componentes/cuadroNumerico/cuadroNumerico.js'/>"></script>
		<script type="text/javascript"
			src="<html:rewrite page='/paginas/strategos/indicadores/indicadoresJs/indicadores.js'/>"></script>


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
					var selectCargo = document.getElementById('selectCargo');
					if (selectCargo != null)
						url = url + '&selectCargo=' + selectCargo.value;
					
					var selectUnidad = document.getElementById('selectUnidad');
					if (selectUnidad != null)
						url = url + '&selectUnidad=' + selectUnidad.value;
					
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
				if(document.editarIniciativaForm.nombre.value === "" ){
					alert("Campo nombre no puede estar vacio")
				}
				else{
					
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
			
			function seleccionarFechaInicio() 
			{			
				mostrarCalendarioConFuncionCierre('document.editarIniciativaForm.fechaInicio' , document.editarIniciativaForm.fechaInicio.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
			
			function seleccionarFechaTerminacion() 
			{
				mostrarCalendarioConFuncionCierre('document.editarIniciativaForm.fechaFin' , document.editarIniciativaForm.fechaFin.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
			
			function seleccionarFechaActaInicio() 
			{
				mostrarCalendarioConFuncionCierre('document.editarIniciativaForm.fechaActaInicio' , document.editarIniciativaForm.fechaActaInicio.value, '<vgcutil:message key="formato.fecha.corta" />', false);
			}
			
		</script>

		<%-- Tag que devuelve el foco al objeto indicado --%>
		<jsp:include flush="true"
			page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
		<vgcinterfaz:setFocoObjetoInterfaz
			objetoInputHtml="document.editarIniciativaForm.nombre" />

		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

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
			<html:hidden property="cargoId" />
			<html:hidden property="faseId" />
			<html:hidden property="unidad" />

			<vgcinterfaz:contenedorForma width="940px" height="740px"
				bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>..::
					<%-- Para el caso de Nuevo --%>
					<logic:equal name="editarIniciativaForm" property="iniciativaId"
						value="0">
						<logic:empty name="editarIniciativaForm"
							property="nombreIniciativaSingular">
							<vgcutil:message key="jsp.editariniciativa.titulo.nuevo"
								arg0="<%= tituloIniciativa %>" />
						</logic:empty>
						<logic:notEmpty name="editarIniciativaForm"
							property="nombreIniciativaSingular">
							<bean:write name="editarIniciativaForm"
								property="nombreIniciativaSingular" />
						</logic:notEmpty>

					</logic:equal>

					<%-- Para el caso de Modificar --%>
					<logic:notEqual name="editarIniciativaForm" property="iniciativaId"
						value="0">
						<logic:empty name="editarIniciativaForm"
							property="nombreIniciativaSingular">
							<vgcutil:message key="jsp.editariniciativa.titulo.modificar"
								arg0="<%= tituloIniciativa %>" />
						</logic:empty>
						<logic:notEmpty name="editarIniciativaForm"
							property="nombreIniciativaSingular">
							<vgcutil:message key="menu.edicion.modificar" />&nbsp;<bean:write
								name="editarIniciativaForm" property="nombreIniciativaSingular" />
						</logic:notEmpty>
					</logic:notEqual>

				</vgcinterfaz:contenedorFormaTitulo>

				<vgcinterfaz:contenedorPaneles height="630px" width="860px"
					nombre="editarIniciativa">

					<!-- Panel: Datos B�sicos -->
					<vgcinterfaz:panelContenedor anchoPestana="105"
						nombre="datosBasicos">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.datos.basicos" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="bordeFichaDatos" cellpadding="4" cellspacing="10"
							border="0">
							<%-- Organizacion --%>

							<logic:notEqual name="editarIniciativaForm"
								property="desdeInstrumento" value="true">
								<tr>
									<td align="right"><b><vgcutil:message
												key="jsp.editariniciativa.ficha.organizacion" /></b></td>
									<td colspan="3"><bean:write name="editarIniciativaForm"
											property="organizacionNombre" /></td>
								</tr>
							</logic:notEqual>

							<logic:equal name="editarIniciativaForm"
								property="desdeInstrumento" value="true">
								<tr>
									<td align="right"><b><vgcutil:message
												key="jsp.editariniciativa.ficha.organizacion" /></b></td>
									<logic:equal name="editarIniciativaForm"
										property="iniciativaId" value="0">
										<td align="right"><input type="button" style="width: 80%"
											class="cuadroTexto"
											value="<vgcutil:message key="jsp.seleccionarindicador.seleccionarorganizacion" />"
											onclick="seleccionarOrganizaciones();"></td>
									</logic:equal>
									<logic:notEqual name="editarIniciativaForm"
										property="iniciativaId" value="0">
										<td colspan="3"><bean:write name="editarIniciativaForm"
												property="organizacionNombre" /></td>
									</logic:notEqual>
								</tr>
							</logic:equal>

							<!-- Campo Nombre -->
							<tr>
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.nombre" /></td>
								<td colspan="3"><html:text property="nombre"
										 size="90"
										disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
										maxlength="250" styleClass="cuadroTexto"
										/></td>
							</tr>

							<%-- Campo descripci�n --%>
							<tr>
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.descripcion" /></td>
								<td colspan="3"><html:textarea 
										disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
										property="descripcion" onkeypress="ejecutarPorDefecto(event)"
										cols="88" rows="3" styleClass="cuadroTexto" /></td>

							</tr>

							<!-- Campos tipo proyecto - a�os formulacion -->
							<tr>
								<!-- Tipo proyecto -->
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.tipoproyecto" /></td>
								<td><logic:notEqual name="editarIniciativaForm"
										property="bloqueado" value="true">
										<select class="cuadroCombinado" name="selectTipoProyecto"
											id="selectTipoProyecto">
											<logic:iterate name="editarIniciativaForm" property="tipos"
												id="tip">
												<bean:define id="tipoProyectoId" toScope="page">
													<bean:write name='tip' property='tipoProyectoId' />
												</bean:define>
												<bean:define id="nombre" toScope="page">
													<bean:write name='tip' property='nombre' />
												</bean:define>
												<logic:equal name='editarIniciativaForm' property='tipoId'
													value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm'
													property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:notEqual> <logic:equal name="editarIniciativaForm" property="bloqueado"
										value="true">
										<select class="cuadroCombinado" name="selectTipoProyecto"
											id="selectTipoProyecto"
											disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
											<logic:iterate name="editarIniciativaForm" property="tipos"
												id="tip">
												<bean:define id="tipoProyectoId" toScope="page">
													<bean:write name='tip' property='tipoProyectoId' />
												</bean:define>
												<bean:define id="nombre" toScope="page">
													<bean:write name='tip' property='nombre' />
												</bean:define>
												<logic:equal name='editarIniciativaForm' property='tipoId'
													value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm'
													property='tipoId' value='<%=tipoProyectoId.toString()%>'>
													<option value="<%=tipoProyectoId%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:equal></td>

								<!-- A�o formulaci�n -->
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.anioformulacion" /></td>
								<td colspan="1"><html:text property="anioFormulacion"
										size="5" disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
										maxlength="4" styleClass="cuadroTexto" /></td>

							</tr>

							<!-- Campos frecuencia - estatus-->
							<tr>

								<!-- Frecuencia -->
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.frecuencia" /></td>
								<td><html:select property="frecuencia"
										disabled="<%= Boolean.parseBoolean(bloquearForma) %>"
										styleClass="cuadroTexto" size="1">
										<html:option value=""></html:option>
										<html:optionsCollection property="frecuencias"
											value="frecuenciaId" label="nombre" />
									</html:select></td>


								<!-- Estatus -->
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.estatus" /></td>
								<td><logic:notEqual name="editarIniciativaForm"
										property="bloqueado" value="true">
										<select class="cuadroCombinado" name="selectEstatusType"
											id="selectEstatusType">
											<logic:iterate name="editarIniciativaForm"
												property="estatuses" id="est">
												<bean:define id="id" toScope="page">
													<bean:write name='est' property='id' />
												</bean:define>
												<bean:define id="nombre" toScope="page">
													<bean:write name='est' property='nombre' />
												</bean:define>
												<logic:equal name='editarIniciativaForm'
													property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm'
													property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:notEqual> <logic:equal name="editarIniciativaForm" property="bloqueado"
										value="true">
										<select class="cuadroCombinado" name="selectEstatusType"
											id="selectEstatusType"
											disabled="<%= Boolean.parseBoolean(bloquearForma) %>">
											<logic:iterate name="editarIniciativaForm"
												property="estatuses" id="est">
												<bean:define id="id" toScope="page">
													<bean:write name='est' property='id' />
												</bean:define>
												<bean:define id="nombre" toScope="page">
													<bean:write name='est' property='nombre' />
												</bean:define>
												<logic:equal name='editarIniciativaForm'
													property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm'
													property='estatusId' value='<%=id.toString()%>'>
													<option value="<%=id%>"><%=nombre%></option>
												</logic:notEqual>
											</logic:iterate>
										</select>
									</logic:equal></td>

							</tr>

							<%-- Campo Tipo de Medici�n --%>
							<tr align="left">
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.titulo.mediciones" /></td>
								<td><html:radio property="tipoMedicion" value="0"
										disabled="<%= Boolean.parseBoolean(bloquearForma) %>" /> <vgcutil:message
										key="jsp.editariniciativa.ficha.tipomedicion.enperiodo" />
									&nbsp;&nbsp; <html:radio property="tipoMedicion" value="1"
										disabled="<%= Boolean.parseBoolean(bloquearForma) %>" /> <vgcutil:message
										key="jsp.editariniciativa.ficha.tipomedicion.alperiodo" /></td>
								<td align="left" colspan="1">Codigo</td>
								<td colspan="1"><html:text property="codigoIniciativa"
										onkeypress="ejecutarPorDefecto(event)" size="36"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<tr>
								<td align="left"><vgcutil:message
										key="jsp.pagina.instrumentos.fecha" /></td>
								<td><html:text property="fechaInicio"
										onkeypress="ejecutarPorDefecto(event)" size="30"
										maxlength="10" styleClass="cuadroTexto" /> <span
									style="padding: 2px"> <img style="cursor: pointer"
										onclick="seleccionarFechaInicio();"
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>"
										border="0" width="10" height="10"
										title="<vgcutil:message 
										key="boton.calendario.alt" />">
								</span></td>

								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.fechafin" /></td>

								<td><html:text property="fechaFin"
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

							<%-- Campo Responsable proy - cargo --%>
							<tr>
								<!-- Responsable Proyecto -->
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.reponsableproyecto" /></td>

								<td colspan="1"><html:text property="responsableProyecto"
										onkeypress="ejecutarPorDefecto(event)" size="33"
										styleClass="cuadroTexto" /> <!-- Cargo -->
								<td align="left"><vgcutil:message
										key="jsp.editariniciativa.ficha.cargo" /></td>
								<td><select class="cuadroCombinado" name="selectCargo"
									id="selectCargo">
										<option value="" selected></option>
										<logic:iterate name="editarIniciativaForm" property="cargos"
											id="car">

											<bean:define id="cargoId" toScope="page">
												<bean:write name='car' property='cargoId' />
											</bean:define>
											<bean:define id="nombre" toScope="page">
												<bean:write name='car' property='nombre' />
											</bean:define>

											<logic:equal name='editarIniciativaForm' property='cargoId'
												value='<%=cargoId.toString()%>'>
												<option value="<%=cargoId%>" selected><%=nombre%></option>
											</logic:equal>
											<logic:notEqual name='editarIniciativaForm'
												property='cargoId' value='<%=cargoId.toString()%>'>
												<option value="<%=cargoId%>"><%=nombre%></option>
											</logic:notEqual>

										</logic:iterate>
								</select></td>
							</tr>

							<%-- Campo Objetivo Estrategico --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.objetivoestrategico" /></td>
								<td colspan="3"><html:textarea
										property="objetivoEstrategico"
										onkeypress="ejecutarPorDefecto(event)" cols="88" rows="2"										
										styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Iniciativa Estrategica--%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.iniciativaestrategica" /></td>
								<td colspan="3"><html:textarea
										property="iniciativaEstrategica" onkeypress="" cols="88"
										rows="1" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Poblacion Beneficiada --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.poblacionbeneficiada" /></td>
								<td colspan="3"><html:textarea
										property="poblacionBeneficiada" onkeypress="" cols="88"
										rows="2" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Contexto --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.contexto" /></td>
								<td colspan="3"><html:textarea property="contexto"
										onkeypress="" cols="88" rows="4" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Justificacion --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.justificacion" /></td>
								<td colspan="3"><html:textarea property="justificacion"
										onkeypress="" cols="88" rows="4" styleClass="cuadroTexto" /></td>
							</tr>



							<!-- Campo enteEjecutor
							<tr>
								<td align="right"><vgcutil:message key="jsp.editariniciativa.ficha.enteejecutor" /></td>
								<td colspan="3"><html:text property="enteEjecutor" size="63" disabled="<%= Boolean.parseBoolean(bloquearForma) %>" maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>  -->

						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Parte II -->
					<vgcinterfaz:panelContenedor anchoPestana="105"
						nombre="datosBasicosII">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message
								key="jsp.editariniciativa.pestana.datos.basicos.dos" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="bordeFichaDatos" cellpadding="5" cellspacing="4"
							border="0">



							<%-- Campo Lider Iniciativa --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.lideriniciativa" /></td>
								<td colspan="3"><html:textarea property="liderIniciativa"
										onkeypress="" cols="85" rows="2" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Tipo Iniciativa --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.tipoiniciativa" /></td>
								<td colspan="3"><html:textarea property="tipoIniciativa"
										onkeypress="" cols="85" rows="2" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Definicion problema --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.definicionproblema" /></td>
								<td colspan="3"><html:textarea
										property="definicionProblema" onkeypress="" cols="85" rows="5"
										styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Alcance --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.alcance" /></td>
								<td colspan="3"><html:textarea property="alcance"
										onkeypress="" cols="85" rows="5" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Objetivo General --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.objetivogeneral" /></td>
								<td colspan="3"><html:textarea property="objetivoGeneral"
										onkeypress="" cols="85" rows="2" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Objetivos Especificos --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.objetivosespecificos" /></td>
								<td colspan="3"><html:textarea
										property="objetivoEspecificos" onkeypress="" cols="85"
										rows="5" styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Organizaciones involucradas --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.organizacionesinvolucradas" /></td>
								<td colspan="3"><html:textarea
										property="organizacionesInvolucradas"
										onkeypress="ejecutarPorDefecto(event)" cols="85" rows="4"
										styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo Fuente financiacion - monto --%>
							<tr>
								<!-- Fuente FInanciacion -->
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.fuentefinanciacion" /></td>
								<td colspan="1"><html:text property="fuenteFinanciacion"
										onkeypress="ejecutarPorDefecto(event)" size="30"
										maxlength="150" styleClass="cuadroTexto" /> <!-- Monto Financiamiento -->
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.monto" /></td>
								<td colspan="1"><html:text property="montoFinanciamiento"
										onkeypress="" size="30" maxlength="150"
										styleClass="cuadroTexto" />
							</tr>

							<%-- Campo Monto Total - Monto Moneda Extranjera --%>
							<tr>
								<!-- Fuente FInanciacion -->
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.monto.total" /></td>
								<td colspan="1"><html:text property="montoTotal"
										onkeypress="ejecutarPorDefecto(event)" size="30"
										maxlength="150" styleClass="cuadroTexto" /> <!-- Monto Financiamiento -->
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.monto.moneda.extranjera" /></td>
								<td colspan="1"><html:text property="montoMonedaExt"
										onkeypress="" size="30" maxlength="150"
										styleClass="cuadroTexto" />
							</tr>

							<%-- Campo Situacion Presupuestaria --%>
							<tr>
								<td align="left" colspan="1"><vgcutil:message
										key="jsp.editariniciativa.ficha.situacion.presupuestaria" /></td>
								<td colspan="3"><html:textarea
										property="situacionPresupuestaria"
										onkeypress="ejecutarPorDefecto(event)" cols="85" rows="2"
										styleClass="cuadroTexto" /></td>
							</tr>

						</table>

					</vgcinterfaz:panelContenedor>

					<!-- Panel: Parte III -->
					<vgcinterfaz:panelContenedor anchoPestana="100"
						nombre="datosBasicosIII">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message
								key="jsp.editariniciativa.pestana.datos.basicos.tres" />
						</vgcinterfaz:panelContenedorTitulo>

						<table class="bordeFichaDatos" cellpadding="8" cellspacing="5">
							<tr>
								<!-- Campo resultado Global -->
								<td valign="bottom" colspan="1"><b><vgcutil:message
											key="jsp.editariniciativa.ficha.resultado.global" /></b></td>
							</tr>

							<tr>
								<td valign="top"><html:textarea
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="resultado" onkeypress="ejecutarPorDefecto(event)"
										cols="114" rows="5" styleClass="cuadroTexto" /></td>
							</tr>

							<tr>
								<!-- Campo Resultado Especifico -->
								<td valign="bottom"><b><vgcutil:message
											key="jsp.editariniciativa.ficha.resultado.especifico" />&nbsp;&nbsp;</b>
									<html:select
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="ano" onchange="mostrarResultadoEspecifico();"
										styleClass="cuadroTexto">
										<html:optionsCollection property="grupoAnos" value="clave"
											label="valor" />
									</html:select></td>
							</tr>

							<tr>
								<td valign="top"><html:textarea
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="resultadoEspecificoIniciativa" cols="114" rows="5"
										styleClass="cuadroTexto"
										onblur="guardarResultadoEspecifico();" /></td>
							</tr>

							<%-- Campo hitos --%>
							<tr>
								<td align="left" colspan="1"><b><vgcutil:message
											key="jsp.editariniciativa.ficha.hitos" /></b></td>
							</tr>
							<tr>
								<td colspan="3"><html:textarea property="hitos"
										onkeypress="ejecutarPorDefecto(event)" cols="114" rows="6"
										styleClass="cuadroTexto" /></td>
							</tr>

							<%-- Campo hitos --%>
							<tr>
								<td align="left" colspan="1"><b><vgcutil:message
											key="jsp.editariniciativa.ficha.sector" /></b></td>
							</tr>
							<tr>
								<td colspan="3"><html:textarea property="sector"
										onkeypress="ejecutarPorDefecto(event)" cols="114" rows="2"
										styleClass="cuadroTexto" /></td>
							</tr>

							<tr>
								<td align="left"><b><vgcutil:message
											key="jsp.editariniciativa.ficha.fecha.acta.inicio" /></b></td>
							</tr>
							<tr>
								<td><html:text property="fechaActaInicio"
										onkeypress="ejecutarPorDefecto(event)" size="30"
										maxlength="10" styleClass="cuadroTexto" /> <span
									style="padding: 2px"> <img style="cursor: pointer"
										onclick="seleccionarFechaActaInicio();"
										src="<html:rewrite page='/componentes/calendario/calendario.gif'/>"
										border="0" width="10" height="10"
										title="<vgcutil:message 
											key="boton.calendario.alt" />">
								</span></td>
							</tr>
						</table>


						<table class="bordeFichaDatos" cellpadding="5" cellspacing="4"
							border="0">



						</table>


					</vgcinterfaz:panelContenedor>

					<logic:equal name="editarIniciativaForm"
						property="mostrarAdministracionPublica" value="true">


						<!-- Panel: Parte IV -->
						<vgcinterfaz:panelContenedor anchoPestana="100"
							nombre="datosBasicosIV">
							<vgcinterfaz:panelContenedorTitulo>
								<vgcutil:message
									key="jsp.editariniciativa.pestana.datos.basicos.cuatro" />
							</vgcinterfaz:panelContenedorTitulo>

							<table class="bordeFichaDatos" cellpadding="8" cellspacing="5">
								<%-- Campo Responsable Gerencia General --%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.responsable.gerencia.general" /></td>
									<td colspan="3"><html:textarea
											property="gerenciaGeneralesRes"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<tr>
									<!-- Cargo -->
									<td align="left"><vgcutil:message
									key="jsp.editariniciativa.ficha.fase" /></td>
									<!-- Cargo -->
									<td><select class="cuadroCombinado" name="selectFase"
										id="selectFase">
											<option value="" selected></option>
											<logic:iterate name="editarIniciativaForm" property="fases"
												id="fase">

												<bean:define id="faseId" toScope="page">
													<bean:write name='fase' property='faseProyectoId' />
												</bean:define>
												<bean:define id="nombre" toScope="page">
													<bean:write name='fase' property='nombre' />
												</bean:define>

												<logic:equal name='editarIniciativaForm' property='faseId'
													value='<%=faseId.toString()%>'>
													<option value="<%=faseId%>" selected><%=nombre%></option>
												</logic:equal>
												<logic:notEqual name='editarIniciativaForm'
													property='faseId' value='<%=faseId.toString()%>'>
													<option value="<%=faseId%>"><%=nombre%></option>
												</logic:notEqual>

											</logic:iterate>
									</select></td>
								</tr>

								<%-- Campo Codigo Sipe --%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.codigo.sipe" /></td>
									<td colspan="3"><html:textarea property="codigoSipe"
											onkeypress="ejecutarPorDefecto(event)" cols="50" rows="1"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Proyecto Presupuestario Asociado --%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.proyecto.presupuestario" /></td>
									<td colspan="3"><html:textarea
											property="proyectoPresupAso"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>
							</table>
							<br>
							<br>
						&nbsp;&nbsp;								
						<b><vgcutil:message
									key="jsp.editariniciativa.ficha.localizacion" /></b>
							<table class="bordeFichaDatos" cellpadding="8" cellspacing="5">

								<%-- Campo Estado--%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.estado" /></td>
									<td colspan="3"><html:textarea property="estado"
											onkeypress="ejecutarPorDefecto(event)" cols="50" rows="1"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Municipio --%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.municipio" /></td>
									<td colspan="3"><html:textarea property="municipio"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="1"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Parroquia --%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.parroquia" /></td>
									<td colspan="3"><html:textarea property="parroquia"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="1"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Direccion del proyecto --%>
								<tr>
									<td align="left" colspan="1"><vgcutil:message
									key="jsp.editariniciativa.ficha.direccion" /></td>
									<td colspan="3"><html:textarea
											property="direccionProyecto"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

							</table>




						</vgcinterfaz:panelContenedor>

						<!-- Panel: Parte V -->
						<vgcinterfaz:panelContenedor anchoPestana="100"
							nombre="datosBasicosV">
							<vgcinterfaz:panelContenedorTitulo>
								<vgcutil:message
									key="jsp.editariniciativa.pestana.datos.basicos.cinco" />
							</vgcinterfaz:panelContenedorTitulo>

							<br>
						
						&nbsp;&nbsp;								
						<b>Plan de Patria</b>
							<table class="bordeFichaDatos" cellpadding="8" cellspacing="5">

								<%-- Campo Objetivo Historico--%>
								<tr>
									<td align="left" colspan="1">Objetivo Historico</td>
									<td colspan="3"><html:textarea
											property="objetivoHistorico"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Objetivo Nacional --%>
								<tr>
									<td align="left" colspan="1">Objetivo Nacional</td>
									<td colspan="3"><html:textarea property="objetivoNacional"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Objetivo Estrategico --%>
								<tr>
									<td align="left" colspan="1">Objetivo Estrategico</td>
									<td colspan="3"><html:textarea
											property="objetivoEstrategicoPV"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Objetivo General --%>
								<tr>
									<td align="left" colspan="1">Objetivo General</td>
									<td colspan="3"><html:textarea
											property="objetivoGeneralPV"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Objetivo Especifico --%>
								<tr>
									<td align="left" colspan="1">Objetivo Especifico</td>
									<td colspan="3"><html:textarea
											property="objetivoEspecifico"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Programa --%>
								<tr>
									<td align="left" colspan="1">Programa</td>
									<td colspan="3"><html:textarea property="programa"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Problemas --%>
								<tr>
									<td align="left" colspan="1">Problemas</td>
									<td colspan="3"><html:textarea property="problemas"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="3"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Causas --%>
								<tr>
									<td align="left" colspan="1">Causas</td>
									<td colspan="3"><html:textarea property="causas"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="3"
											styleClass="cuadroTexto" /></td>
								</tr>

								<%-- Campo Lineas Estrategicas --%>
								<tr>
									<td align="left" colspan="1">Lineas Estrategicas</td>
									<td colspan="3"><html:textarea
											property="lineasEstrategicas"
											onkeypress="ejecutarPorDefecto(event)" cols="97" rows="2"
											styleClass="cuadroTexto" /></td>
								</tr>

							</table>

						</vgcinterfaz:panelContenedor>

					</logic:equal>

					<!-- Panel: Responsables -->
					<vgcinterfaz:panelContenedor anchoPestana="100"
						nombre="responsables">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.responsables" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor"
							style="height: 160px; border-spacing: 0px; padding: 3px; boder: 0px; border-collapse: collapse;">

							<%-- Responsable de Fijar Meta--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message
										key="jsp.editariniciativa.ficha.responsablefijarmeta" /></td>
								<td><html:text style="width: 600px;"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="responsableFijarMeta"
										onkeypress="ejecutarPorDefecto(event)" size="40"
										readonly="true" maxlength="50" styleClass="cuadroTexto" /> <logic:notEqual
										name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer"
											onclick="seleccionarResponsableFijarMeta();"
											src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>"
											border="0" width="11" height="11"
											title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.fijar.meta" />">&nbsp;<img
											style="cursor: pointer"
											onclick="limpiarResponsableFijarMeta();"
											src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
											border="0" width="10" height="10"
											title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual></td>
							</tr>

							<%-- Responsable de Lograr Meta--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message
										key="jsp.editariniciativa.ficha.responsablelograrmeta" /></td>
								<td><html:text style="width: 600px;"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="responsableLograrMeta"
										onkeypress="ejecutarPorDefecto(event)" size="40"
										readonly="true" maxlength="50" styleClass="cuadroTexto" /> <logic:notEqual
										name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer"
											onclick="seleccionarResponsableLograrMeta();"
											src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>"
											border="0" width="11" height="11"
											title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.lograr.meta" />">&nbsp;<img
											style="cursor: pointer"
											onclick="limpiarResponsableLograrMeta();"
											src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
											border="0" width="10" height="10"
											title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual></td>
							</tr>

							<%-- Responsable de Seguimiento--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message
										key="jsp.editariniciativa.ficha.responsableseguimiento" /></td>
								<td><html:text style="width: 600px;"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="responsableSeguimiento"
										onkeypress="ejecutarPorDefecto(event)" size="40"
										readonly="true" maxlength="50" styleClass="cuadroTexto" /> <logic:notEqual
										name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer"
											onclick="seleccionarResponsableSeguimiento();"
											src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>"
											border="0" width="11" height="11"
											title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.seguimiento" />">&nbsp;<img
											style="cursor: pointer"
											onclick="limpiarResponsableSeguimiento();"
											src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
											border="0" width="10" height="10"
											title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual></td>
							</tr>

							<%-- Responsable de Lograr Meta--%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message
										key="jsp.editariniciativa.ficha.responsablecargarmeta" /></td>
								<td><html:text style="width: 600px;"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="responsableCargarMeta"
										onkeypress="ejecutarPorDefecto(event)" size="40"
										readonly="true" maxlength="50" styleClass="cuadroTexto" /> <logic:notEqual
										name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer"
											onclick="seleccionarResponsableCargarMeta();"
											src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>"
											border="0" width="11" height="11"
											title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.cargar.meta" />">&nbsp;<img
											style="cursor: pointer"
											onclick="limpiarResponsableCargarMeta();"
											src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
											border="0" width="10" height="10"
											title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual></td>
							</tr>

							<%-- Responsable de Cargar Ejecutado --%>
							<tr>
								<td style="text-align: left; width: 200px;"><vgcutil:message
										key="jsp.editariniciativa.ficha.responsablecargarejecutado" /></td>
								<td><html:text style="width: 600px;"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										property="responsableCargarEjecutado"
										onkeypress="ejecutarPorDefecto(event)" size="40"
										readonly="true" maxlength="50" styleClass="cuadroTexto" /> <logic:notEqual
										name="editarIniciativaForm" property="bloqueado" value="true">
										&nbsp;<img style="cursor: pointer"
											onclick="seleccionarResponsableCargarEjecutado();"
											src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>"
											border="0" width="11" height="11"
											title="<vgcutil:message key="jsp.editariniciativa.ficha.seleccionarresponsable.cargar.ejecutado" />">&nbsp;<img
											style="cursor: pointer"
											onclick="limpiarResponsableCargarEjecutado();"
											src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>"
											border="0" width="10" height="10"
											title="<vgcutil:message key="boton.limpiar.alt" />">
									</logic:notEqual></td>
							</tr>
						</table>
						<br>

						<b>Gerente Del Proyecto</b>
						<br>
						<br>
						<table class="panelContenedor"
							style="height: 50px; border-spacing: 0px; padding: 3px; boder: 0px; border-collapse: collapse;">
							<!-- Campos tipo proyecto - a�os formulacion -->
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Nombre</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="gerenteProyectoNombre" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Cedula De Identidad</td>
								<td colspan="2"><html:text property="gerenteProyectoCedula"
										size="30" disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Email</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="gerenteProyectoEmail" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Telefono</td>
								<td colspan="2"><html:text
										property="gerenteProyectoTelefono" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>

							<tr>
						</table>
						<br>
						<b>Responsable Tecnico</b>
						<br>
						<br>

						<table class="panelContenedor"
							style="height: 50px; border-spacing: 0px; padding: 3px; boder: 0px; border-collapse: collapse;">
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Nombre</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableTecnicoNombre" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Cedula De Identidad</td>
								<td colspan="2"><html:text
										property="responsableTecnicoCedula" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Email</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableTecnicoEmail" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Telefono</td>
								<td colspan="2"><html:text
										property="responsableTecnicoTelefono" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
						</table>
						<br>
						<b>Responsable Registrador</b>
						<br>
						<br>
						<table class="panelContenedor"
							style="height: 50px; border-spacing: 0px; padding: 3px; boder: 0px; border-collapse: collapse;">
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Nombre</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableRegistradorNombre" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Cedula De Identidad</td>
								<td colspan="2"><html:text
										property="responsableRegistradorCedula" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Email</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableRegistradorEmail" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Telefono</td>
								<td colspan="2"><html:text
										property="responsableRegistradorTelefono" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
						</table>
						<br>
						<b>Responsable Administrativo</b>
						<br>
						<br>
						<table class="panelContenedor"
							style="height: 50px; border-spacing: 0px; padding: 3px; boder: 0px; border-collapse: collapse;">
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Nombre</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableAdministrativoNombre" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Cedula De Identidad</td>
								<td colspan="2"><html:text
										property="responsableAdministrativoCedula" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Email</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableAdministrativoEmail" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Telefono</td>
								<td colspan="2"><html:text
										property="responsableAdministrativoTelefono" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
						</table>
						<br>
						<b>Responsable Administrador de Contratos</b>
						<br>
						<br>
						<table class="panelContenedor"
							style="height: 50px; border-spacing: 0px; padding: 3px; boder: 0px; border-collapse: collapse;">
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Nombre</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableAdminContratosNombre" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Cedula De Identidad</td>
								<td colspan="2"><html:text
										property="responsableAdminContratosCedula" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
							<tr>
								<!-- Gerente del proyecto -->
								<td align="left">Email</td>
								<td colspan="2">&nbsp;&nbsp;&nbsp; <html:text
										property="responsableAdminContratosEmail" size="50"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" />

								</td>

								<!-- A�o formulaci�n -->
								<td align="left">Telefono</td>
								<td colspan="2"><html:text
										property="responsableAdminContratosTelefono" size="30"
										disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
										maxlength="50" styleClass="cuadroTexto" /></td>
							</tr>
						</table>


					</vgcinterfaz:panelContenedor>

					<!-- Panel: Alertas -->
					<vgcinterfaz:panelContenedor anchoPestana="70" nombre="alertas">
						<vgcinterfaz:panelContenedorTitulo>
							<vgcutil:message key="jsp.editariniciativa.pestana.alertas" />
						</vgcinterfaz:panelContenedorTitulo>
						<table class="panelContenedor" cellpadding="3" cellspacing="0"
							border="0">
							<tr>
								<td>
									<table class="contenedorBotonesSeleccion" width="100%"
										cellpadding="3" cellspacing="3" border="0">
										<tr>
											<td align="right" width="30%"><vgcutil:message
													key="jsp.editariniciativa.ficha.alerta.zonaverde" /></td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0"
													class="bordeFichaDatos">
													<tr>
														<td valign="middle" align="left"><html:text size="5"
																disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
																property="alertaZonaVerde" styleClass="cuadroTexto"
																onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)"
																onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)"
																onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
														</td>
														<td>&nbsp;%</td>
													</tr>
												</table>
											</td>
										</tr>

										<tr>
											<td align="right" width="30%"><vgcutil:message
													key="jsp.editariniciativa.ficha.alerta.zonaamarilla" /></td>
											<td>
												<table border="0" cellpadding="0" cellspacing="0"
													class="bordeFichaDatos">
													<tr>
														<td valign="middle" align="left"><html:text size="5"
																disabled="<%=Boolean.parseBoolean(bloquearForma)%>"
																property="alertaZonaAmarilla" styleClass="cuadroTexto"
																onkeypress="return validarEntradaNumeroEventoOnKeyPress(this, event, 2, false)"
																onkeyup="return validarEntradaNumeroEventoOnKeyUp(this, event, 2, false)"
																onblur="validarEntradaNumeroEventoOnBlur(this, event, 2, false)" />
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
							<area shape="rect" coords="0,0,14,11" href="#"
								onmouseover="upAction('botonZonaVerde')"
								onmouseout="normalAction('botonZonaVerde')"
								onmousedown="iniciarConteoContinuo('alertaZonaVerde');aumentoConstante()"
								onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#"
								onmouseover="downAction('botonZonaVerde')"
								onmouseout="normalAction('botonZonaVerde')"
								onmousedown="iniciarConteoContinuo('alertaZonaVerde');decrementoConstante()"
								onmouseup="finalizarConteoContinuo()" />
						</map>
						<map name="MapControlUpDown2" id="MapControlUpDown2">
							<area shape="rect" coords="0,0,14,11" href="#"
								onmouseover="upAction('botonZonaAmarilla')"
								onmouseout="normalAction('botonZonaAmarilla')"
								onmousedown="iniciarConteoContinuo('alertaZonaAmarilla');aumentoConstante()"
								onmouseup="finalizarConteoContinuo()" />
							<area shape="rect" coords="0,11,14,20" href="#"
								onmouseover="downAction('botonZonaAmarilla')"
								onmouseout="normalAction('botonZonaAmarilla')"
								onmousedown="iniciarConteoContinuo('alertaZonaAmarilla');decrementoConstante()"
								onmouseup="finalizarConteoContinuo()" />
						</map>
					</vgcinterfaz:panelContenedor>


					<logic:equal name="editarIniciativaForm" property="iniciativaId"
						value="0">

						<!-- Panel: plan de cuentas -->
						<bean:define scope="page" id="unidadDisabled" value="false"></bean:define>
						<vgcinterfaz:panelContenedor anchoPestana="150" nombre="cuentas">
							<vgcinterfaz:panelContenedorTitulo>
								<vgcutil:message key="jsp.editariniciativa.pestana.cuentas" />
							</vgcinterfaz:panelContenedorTitulo>
							<table class="panelContenedor" cellpadding="3" cellspacing="0"
								border="0">
								<tr>
									<td>
										<table class="contenedorBotonesSeleccion" width="100%"
											cellpadding="3" cellspacing="3" border="0">
											<tr>
												<td align="left" colspan="1"><vgcutil:message
														key="jsp.editariniciativa.ficha.partidas" /></td>
												<td align="left" colspan="2"><html:radio
														property="partidas" onclick="toggle(this)" value="0"
														disabled="<%=Boolean.parseBoolean(bloquearForma)%>" /> <vgcutil:message
														key="jsp.copiarplan.ficha.claseindicador.si" />
													&nbsp;&nbsp; <html:radio property="partidas"
														onclick="toggle(this)" value="1"
														disabled="<%=Boolean.parseBoolean(bloquearForma)%>" /> <vgcutil:message
														key="jsp.copiarplan.ficha.claseindicador.no" /></td>
											</tr>

											<tr id="partidasPre" style="display: none">
												<td align="right"><vgcutil:message
														key="jsp.editarindicador.ficha.unidadmedida" /></td>
												<td><select class="cuadroCombinado" name="selectUnidad"
													id="selectUnidad">
														<option value="" selected></option>
														<logic:iterate name="editarIniciativaForm"
															property="unidadesMedida" id="und">

															<bean:define id="id" toScope="page">
																<bean:write name='und' property='unidadId' />
															</bean:define>
															<bean:define id="nombre" toScope="page">
																<bean:write name='und' property='nombre' />
															</bean:define>

															<logic:equal name='editarIniciativaForm'
																property='unidad' value='<%=id.toString()%>'>
																<option value="<%=id%>" selected><%=nombre%></option>
															</logic:equal>
															<logic:notEqual name='editarIniciativaForm'
																property='unidad' value='<%=id.toString()%>'>
																<option value="<%=id%>"><%=nombre%></option>
															</logic:notEqual>

														</logic:iterate>
												</select></td>

											</tr>
										</table>
									</td>
								</tr>
								<tr height="100%">
									<td colspan="2">&nbsp;</td>
								</tr>
							</table>

							<map name="MapControlUpDown1" id="MapControlUpDown1">
								<area shape="rect" coords="0,0,14,11" href="#"
									onmouseover="upAction('botonZonaVerde')"
									onmouseout="normalAction('botonZonaVerde')"
									onmousedown="iniciarConteoContinuo('alertaZonaVerde');aumentoConstante()"
									onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#"
									onmouseover="downAction('botonZonaVerde')"
									onmouseout="normalAction('botonZonaVerde')"
									onmousedown="iniciarConteoContinuo('alertaZonaVerde');decrementoConstante()"
									onmouseup="finalizarConteoContinuo()" />
							</map>
							<map name="MapControlUpDown2" id="MapControlUpDown2">
								<area shape="rect" coords="0,0,14,11" href="#"
									onmouseover="upAction('botonZonaAmarilla')"
									onmouseout="normalAction('botonZonaAmarilla')"
									onmousedown="iniciarConteoContinuo('alertaZonaAmarilla');aumentoConstante()"
									onmouseup="finalizarConteoContinuo()" />
								<area shape="rect" coords="0,11,14,20" href="#"
									onmouseover="downAction('botonZonaAmarilla')"
									onmouseout="normalAction('botonZonaAmarilla')"
									onmousedown="iniciarConteoContinuo('alertaZonaAmarilla');decrementoConstante()"
									onmouseup="finalizarConteoContinuo()" />
							</map>
						</vgcinterfaz:panelContenedor>

					</logic:equal>
				</vgcinterfaz:contenedorPaneles>

				<!-- Barra Inferior -->
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					<logic:notEqual name="editarIniciativaForm" property="bloqueado"
						value="true">
						<img
							src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>"
							border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="javascript:guardar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
								key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:notEqual>
					<img
						src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>"
						border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'"
						href="javascript:cancelar();" class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>

		</html:form>

		<%-- Colocar el form en el archivo validation.xml en WEB-INF --%>
		<html:javascript formName="editarIniciativaForm"
			dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript"
			src="<html:rewrite page='/componentes/comunes/staticJavascript.jsp'/>"></script>
		<script type="text/javascript">
			inicializarResultadosEspecificos();
			inicializarCheckboxSincronizadorCamposTexto(
					document.editarIniciativaForm.nombreLargo,
					document.editarIniciativaForm.nombre, 250,
					document.editarIniciativaForm.sincronizarNombres);
			_frecuencia = document.editarIniciativaForm.frecuencia.value;
			if (document.editarIniciativaForm.tipoMedicion[0].checked)
				_tipoMedicion = 0;
			else if (document.editarIniciativaForm.tipoMedicion[1].checked)
				_tipoMedicion = 1;

			function toggle(elemento) {
				if (elemento.value == "1") {
					document.getElementById("partidasPre").style.display = "none";

				} else {
					if (elemento.value == "0") {
						document.getElementById("partidasPre").style.display = "block";

					}
				}
			}
		</script>
	</tiles:put>

</tiles:insert>
