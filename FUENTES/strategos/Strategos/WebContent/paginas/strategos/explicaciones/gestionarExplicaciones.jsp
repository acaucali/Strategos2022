<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Kerwin Arias (23/01/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">
		<logic:equal name="gestionarExplicacionesForm" property="tipo" value="0">
			<vgcutil:message key="jsp.gestionarexplicaciones.titulo" />
		</logic:equal>
		<logic:equal name="gestionarExplicacionesForm" property="tipo" value="1">
			<vgcutil:message key="jsp.gestionarexplicaciones.informes.cualitativos.titulo" />
		</logic:equal>
		<logic:equal name="gestionarExplicacionesForm" property="tipo" value="2">
			<vgcutil:message key="jsp.gestionarexplicaciones.informes.ejecutivos.titulo" />
		</logic:equal>
		<logic:equal name="gestionarExplicacionesForm" property="tipo" value="3">
			<vgcutil:message key="jsp.gestionarexplicaciones.eventos.titulo" />
		</logic:equal>
		<logic:equal name="gestionarExplicacionesForm" property="tipo" value="4">
			<vgcutil:message key="jsp.gestionarexplicaciones.noticia.titulo" />
		</logic:equal>
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script type="text/javascript">

			function nuevo() 
			{
				window.location.href='<html:rewrite action="/explicaciones/crearExplicacion" />?tipo=' + document.gestionarExplicacionesForm.tipo.value;
			}
	
			function modificar() 
			{
				if ((document.gestionarExplicacionesForm.seleccionados.value == null) || (document.gestionarExplicacionesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var explicacionId = document.gestionarExplicacionesForm.seleccionados.value;
				
				<logic:equal name="gestionarExplicacionesForm" property="editarForma" value="true">
					window.location.href='<html:rewrite action="/explicaciones/modificarExplicacion" />?explicacionId=' + explicacionId + '&tipo=' + document.gestionarExplicacionesForm.tipo.value;
				</logic:equal>
				<logic:notEqual name="gestionarExplicacionesForm" property="editarForma" value="true">
					<logic:equal name="gestionarExplicacionesForm" property="verForma" value="true">
						window.location.href='<html:rewrite action="/explicaciones/verExplicacion" />?explicacionId=' + explicacionId + '&tipo=' + document.gestionarExplicacionesForm.tipo.value;
					</logic:equal>
				</logic:notEqual>
			}
	
			function eliminar() 
			{
				if ((document.gestionarExplicacionesForm.seleccionados.value == null) || (document.gestionarExplicacionesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				var explicacionId = document.gestionarExplicacionesForm.seleccionados.value;
				var mensaje = '';
				<logic:equal name="gestionarExplicacionesForm" property="tipo" value="0">
					mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.eliminarexplicacion.confirmar" />';
				</logic:equal>
				<logic:equal name="gestionarExplicacionesForm" property="tipo" value="1">
					mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.informes.cualitativos.eliminarexplicacion.confirmar" />';
				</logic:equal>
				<logic:equal name="gestionarExplicacionesForm" property="tipo" value="2">
					mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.informes.ejecutivos.eliminarexplicacion.confirmar" />';
				</logic:equal>
				<logic:equal name="gestionarExplicacionesForm" property="tipo" value="3">
					mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.eventos.eliminarexplicacion.confirmar" />';
				</logic:equal>
				<logic:equal name="gestionarExplicacionesForm" property="tipo" value="4">
					mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.noticia.eliminarexplicacion.confirmar" />';
				</logic:equal>
				<logic:equal name="gestionarExplicacionesForm" property="tipo" value="5">
					mensaje = '<vgcutil:message key="jsp.gestionarexplicaciones.eliminarexplicacion.confirmar" />';
				</logic:equal>
				
				var eliminar = confirm(mensaje);
				if (eliminar) 
					window.location.href='<html:rewrite action="/explicaciones/eliminarExplicacion"/>?explicacionId=' + explicacionId + '&ts=<%= (new Date()).getTime() %>';
			}
			
			function propiedades() 
			{
				if ((document.gestionarExplicacionesForm.seleccionados.value == null) || (document.gestionarExplicacionesForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				var explicacionId = document.gestionarExplicacionesForm.seleccionados.value;
		    	abrirVentanaModal('<html:rewrite action="/explicaciones/propiedadesExplicacion" />?explicacionId=' + explicacionId, "Explicacion", 450, 470);			
			}
	
			function limpiarFiltros() 
			{
				document.gestionarExplicacionesForm.filtroTitulo.value = "";			  
				document.gestionarExplicacionesForm.action = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>?tipo=' + document.gestionarExplicacionesForm.tipo.value;
				document.gestionarExplicacionesForm.submit();
			}
	
			function buscar() 
			{
				document.gestionarExplicacionesForm.action = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>?tipo=' + document.gestionarExplicacionesForm.tipo.value;
				document.gestionarExplicacionesForm.submit();
			}

			function actualizar() 
			{
				document.gestionarExplicacionesForm.action = '<html:rewrite action="/explicaciones/gestionarExplicaciones"/>?tipo=' + document.gestionarExplicacionesForm.tipo.value;
				document.gestionarExplicacionesForm.submit();
			}
			
			function reporteExplicaciones(tipo)
			{
				var url = "?tipo=" + document.gestionarExplicacionesForm.tipo.value;
				url = url + "&titulo=" + document.gestionarExplicacionesForm.filtroTitulo.value;
				url = url + "&objetoId=" + document.gestionarExplicacionesForm.objetoId.value;
				url = url + "&objetoKey=" + document.gestionarExplicacionesForm.tipoObjetoKey.value;
				
				if (tipo == 0)
					abrirReporte('<html:rewrite action="/explicaciones/generarReporteExplicacionesPdf"/>' + url);
				else
					abrirReporte('<html:rewrite action="/explicaciones/generarReporteExplicacionesXls"/>' + url);
			}
			
			function reporteExplicacionesInstrumentoPdf() 
			{
				abrirReporte('<html:rewrite action="/reportes/explicaciones/instrumentos/ejecucionPdf"/>?instrumentoId=' + document.gestionarExplicacionesForm.objetoId.value, 'explicacionesInstrumento', '1050', '850');
			}
			
			function reporteExplicacionesInstrumentoExcel() 
			{
				window.location.href='<html:rewrite action="/reportes/explicaciones/instrumentos/ejecucionXls"/>?instrumentoId=' + document.gestionarExplicacionesForm.objetoId.value;	
			}

		</script>

		<%-- Se pasan los valores de los Objetos que estan en "session" o "request" a las propiedades locales --%>
		<bean:define id="totalElementos" name="paginaExplicaciones" property="total" scope="request" toScope="page" />
		<bean:define id="tamanoPagina" name="paginaExplicaciones" property="tamanoPagina" scope="request" toScope="page" />
		<bean:define id="tamanoSetPaginas" name="paginaExplicaciones" property="tamanoSetPaginas" scope="request" toScope="page" />
		<bean:define id="nroPagina" name="paginaExplicaciones" property="nroPagina" scope="request" toScope="page" />

		<%-- Inclusi�n de los JavaScript externos a esta p�gina --%>
		<jsp:include flush="true" page="/componentes/visorLista/visorListaJs.jsp"></jsp:include>
		<jsp:include flush="true" page="/paginas/strategos/menu/menuHerramientasJs.jsp"></jsp:include>

		<%-- Representaci�n de la Forma --%>
		<html:form action="/explicaciones/gestionarExplicaciones" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="tipo" />
			<html:hidden property="objetoId" />
			<html:hidden property="tipoObjetoKey" />
			<html:hidden property="desdeInstrumento" />

			<vgcinterfaz:contenedorForma idContenedor="body-explicaciones">

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>
						..::
						<logic:equal name="gestionarExplicacionesForm" property="tipo" value="0">
							<vgcutil:message key="jsp.gestionarexplicaciones.titulo" />
						</logic:equal>
						<logic:equal name="gestionarExplicacionesForm" property="tipo" value="1">
							<vgcutil:message key="jsp.gestionarexplicaciones.informes.cualitativos.titulo" />
						</logic:equal>
						<logic:equal name="gestionarExplicacionesForm" property="tipo" value="2">
							<vgcutil:message key="jsp.gestionarexplicaciones.informes.ejecutivos.titulo" />
						</logic:equal>
						<logic:equal name="gestionarExplicacionesForm" property="tipo" value="3">
							<vgcutil:message key="jsp.gestionarexplicaciones.eventos.titulo" />
						</logic:equal>
						<logic:equal name="gestionarExplicacionesForm" property="tipo" value="4">
							<vgcutil:message key="jsp.gestionarexplicaciones.noticia.titulo" />
						</logic:equal>
						<logic:equal name="gestionarExplicacionesForm" property="tipo" value="5">
							<vgcutil:message key="jsp.gestionarexplicaciones.titulo" />
						</logic:equal>
						 - <bean:write name="gestionarExplicacionesForm" property="tipoObjetoKey" /> (<bean:write name="gestionarExplicacionesForm" property="nombreObjetoKey" />)						
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Bot�n Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar()
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Bot�n Regresar --%>
				<vgcinterfaz:contenedorFormaBotonRegresar>
				
					<logic:equal name="gestionarExplicacionesForm" property="desdeInstrumento" value="true">
						javascript:irAtras(3)
					</logic:equal>
					
					<logic:notEqual name="gestionarExplicacionesForm" property="desdeInstrumento" value="true">
						javascript:irAtras(2)
					</logic:notEqual>
				
				</vgcinterfaz:contenedorFormaBotonRegresar>

				<%-- Men� --%>
				<vgcinterfaz:contenedorFormaBarraMenus>
					
					<%-- Inicio del Men� --%>
					<vgcinterfaz:contenedorMenuHorizontal>										
					
						<%-- Men�: Archivo --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" />
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporteExplicaciones(0);" permisoId="" agregarSeparador="true"/>
								<%--
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="" agregarSeparador="true"/>
								<vgcinterfaz:menuAnidado key="menu.archivo.presentacionpreliminar" agregarSeparador="true">
									<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar.pdf" permisoId="" aplicaOrganizacion="true" onclick="reporteExplicaciones(0);" />
									<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar.xls" permisoId="" aplicaOrganizacion="true" onclick="reporteExplicaciones(1);" />
								</vgcinterfaz:menuAnidado>
								--%>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>		
						
						<%-- Men�: Edici�n --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<logic:equal name="gestionarExplicacionesForm" property="addForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.nuevo" onclick="nuevo();" />
								</logic:equal>
								<logic:equal name="gestionarExplicacionesForm" property="editarForma" value="true">						
									<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
								</logic:equal>
								<logic:notEqual name="gestionarExplicacionesForm" property="editarForma" value="true">
									<logic:equal name="gestionarExplicacionesForm" property="verForma" value="true">
										<vgcinterfaz:botonMenu key="menu.edicion.modificar" onclick="modificar();" />
									</logic:equal>
								</logic:notEqual>
								<logic:equal name="gestionarExplicacionesForm" property="deleteForma" value="true">
									<vgcinterfaz:botonMenu key="menu.edicion.eliminar" onclick="eliminar();" agregarSeparador="true" />
								</logic:equal>
								<vgcinterfaz:botonMenu key="menu.edicion.propiedades" onclick="propiedades();" permisoId="EXPLICACION" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Men�: Herramientas --%>				
						<vgcinterfaz:contenedorMenuHorizontalItem>	
							<vgcinterfaz:menuBotones id="menuHerramientas" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.cambioclave" onclick="editarClave();" permisoId="" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.herramientas.opciones" onclick="editarMensajeEmail();" permisoId="SEGUIMIENTO_MENSAJE" />
								<%-- 
								<vgcinterfaz:botonMenu key="menu.herramientas.configurar.sistema" onclick="configurarSistema();" permisoId="CONFIGURACION_SISTEMA" />
								--%>
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Men�: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>					
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" />
							</vgcinterfaz:menuBotones>	
						</vgcinterfaz:contenedorMenuHorizontalItem>
					
					</vgcinterfaz:contenedorMenuHorizontal>	
					
				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Gen�rica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="100px"><b><vgcutil:message key="jsp.gestionarexplicaciones.organizacion" /></b></td>
							<td><bean:write name="gestionarExplicacionesForm" property="nombreOrganizacion" /></td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><b><vgcutil:message key="jsp.gestionarexplicaciones.objeto" /></b></td>
							<td><bean:write name="gestionarExplicacionesForm" property="tipoObjetoKey" /> (<bean:write name="gestionarExplicacionesForm" property="nombreObjetoKey" />)</td>
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
						<logic:equal name="gestionarExplicacionesForm" property="addForma" value="true">						
							<vgcinterfaz:barraHerramientasBoton nombreImagen="nuevo" pathImagenes="/componentes/barraHerramientas/" nombre="nuevo" onclick="javascript:nuevo();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.nuevo" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:equal name="gestionarExplicacionesForm" property="editarForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.modificar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<logic:notEqual name="gestionarExplicacionesForm" property="editarForma" value="true">
							<logic:equal name="gestionarExplicacionesForm" property="verForma" value="true">
								<vgcinterfaz:barraHerramientasBoton nombreImagen="modificar" pathImagenes="/componentes/barraHerramientas/" nombre="modificar" onclick="javascript:modificar();">
									<vgcinterfaz:barraHerramientasBotonTitulo>
										<vgcutil:message key="menu.edicion.modificar" />
									</vgcinterfaz:barraHerramientasBotonTitulo>
								</vgcinterfaz:barraHerramientasBoton>
							</logic:equal>
						</logic:notEqual>
						<logic:equal name="gestionarExplicacionesForm" property="deleteForma" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="eliminar" pathImagenes="/componentes/barraHerramientas/" nombre="eliminar" onclick="javascript:eliminar();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.edicion.eliminar" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
						<vgcinterfaz:barraHerramientasSeparador />
						<vgcinterfaz:barraHerramientasBoton nombreImagen="propiedades" pathImagenes="/componentes/barraHerramientas/" nombre="propiedades" onclick="javascript:propiedades();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="menu.edicion.propiedades" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						
						<logic:equal name="gestionarExplicacionesForm" property="desdeInstrumento" value="true">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfResumido" onclick="javascript:reporteExplicacionesInstrumentoPdf();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar.resumida" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
							
							<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:reporteExplicacionesInstrumentoExcel();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.usuarios.reportes.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</logic:equal>
											
					</vgcinterfaz:barraHerramientas>

				</vgcinterfaz:contenedorFormaBarraGenerica>

				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="5" cellspacing="1" id="tablaExplicaciones">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">						

						<td align="center" width="30%" onClick="javascript:consultar(gestionarExplicacionesForm, 'creadoId', null);" style="cursor: pointer"
							onMouseOver="this.className='mouseEncimaEncabezadoListView'" onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message
							key="jsp.gestionarexplicaciones.columna.autor" /><img name="creadoId" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10"
							title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="10%" onClick="javascript:consultar(gestionarExplicacionesForm, 'fecha', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.fecha" /><img name="fecha"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<td align="center" width="50%" onClick="javascript:consultar(gestionarExplicacionesForm, 'titulo', null);" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.titulo" /><img name="titulo"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>

						<%--<td align="center" width="10%" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.gestionarexplicaciones.columna.adjuntos" /></td>--%>
					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="paginaExplicaciones" property="lista" scope="request" id="explicacion">

						<tr onclick="eventoClickFilaV2(document.gestionarExplicacionesForm.seleccionados, document.gestionarExplicacionesForm.valoresSeleccionados, 'tablaExplicaciones', this)" id="<bean:write name="explicacion" property="explicacionId" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaV2(document.gestionarExplicacionesForm.seleccionados, this)" onMouseOut="eventoMouseFueraFilaV2(document.gestionarExplicacionesForm.seleccionados, this)" height="20px">
						
							<td><bean:write name="explicacion" property="usuarioCreado.fullName" /></td>

							<td align="center"><bean:write name="explicacion" property="fechaFormateada" /></td>

							<td><bean:write name="explicacion" property="titulo" /></td>

							<%-- <td align="center"><bean:write name="explicacion" property="numeroAdjuntos" /></td>--%>
							
						</tr>

					</logic:iterate>

					<%-- Validaci�n cuando no hay registros --%>
					<logic:equal name="paginaExplicaciones" property="total" value="0" scope="request">
						<tr class="mouseFueraCuerpoListView" id="0" height="20px">
							<td valign="middle" align="center" colspan="4">
								<logic:equal name="gestionarExplicacionesForm" property="tipo" value="0">
									<vgcutil:message key="jsp.gestionarexplicaciones.noregistros" />
								</logic:equal>
								<logic:equal name="gestionarExplicacionesForm" property="tipo" value="1">
									<vgcutil:message key="jsp.gestionarexplicaciones.informes.cualitativos.noregistros" />
								</logic:equal>
								<logic:equal name="gestionarExplicacionesForm" property="tipo" value="2">
									<vgcutil:message key="jsp.gestionarexplicaciones.informes.ejecutivos.noregistros" />
								</logic:equal>
								<logic:equal name="gestionarExplicacionesForm" property="tipo" value="3">
									<vgcutil:message key="jsp.gestionarexplicaciones.eventos.noregistros" />
								</logic:equal>
								<logic:equal name="gestionarExplicacionesForm" property="tipo" value="4">
									<vgcutil:message key="jsp.gestionarexplicaciones.noticia.noregistros" />
								</logic:equal>
								<logic:equal name="gestionarExplicacionesForm" property="tipo" value="5">
									<vgcutil:message key="jsp.gestionarexplicaciones.noregistros" />
								</logic:equal>
							</td>
						</tr>
					</logic:equal>

				</table>

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager resultSize="<%=totalElementos%>" pageSize="<%=tamanoPagina%>" page="<%=nroPagina%>" labelPage="inPagina"
						action="javascript:consultar(gestionarExplicacionesForm, null, inPagina)" styleClass="paginador" pagesSetSize="<%=tamanoSetPaginas%>" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>

			<script>

				// Invoca la funci�n que hace el ordenamiento de las columnas
				actualizarSeleccionadosV2(gestionarExplicacionesForm.seleccionados, 'tablaExplicaciones');
				
				//Invoca la funci�n que hace el ordenamiento de las columnas
				cambioImagenOrden(gestionarExplicacionesForm);

				// Arma la descripci�n al final de la lista
				var barraInferior = document.getElementById('barraInferior');
				var atributoOrden = gestionarExplicacionesForm.atributoOrden.value;				
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
