<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%--  Modificado por: Kerwin Arias (12/05/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.framework.gestionarauditorias.titulo" />
	</tiles:put>

	<%-- Barra de Area --%>
	<tiles:put name="areaBar" value="/paginas/framework/barraAreasAdministracion.jsp"></tiles:put>

	<tiles:put name="body" type="String">

		<script type="text/javascript">

			var _fechaDesde;
			var _fechaHasta;
			
			function configurarVisorAuditorias() 
			{
				configurarVisorLista('com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase', 'visorAuditorias', '<vgcutil:message key="jsp.framework.gestionarauditorias.titulo" />');
			}
			
			function validar() 
			{
				if (document.gestionarAuditoriasForm.fechaDesde.value == "")
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.vacio" /> ');
					return false;
				}

				if (document.gestionarAuditoriasForm.fechaHasta.value == "")
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.vacio" /> ');
					return false;
				}
				
		 		if (!fechaValida(document.gestionarAuditoriasForm.fechaDesde))
	 			{
		 			alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.invalido" /> ');
		 			return;
	 			}

		 		if (!fechaValida(document.gestionarAuditoriasForm.fechaHasta))
	 			{
		 			alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.invalido" /> ');
		 			return;
	 			}
				
				var fecha1 = document.gestionarAuditoriasForm.fechaDesde.value.split("/");
				var fecha2 = document.gestionarAuditoriasForm.fechaHasta.value.split("/");

				var diaDesde = fecha1[0];
				var diaHasta = fecha2[0];
				
				var mesDesde = fecha1[1];
				var mesHasta = fecha2[1];
				
				var anoDesde = fecha1[2];
				var anoHasta = fecha2[2];

		 		var desde = parseInt(anoDesde + "" + (mesDesde.length == 1 ? ("0" + mesDesde) : mesDesde) + "" + (diaDesde.length == 1 ? ("0" + diaDesde) : diaDesde));
				var hasta = parseInt(anoHasta + "" + (mesHasta.length == 1 ? ("0" + mesHasta) : mesHasta) + "" + (diaHasta.length == 1 ? ("0" + diaHasta) : diaHasta));

				if (hasta<desde) 
				{
					alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.rango.fechas.invalido" /> ');
					return false;
				} 
		 		
				return true;
			}
			
			function buscar() 
			{
				if (validar())
					document.gestionarAuditoriasForm.submit();
			}

			function limpiarBusqueda() 
			{
				document.gestionarAuditoriasForm.fechaDesde.value = _fechaDesde;
				document.gestionarAuditoriasForm.fechaHasta.value = _fechaHasta;
				document.gestionarAuditoriasForm.tipoEvento.value = '';
				document.gestionarAuditoriasForm.usuario.value = '';
				document.gestionarAuditoriasForm.entidad.value = '';
				buscar();
			}
			
			function seleccionarFechaDesde() 
			{
				mostrarCalendario('document.gestionarAuditoriasForm.fechaDesde' , document.gestionarAuditoriasForm.fechaDesde.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function limpiarFechaDesde() 
			{
				document.gestionarAuditoriasForm.fechaDesde.value = '';
			}
			
			function seleccionarFechaHasta() 
			{
				mostrarCalendario('document.gestionarAuditoriasForm.fechaHasta' , document.gestionarAuditoriasForm.fechaHasta.value, '<vgcutil:message key="formato.fecha.corta" />');
			}
			
			function limpiarFechaHasta() 
			{
				document.gestionarAuditoriasForm.fechaHasta.value = '';
			}

			function actualizar() 
			{
				document.gestionarAuditoriasForm.submit();
			}
			
			function limpiarFiltros() 
			{
				alert(document.gestionarAuditoriasForm.tipoEvento.value);
			}
			
			function seleccionarUsuario() 
			{
				abrirSelectorUsuarios('gestionarAuditoriasForm', 'usuario', 'usuarioId', null, null, null, null);
			}
			
			function limpiarUsuario() 
			{
				document.gestionarAuditoriasForm.usuarioId.value = '';
				document.gestionarAuditoriasForm.usuario.value = '';
			}
			
			
			function limpiarInstanciaNombre() 
			{
				document.gestionarAuditoriasForm.entidad.value = '';
			}
			
			function reporte() 
			{
				alert('Funci�n No Disponible');
			}
			
			function gestionarAuditoriasDetalle(auditoriaId) 
			{
				queryString = '?';
				if (auditoriaId != null) 
					queryString = queryString + 'auditoriaId=' + auditoriaId ;
				
				abrirVentanaModal('<html:rewrite action="/framework/auditorias/gestionarAuditoriasPorAtributo"/>' + queryString,'auditorias', '1000', '690');
			}
			
			function reporteAuditoriaExcel() 
			{						
				queryString = '?';
				if(document.gestionarAuditoriasForm.fechaDesde.value != null || document.gestionarAuditoriasForm.fechaDesde.value !=''){
					queryString = queryString + 'fechaDesde=' + document.gestionarAuditoriasForm.fechaDesde.value ;
				}
				if(document.gestionarAuditoriasForm.fechaHasta.value != null || document.gestionarAuditoriasForm.fechaHasta.value !=''){
					queryString = queryString + '&fechaHasta=' + document.gestionarAuditoriasForm.fechaHasta.value ;
				}
				if(document.gestionarAuditoriasForm.tipoEvento.value != null || document.gestionarAuditoriasForm.tipoEvento.value !=''){
					queryString = queryString + '&accion=' + document.gestionarAuditoriasForm.tipoEvento.value ;
				}
				if(document.gestionarAuditoriasForm.usuario.value != null || document.gestionarAuditoriasnForm.usuario.value !=''){
					queryString = queryString + '&usuario=' + document.gestionarAuditoriasForm.usuario.value ;
				}
				if(document.gestionarAuditoriasForm.entidad.value != null || document.gestionarAuditoriasForm.entidad.value !=''){
					queryString = queryString + '&entidad=' + document.gestionarAuditoriasForm.entidad.value ;
				}
				
				window.location.href='<sslext:rewrite action="/framework/auditorias/reporteAuditoriaExcel"/>' + queryString;	
			}
			
			function reporteAuditoriaPdf() 
			{
				queryString = '?';
				if(document.gestionarAuditoriasForm.fechaDesde.value != null || document.gestionarAuditoriasForm.fechaDesde.value !=''){
					queryString = queryString + 'fechaDesde=' + document.gestionarAuditoriasForm.fechaDesde.value ;
				}
				if(document.gestionarAuditoriasForm.fechaHasta.value != null || document.gestionarAuditoriasForm.fechaHasta.value !=''){
					queryString = queryString + '&fechaHasta=' + document.gestionarAuditoriasForm.fechaHasta.value ;
				}
				if(document.gestionarAuditoriasForm.tipoEvento.value != null || document.gestionarAuditoriasForm.tipoEvento.value !=''){
					queryString = queryString + '&accion=' + document.gestionarAuditoriasForm.tipoEvento.value ;
				}
				if(document.gestionarAuditoriasForm.usuario.value != null || document.gestionarAuditoriasnForm.usuario.value !=''){
					queryString = queryString + '&usuario=' + document.gestionarAuditoriasForm.usuario.value ;
				}
				if(document.gestionarAuditoriasForm.entidad.value != null || document.gestionarAuditoriasForm.entidad.value !=''){
					queryString = queryString + '&entidad=' + document.gestionarAuditoriasForm.entidad.value ;
				}
				
				abrirReporte('<html:rewrite action="/framework/auditorias/reporteAuditoriaPdf"/>'+ queryString, 'auditoriasResumido', '750', '550');
			}
			

		</script>

		<jsp:include flush="true" page="/paginas/framework/usuarios/usuariosJs.jsp"></jsp:include>

		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<html:form action="/framework/auditorias/gestionarAuditorias" styleClass="formaHtmlCompleta">

			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />

			<vgcinterfaz:contenedorForma>

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.framework.gestionarauditorias.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Bot�n Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<vgcinterfaz:contenedorFormaBarraMenus>
					<vgcinterfaz:contenedorMenuHorizontal>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
								<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" permisoId="AUDITORIA_PRINT_SET"/>
								<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" permisoId="AUDITORIA_PRINT"/>
								<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuHerramientasAuditorias" key="menu.herramientas">
								<vgcinterfaz:botonMenu key="menu.herramientas.configurarvisorlista" onclick="configurarVisorAuditorias();" permisoId="AUDITORIA_SET_VIEW" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
						<%-- Men�: Ayuda --%>
						<vgcinterfaz:contenedorMenuHorizontalItem>
							<vgcinterfaz:menuBotones id="menuAyuda" key="menu.ayuda">
								<vgcinterfaz:botonMenu key="menu.ayuda.manual" onclick="abrirManual();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.acerca" onclick="acerca();" agregarSeparador="true" />
								<vgcinterfaz:botonMenu key="menu.ayuda.licencia" onclick="licencia();" />
							</vgcinterfaz:menuBotones>
						</vgcinterfaz:contenedorMenuHorizontalItem>
						
					</vgcinterfaz:contenedorMenuHorizontal>
				</vgcinterfaz:contenedorFormaBarraMenus>
				
				
				
				
				
				<%-- Barra Gen�rica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<vgcinterfaz:barraHerramientas nombre="barraGestionarAuditoriasEventos" agregarSeparadorInferior="true" agregarSeparadorSuperior="false">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="buscar" onclick="javascript:buscar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.buscar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="limpiarFiltro" pathImagenes="/componentes/barraHerramientas/" nombre="limpiarBusqueda" onclick="javascript:limpiarBusqueda();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.limpiar" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						
						
						<!-- 
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
							<vgcinterfaz:barraHerramientasBoton nombreImagen="pdf" pathImagenes="/componentes/barraHerramientas/" nombre="pdfResumido" onclick="javascript:reporteAuditoriaPdf();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="menu.archivo.presentacionpreliminar.detallada" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
							</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
						-->
						
						
						<vgcutil:tienePermiso aplicaOrganizacion="false" permisoId="USUARIO_PRINT">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="exportar"
								pathImagenes="/componentes/barraHerramientas/" nombre="exportar"
								onclick="javascript:reporteAuditoriaExcel();">
								<vgcinterfaz:barraHerramientasBotonTitulo>
									<vgcutil:message key="boton.exportar.usuarios.reportes.excel" />
								</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						</vgcutil:tienePermiso>
				
						
					</vgcinterfaz:barraHerramientas>
					
					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.filtro.fechadesde" /></td>
							<td><html:text property="fechaDesde" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td width="30px">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.filtro.fechahasta" /></td>
							<td><html:text property="fechaHasta" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.calendario.alt" />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
							<td width="30px">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						
						<tr class="barraFiltrosForma">
							<td><vgcutil:message key="jsp.framework.gestionarauditorias.columna.tipoevento" /></td>
							<td><html:select property="tipoEvento" styleClass="cuadroTexto" size="1">
								<html:option value="">
									<vgcutil:message key="jsp.framework.gestionarauditorias.filtro.todos" />
								</html:option>
								<html:option value="inserci�n">
									<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.filtro.insercion" />
								</html:option>
								<html:option value="actualizaci�n">
									<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.filtro.modificacion" />
								</html:option>
								<html:option value="eliminaci�n">
									<vgcutil:message key="jsp.framework.gestionarauditorias.filtro.eliminacion" />
								</html:option>
								<html:option value="login">
									<vgcutil:message key="jsp.framework.gestionarauditorias.filtro.login" />
								</html:option>
								<html:option value="logout">
									<vgcutil:message key="jsp.framework.gestionarauditorias.filtro.logout" />
								</html:option>
								
							</html:select></td>
							<td width="30px">&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						
						<tr class="barraFiltrosForma">
							<td width="120px"><vgcutil:message key="jsp.framework.gestionarauditorias.columna.usuario" /></td>
							<td width="320px"><html:text property="usuario" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px"><html:hidden property="usuarioId" />
								<img style="cursor:pointer" onclick="seleccionarUsuario()" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title='Seleccionar Usuario'>&nbsp;<img style="cursor:pointer" onclick="limpiarUsuario()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />">
							</td>
							<td>&nbsp;</td>
						</tr>
						
						<tr class="barraFiltrosForma">
							<td width="120px"><vgcutil:message key="jsp.framework.gestionarauditorias.columna.nombreobjeto" /></td>
							<td width="320px"><html:text property="entidad" styleClass="cuadroTexto" size="50" /></td>
							<td width="30px">
								<img src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" onclick="limpiarInstanciaNombre()" style="cursor: pointer;" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
							</td>
							<td>&nbsp;</td>
						</tr>
						
						
						
					</table>

				</vgcinterfaz:contenedorFormaBarraGenerica>
				
				
				
				
				
				
				<vgcinterfaz:visorLista namePaginaLista="paginaAuditorias" nombre="visorAuditorias" messageKeyNoElementos="jsp.framework.gestionarauditorias.noauditorias" nombreConfiguracionBase="com.visiongc.framework.web.configuracion.FrameworkWebConfiguracionesBase">
					<vgcinterfaz:columnaVisorLista nombre="fechaEjecucion" width="200px" onclick="javascript:consultar(self.document.gestionarAuditoriasForm,'fechaEjecucion', null)">
						<vgcutil:message key="jsp.framework.gestionarauditorias.columna.fecha" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="usuario" width="280px" onclick="javascript:consultar(self.document.gestionarAuditoriasForm,'usuario', null)">
						<vgcutil:message key="jsp.framework.gestionarauditorias.columna.usuario" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="tipoEvento" width="150px" onclick="javascript:consultar(self.document.gestionarAuditoriasForm,'tipoEvento', null)">
						<vgcutil:message key="jsp.framework.gestionarauditorias.columna.tipoevento" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="entidad" width="330px" onclick="javascript:consultar(self.document.gestionarAuditoriasForm,'entidad', null)">
						<vgcutil:message key="jsp.framework.gestionarauditorias.columna.nombreobjeto" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="detalle" width="230px">
						<vgcutil:message key="jsp.framework.gestionarauditorias.medicion.columna.detalle" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:filasVisorLista nombreObjeto="auditoria">
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="fechaEjecucion" align="center">
							<bean:write name="auditoria" property="fechaEjecucion" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="usuario" align="left">
							<bean:write name="auditoria" property="usuario" />
						</vgcinterfaz:valorFilaColumnaVisorLista>					
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="tipoEvento" align="left">
							<bean:write name="auditoria" property="tipoEvento"/>
						</vgcinterfaz:valorFilaColumnaVisorLista>											
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="entidad" align="left">
							<bean:write name="auditoria" property="entidad" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="detalle" align="left">
							<a href="javascript:gestionarAuditoriasDetalle('<bean:write name="auditoria" property="auditoriaId" />');">Consultar Detalle</a>
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</vgcinterfaz:filasVisorLista>
				</vgcinterfaz:visorLista>
				
				

				<%-- Paginador --%>
				<vgcinterfaz:contenedorFormaPaginador>
					<pagination-tag:pager nombrePaginaLista="paginaAuditorias" labelPage="inPagina" action="javascript:consultar(gestionarAuditoriasForm, null, inPagina)" styleClass="paginador" />
				</vgcinterfaz:contenedorFormaPaginador>

				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<logic:notEmpty name="gestionarAuditoriasForm" property="atributoOrden">
						<b><vgcutil:message key="jsp.visorlista.ordenado" /></b>: <bean:write name="paginaAuditorias" property="infoOrden" />
					</logic:notEmpty>
				</vgcinterfaz:contenedorFormaBarraInferior>

			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			_fechaDesde = document.gestionarAuditoriasForm.fechaDesde.value;
			_fechaHasta = document.gestionarAuditoriasForm.fechaHasta.value;
		</script>

	</tiles:put>

</tiles:insert>
