<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>

<%@ page import="java.util.Date"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- @Author: Kerwin Arias (21/01/2012) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">
		<logic:equal name="editarPerspectivaForm" property="tipo" value="0">
			<vgcutil:message key="jsp.copiarperspectiva.titulo" />
		</logic:equal>
		<logic:equal name="editarPerspectivaForm" property="tipo" value="1">
			<vgcutil:message key="jsp.copiarperspectiva.tituloObjetivo" />
		</logic:equal>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript para el wizzard --%>
		<script type="text/javascript">
		
			var pasoActual = 1;
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (pasoActual) 
				{
					case 1:
						break;
					case 2:
						break;
					case 3:
						//break;
					case 4:
						copiar();
						break;
				}
				if (valido) 
				{
					pasoActual = pasoActual + 1;
					mostrarBotones(pasoActual);
				}
				mostrarTitulo();
			}
			
			function previo() 
			{
				pasoActual = pasoActual - 1;
				mostrarBotones(pasoActual);
				mostrarTitulo();
			}

			function crearBoton(nombreBoton, accionBoton)
			{
				var boton = '<a onMouseOver=\"this.className=\'mouseEncimaBarraInferiorForma\'\"'
					+ ' onMouseOut=\"this.className=\'mouseFueraBarraInferiorForma\'\"'
					+ ' href=\"' + accionBoton + '\"'
					+ ' class=\"mouseFueraBarraInferiorForma\" >'
					+ nombreBoton + '</a>';
			
				return boton;
			}
		
			function mostrarBotones(paso) 
			{
			
				var botones = "";
				var separacion = "&nbsp;&nbsp;&nbsp;&nbsp;";
				var nombreBotonPrevio = '<vgcutil:message key="boton.previo.alt" />';
				var accionBotonPrevio = 'javascript:previo();';
				var nombreBotonSiguiente = '<vgcutil:message key="boton.siguiente.alt" />';
				var accionBotonSiguiente = 'javascript:siguiente();';
				var nombreBotonCancelar = '<vgcutil:message key="boton.cancelar.alt" />';
				var accionBotonCancelar = 'javascript:cancelar();';
				var nombreBotonFinalizar = '<vgcutil:message key="boton.finalizar.alt" />';
				var accionBotonFinalizar = 'javascript:siguiente();';
			
				switch (paso) 
				{
					case 1:
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;  
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelPresentacion" />
					break;
					case 2:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
					botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelRango" />
					break;
					case 3:
					//botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					//botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
					//botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					//<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelAlcance" />
					//break;
					case 4:
					botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
					botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
					botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
					<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesCopiar" nombrePanel="panelLog" />
					break;
				}
				
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}
			
			function mostrarTitulo() 
			{
				var titulo = (document.editarPerspectivaForm.nombre.value == "1" ? '..:: <vgcutil:message key="jsp.copiarperspectiva.titulo1" /> ' : '..:: <vgcutil:message key="jsp.copiarperspectiva.titulo1Objetivo" /> ') + pasoActual + ' <vgcutil:message key="jsp.copiarorganizacion.titulo2To3" />';
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;

				celda = document.getElementById("mensajeFinal");
				celda.innerHTML = '<vgcutil:message key="jsp.copiarorganizacion.finalizarasistente" />' + ' "' + document.editarPerspectivaForm.nombre.value + '" ' + '<vgcutil:message key="jsp.copiarorganizacion.finalizarasistente3" />' + ' "' + document.editarPerspectivaForm.nuevoNombre.value + '"' + '<vgcutil:message key="jsp.copiarorganizacion.finalizarasistente4" />';
			}
			
			function validar() 
			{
				if (window.document.editarPerspectivaForm.nuevoNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.editarorganizacion.alert.nombre.invalido.vacio" /> ');
					return false;
				}
				if (window.document.editarPerspectivaForm.nuevoNombre.value == document.editarPerspectivaForm.nombre.value)
				{
					alert('<vgcutil:message key="jsp.editarorganizacion.alert.nombre.invalido.igualalafuente" /> ');
					return false;
				}
				return true;
			}
			
			function cancelar() 
			{
				window.document.editarPerspectivaForm.action = '<html:rewrite action="/planes/perspectivas/copiarCancelarPerspectiva"/>?cancelar=true';
				window.document.editarPerspectivaForm.submit();			
			}

			function copiar() 
			{
				if (validar())
				{
					var xml = '?showPresentacion=' + (document.editarPerspectivaForm.showPresentacion.checked ? 1 : 0);
					
					document.editarPerspectivaForm.action= '<html:rewrite action="/planes/perspectivas/copiarGuardarPerspectiva" />' + xml + '&ts=<%= (new Date()).getTime() %>';
					document.editarPerspectivaForm.submit();
				}
				else
				{
					pasoActual = 3;
					mostrarBotones(pasoActual);
					mostrarTitulo();
				}
			}

		</script>

		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/planes/perspectivas/copiarLeerPerspectiva">

			<%-- Id de las tablas --%>
			<html:hidden property="perspectivaId" />
			<html:hidden property="padreId" />
			<html:hidden property="tipo" />
			<input type="hidden" name="copiaNombre">

			<vgcinterfaz:contenedorForma width="570px" height="400px" bodyAlign="center" bodyValign="center" >
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
				<table class="bordeFichaDatos" width="100%" border="0" cellspacing="0" cellpadding="3">
					<tr height=315px>

						<%-- Im�gen del asistente --%>
						<td width="315px">
							<img src="<html:rewrite page='/paginas/strategos/planes/perspectivas/imagenes/wzcopiar.gif'/>" border="0" width="150px" height="310px">
						</td>

						<td><vgcinterfaz:contenedorPaneles height="270px" width="390px" nombre="contenedorPanelesCopiar" mostrarSelectoresPaneles="false">

							<%-- Panel Presentacion --%>
							<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
								<table class="tablaSpacing0Padding0Width100Collapse">
									<tr>
										<td>
											<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
												<tr>
													<td>
														<b><vgcutil:message key="jsp.editarorganizacion.paso1.texto1" /></b></br></br> 
														<vgcutil:message key="jsp.copiarorganizacion.presentacion" /></br></br>
														<vgcutil:message key="jsp.copiarorganizacion.presentacion1" />
													</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<br><br><br>
											<br><br><br>
											<br><br><br><br>
										</td>
									</tr>
									<tr>
										<td class="contenedorTextoSeleccion">
											<html:checkbox property="showPresentacion" styleClass="botonSeleccionMultiple">
												<vgcutil:message key="jsp.copiarorganizacion.presentacion.mostrar" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Nombre --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelRango" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<logic:equal name="editarPerspectivaForm" property="tipo" value="0">
											<td colspan="2"><b><vgcutil:message key="jsp.copiarperspectiva.nombreCopia" /></b></td>
										</logic:equal>
										<logic:equal name="editarPerspectivaForm" property="tipo" value="1">
											<td colspan="2"><b><vgcutil:message key="jsp.copiarperspectiva.nombreCopiaObjetivo" /></b></td>
										</logic:equal>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td align="left">
											<logic:equal name="editarPerspectivaForm" property="tipo" value="0">
												<vgcutil:message key="jsp.copiarperspectiva.fuente" />
											</logic:equal>
											<logic:equal name="editarPerspectivaForm" property="tipo" value="1">
												<vgcutil:message key="jsp.copiarperspectiva.fuenteObjetivo" />
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td align="left">
											<html:text property="nombre" styleClass="cuadroTexto" size="46" readonly="true" ></html:text>
										</td>
									</tr>
									<tr>
										<td align="left">
											<logic:equal name="editarPerspectivaForm" property="tipo" value="0">
												<vgcutil:message key="jsp.copiarperspectiva.destino" />
											</logic:equal>
											<logic:equal name="editarPerspectivaForm" property="tipo" value="1">
												<vgcutil:message key="jsp.copiarperspectiva.destinoObjetivo" />
											</logic:equal>
										</td>
									</tr>
									<tr>
										<td align="left">
											<vgcutil:message key="jsp.copiarorganizacion.nuevonombre" />
										</td>
									</tr>
									<tr>
										<td> 
											<html:text property="nuevoNombre" styleClass="cuadroTexto" size="46"></html:text>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel seleccion de indicadores --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelAlcance" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%" border="0">
									<tr>
										<td align="left">
											<vgcutil:message key="jsp.copiarorganizacion.seleccionarcomponentes" />
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;
											<html:checkbox property="copiarArbol" styleClass="botonSeleccionMultiple" >
												<vgcutil:message key="jsp.copiarorganizacion.copiararbol" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;
											<html:checkbox property="copiarMediciones" styleClass="botonSeleccionMultiple" >
												<vgcutil:message key="jsp.copiarorganizacion.copiarmediciones" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;
											<html:checkbox property="copiarPlantillasGraficos" styleClass="botonSeleccionMultiple" >
												<vgcutil:message key="jsp.copiarorganizacion.copiarplantillasgraficos" />
											</html:checkbox>
										</td>
									</tr>
									<tr>
										<td>&nbsp;&nbsp;
											<html:checkbox property="copiarPlantillasReportes" styleClass="botonSeleccionMultiple" >
												<vgcutil:message key="jsp.copiarorganizacion.copiarplantillasreportes" />
											</html:checkbox>
										</td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

							<%-- Panel Finalizar --%>
							<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelLog" mostrarBorde="false">
								<table class="contenedorBotonesSeleccion" cellpadding="3" cellspacing="3" width="100%">
									<tr>
										<td><span id="mensajeFinal"></span></td>
									</tr>
								</table>
							</vgcinterfaz:panelContenedor>

						</vgcinterfaz:contenedorPaneles></td>
					</tr>
				</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>

		<html:javascript formName="editarPerspectivaForm" dynamicJavascript="true" staticJavascript="false" />
		<script type="text/javascript" src="<html:rewrite page='/paginas/strategos/calculos/calculosJs/staticJavascript.jsp'/>"></script>

		<script type="text/javascript">
			if (document.editarPerspectivaForm.showPresentacion.checked)
				pasoActual = 2;
			mostrarTitulo();
			mostrarBotones(pasoActual);
			document.editarPerspectivaForm.nuevoNombre.value = '<vgcutil:message key="jsp.editarorganizacion.nuevonombreCopia" /> ' + '<bean:write name="editarPerspectivaForm" property="nombre" />';
		</script>
	</tiles:put>
</tiles:insert>
