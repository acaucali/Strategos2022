<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Andres Martinez (08/11/2023) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarexplicaciones.titulo" />
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
		
			function nuevo() 
			{
				window.location.href='<html:rewrite action="/explicaciones/crearExplicacionPGN" />?objetoId=' + document.gestionarExplicacionesPGNForm.objetoId.value;
			}

		function modificar() 
		{
			if ((document.gestionarExplicacionesPGNForm.seleccionados.value == null) || (document.gestionarExplicacionesPGNForm.seleccionados.value == "")) 
			{
				alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
				return;
			}			
			var explicacionId = document.gestionarExplicacionesPGNForm.seleccionados.value;						
			window.location.href='<html:rewrite action="/explicaciones/modificarExplicacionPGN" />?explicacionId=' + explicacionId;
			
		}
		
		function eliminar() 
		{
			if ((document.gestionarExplicacionesPGNForm.seleccionados.value == null) || (document.gestionarExplicacionesPGNForm.seleccionados.value == "")) 
			{
				alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
				return;
			}			
			var explicacionId = document.gestionarExplicacionesPGNForm.seleccionados.value;			
			var mensaje = '';
			mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.eliminarexplicacion.confirmar" />';
			
			var eliminar = confirm(mensaje);
			if (eliminar) 
				window.location.href='<html:rewrite action="/explicaciones/eliminarExplicacionPGN"/>?explicacionId=' + explicacionId + '&ts=<%= (new Date()).getTime() %>';
		}
		
		function limpiarFiltros() 
		{
			document.gestionarExplicacionesPGNForm.filtroTitulo.value = "";			  
			document.gestionarExplicacionesPGNForm.action = '<html:rewrite action="/explicaciones/gestionarExplicacionesPGN"/>';
			document.gestionarExplicacionesPGNForm.submit();
		}
		
		function buscar() 
		{
			document.gestionarExplicacionesPGNForm.action = '<html:rewrite action="/explicaciones/gestionarExplicacionesPGN"/>';
			document.gestionarExplicacionesPGNForm.submit();
		}
		
		function actualizar() 
		{
			document.gestionarExplicacionesPGNForm.action = '<html:rewrite action="/explicaciones/gestionarExplicacionesPGN"/>';
			document.gestionarExplicacionesPGNForm.submit();
		}
		
		</script>
				
		
		<%-- Se pasan los valores de los Objetos que estan en "session" o "request" a las propiedades locales --%>
		<bean:define id="totalElementos" name="paginaExplicacionesPGN" property="total" scope="request" toScope="page" />
		<bean:define id="tamanoPagina" name="paginaExplicacionesPGN" property="tamanoPagina" scope="request" toScope="page" />
		<bean:define id="tamanoSetPaginas" name="paginaExplicacionesPGN" property="tamanoSetPaginas" scope="request" toScope="page" />
		<bean:define id="nroPagina" name="paginaExplicacionesPGN" property="nroPagina" scope="request" toScope="page" />
		
		
		
		<%-- Inclusión de los JavaScript externos a esta página --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>
		
		<%-- Representación de la Forma --%>
		<html:form action="/explicaciones/gestionarExplicacionesPGN" styleClass="formaHtml">
		
			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />	
			<html:hidden property="valoresSeleccionados" />		
			<html:hidden property="objetoId" />			
			
			
			<vgcinterfaz:contenedorForma idContenedor="body-explicaciones">
				
				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..::<vgcutil:message key="jsp.gestionarexplicaciones.titulo" />			
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar()
				</vgcinterfaz:contenedorFormaBotonActualizar>
				
				<%-- Botón Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
					javascript:irAtras(2)
				</vgcinterfaz:contenedorFormaBotonRegresar>
				
				<%-- Menú --%>
				<vgcinterfaz:contenedorFormaBarraMenus>
					
					<%-- Inicio del Menú --%>
					<vgcinterfaz:contenedorMenuHorizontal>
					
						<%-- Menú: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />															
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>		
						
						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">								
									<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" />								
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
									<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" />															
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>					
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
							</vgcinterfaz:menuBotones>	
						</vgcinterfaz:contenedorMenuHorizontalItem>
					
					</vgcinterfaz:contenedorMenuHorizontal>
					
				</vgcinterfaz:contenedorFormaBarraMenus>
				
				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
				<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">						
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.gestionarexplicaciones.organizacion" /></b></td>
							<td><bean:write name="gestionarExplicacionesPGNForm" property="nombreOrganizacion" /></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.gestionarexplicaciones.objeto" /></b></td>
							<td><bean:write name="gestionarExplicacionesPGNForm" property="nombreObjetoKey" /></td>
						</tr>
					</table>

					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="30px"><vgcutil:message key="jsp.gestionarexplicaciones.columna.titulo" /></td>
							<td width="100px"><html:text property="filtroTitulo" styleClass="cuadroTexto" size="50" maxlength="100" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10"
								title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;"
								width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td colspan="4">&nbsp;</td>
						</tr>
					</table>
					
					<%-- Barra de Herramientas --%>	
					<vgcinterfaz:barraHerramientas nombre="barraGestionarExplicaciones">
											
							<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.nuevo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
												
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
																															
							<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.eliminar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
																																			
					</vgcinterfaz:barraHerramientas>
				</vgcinterfaz:contenedorFormaBarraGenerica>
																			
				
				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="5" cellspacing="1" id="tablaExplicaciones">
				
					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">
					
						<td align="center" width="10%" onClick="javascript:consultar(gestionarExplicacionesPGNForm, 'creadoId', null);" style="cursor: pointer"
							onMouseOver="this.className='mouseEncimaEncabezadoListView'" onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message
							key="jsp.gestionarexplicaciones.columna.autor" /><img name="creadoId" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10"
							title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
							
						<td align="center" width="10%" onClick="javascript:consultar(gestionarExplicacionesPGNForm, 'fecha', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.fecha" /><img name="fecha"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
							
						<td align="center" width="50%" onClick="javascript:consultar(gestionarExplicacionesPGNForm, 'titulo', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.titulo" /><img name="titulo"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="10%" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.cumpliendo.fechas" /><img name="fecha"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
												
							<td align="center" width="10%" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.recibido" /><img name="fecha"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>														
					</tr>
					
					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="paginaExplicacionesPGN" property="lista" scope="request" id="explicacion">
						<tr onclick="eventoClickFilaV2(document.gestionarExplicacionesPGNForm.seleccionados, document.gestionarExplicacionesPGNForm.valoresSeleccionados, 'tablaExplicaciones', this)" id="<bean:write name="explicacion" property="explicacionId" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaV2(document.gestionarExplicacionesPGNForm.seleccionados, this)" onMouseOut="eventoMouseFueraFilaV2(document.gestionarExplicacionesPGNForm.seleccionados, this)" height="20px">
						
							<td><bean:write name="explicacion" property="usuarioCreado.fullName" /></td>
							
							<td align="center"><bean:write name="explicacion" property="fechaFormateada" /></td>
							
							<td><bean:write name="explicacion" property="titulo" /></td>

							<logic:equal name="explicacion" property="cumplimiendoFechas" value="true">
								<td align="center">Si</td>					
							</logic:equal>
							<logic:notEqual name="explicacion" property="cumplimiendoFechas" value="true">
								<td align="center">No</td>					
							</logic:notEqual>							

							
							
							<logic:equal name="explicacion" property="recibido" value="true">
								<td align="center">Si</td>					
							</logic:equal>
							<logic:notEqual name="explicacion" property="recibido" value="true">
								<td align="center">No</td>					
							</logic:notEqual>
							
												
							
						</tr>
					</logic:iterate>
					
					<%-- Validación cuando no hay registros --%>
					<logic:equal name="paginaExplicacionesPGN" property="total" value="0" scope="request">
						<tr class="mouseFueraCuerpoListView" id="0" height="20px">
							<td valign="middle" align="center" colspan="4">
								<vgcutil:message key="jsp.gestionarexplicaciones.noregistros" />
							</td>
						</tr>
					</logic:equal>
				</table>
				
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager resultSize="<%=totalElementos%>" pageSize="<%=tamanoPagina%>" page="<%=nroPagina%>" labelPage="inPagina"
						action="javascript:consultar(gestionarExplicacionesPGNForm, null, inPagina)" styleClass="paginador" pagesSetSize="<%=tamanoSetPaginas%>" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>					
			</vgcinterfaz:contenedorForma>		
			
			<script>

				// Invoca la función que hace el ordenamiento de las columnas
				actualizarSeleccionadosV2(gestionarExplicacionesPGNForm.seleccionados, 'tablaExplicaciones');
				
				//Invoca la función que hace el ordenamiento de las columnas
				cambioImagenOrden(gestionarExplicacionesPGNForm);
				// Arma la descripción al final de la lista
				var barraInferior = document.getElementById('barraInferior');
				var atributoOrden = gestionarExplicacionesPGNForm.atributoOrden.value;				
				if (atributoOrden == "creadoId") atributoOrden  = "<vgcutil:message key='jsp.gestionarexplicaciones.columna.autor' />";
				if (atributoOrden == "fecha") atributoOrden  = "<vgcutil:message key='jsp.gestionarexplicaciones.columna.fecha' />";
				if (atributoOrden == "titulo") atributoOrden  = "<vgcutil:message key='jsp.gestionarexplicaciones.columna.titulo' />";
				if ((atributoOrden != null) && (atributoOrden != "")) {
					barraInferior.innerHTML = "<b><vgcutil:message key='jsp.gestionarlista.ordenado' /></b>: " + atributoOrden.toLowerCase() + " [" + gestionarExplicacionesForm.tipoOrden.value.toLowerCase() + "]";
				}

				

			</script>				
		</html:form>
		<script type="text/javascript">
			resizeAlto(document.getElementById('body-explicaciones'), 340);	

			var visor = document.getElementById('tablaExplicaciones');
			if (visor != null)
			{
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
				visor.style.height = "100%";
			}
		</script>
		
	</tiles:put>

</tiles:insert>