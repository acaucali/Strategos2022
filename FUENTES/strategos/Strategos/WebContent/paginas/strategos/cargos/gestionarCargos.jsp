<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
<%@page import="java.util.Date"%>

<%-- Modificado por: Andres Martinez (22/06/2023) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.gestionarcargos.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la página Jsp --%>
		<script type="text/javascript">
			
			function nuevo() 
			{				
				window.location.href='<html:rewrite action="/cargos/crearCargo"/>';
			}
			
			function modificar(cargoId) 
			{
				if ((document.gestionarCargosForm.seleccionados.value == null) || (document.gestionarCargosForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				var cargoId = document.gestionarCargosForm.seleccionados.value;				
				window.location.href='<html:rewrite action="/cargos/modificarCargo"/>?cargoId=' + cargoId;
			}
			
			function eliminar(cargoId){
				if ((document.gestionarCargosForm.seleccionados.value == null) || (document.gestionarCargosForm.seleccionados.value == "")) 
				{
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}			
				var cargoId = document.gestionarCargosForm.seleccionados.value;	
				var eliminar = confirm ('<vgcutil:message key="jsp.gestionarcategoriasmedicion.eliminarcategoriamedicion.confirmar" />');		
				if (eliminar)
					window.location.href='<html:rewrite action="/cargos/eliminarCargo"/>?cargoId=' + cargoId + '&ts=<%= (new Date()).getTime() %>';
			}
			
			function limpiarFiltros() 
			{
				gestionarCargosForm.filtroNombre.value = "";
				gestionarCargosForm.submit();
			}
			
			function buscar() 
			{
				gestionarCargosForm.submit();
			}
		</script>

		<%-- Representación de la Forma --%>
		<html:form action="/cargos/gestionarCargos" styleClass="formaHtml">


			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />

			<vgcinterfaz:contenedorForma idContenedor="body-categorias">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>
					..:: <vgcutil:message key="jsp.gestionarcargos.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Botón Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					<html:rewrite action='/cargos/gestionarCargos' />
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
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina"
									onclick="prepararPagina();" permisoId="" />
								<vgcinterfaz:botonMenu key="menu.archivo.salir"
									onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Edición --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuEdicion" key="menu.edicion">
								<vgcinterfaz:botonMenu key="menu.edicion.nuevo"
									onclick="nuevo();" permisoId="CATEGORIA_ADD" />
								<vgcinterfaz:botonMenu key="menu.edicion.modificar"
									onclick="modificar();" permisoId="CATEGORIA_EDIT" />
								<vgcinterfaz:botonMenu key="menu.edicion.eliminar"
									onclick="eliminar();" permisoId="CATEGORIA_DELETE" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

						<%-- Menú: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual"
									onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca"
									onclick="acerca();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>

					</vgcinterfaz:contenedorMenuHorizontal>

				</vgcinterfaz:contenedorFormaBarraMenus>

				<%-- Barra Genérica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<%-- Filtros --%>
					<table widht="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.gestionarcargos.columna.nombre" /></td>
							<td width="10px"><html:text property="filtroNombre" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><img src="<html:rewrite page='/componentes/formulario/buscar.gif'/>" border="0" onclick="buscar()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.buscar.alt" />"> <img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarFiltros()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />"></td>
            				<td>&nbsp;</td>
						</tr>
					</table>
					
					<%-- Barra de Herramientas --%>
				    <vgcinterfaz:barraHerramientas nombre="barraGestionarCargos">
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
				<vgcinterfaz:visorLista namePaginaLista="paginaCargos" width="100%" messageKeyNoElementos="jsp.gestionarcargos.noregistros" nombre="visorCargos" seleccionSimple="true" nombreForma="gestionarCargosForm" nombreCampoSeleccionados="seleccionados" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
				    <vgcinterfaz:columnaVisorLista nombre="nombre" width="100%" onclick="javascript:consultar(document.gestionarCargosForm,'nombre', null)">
				        <vgcutil:message key="jsp.gestionarcargos.columna.nombre" />
				    </vgcinterfaz:columnaVisorLista>
				    
				    <vgcinterfaz:filasVisorLista nombreObjeto="cargo">
				
				        <vgcinterfaz:visorListaFilaId>
				            <bean:write name="cargo" property="cargoId" />
				        </vgcinterfaz:visorListaFilaId>
				
				        <vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				            <bean:write name="cargo" property="nombre" />
				        </vgcinterfaz:valorFilaColumnaVisorLista>
				    </vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
				
				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
				    <pagination-tag:pager nombrePaginaLista="paginaCargos" labelPage="inPagina" action="javascript:consultar(gestionarCargosForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>
				
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
				    <logic:notEmpty name="gestionarCargosForm" property="atributoOrden">
				        <b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaCargos" property="infoOrden" />
				    </logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>

		<script type="text/javascript">
			resizeAlto(document.getElementById('body-cargos'), 250);

			var visor = document.getElementById('visorCargos');
			if (visor != null)
				visor.style.width = (parseInt(_myWidth) - 140) + "px";
		</script>
	</tiles:put>
</tiles:insert>