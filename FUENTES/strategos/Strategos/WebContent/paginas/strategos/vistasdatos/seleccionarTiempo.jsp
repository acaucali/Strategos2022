<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (19/09/2012) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.seleccionartiempo.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script language="JavaScript" type="text/javascript">			
			
			var seleccionMultiple = '<bean:write name="seleccionarTiempoForm" property="seleccionMultiple" scope="session" />';
			
			function seleccionar() {
				if ((document.seleccionarTiempoForm.seleccionados.value == null) || (document.seleccionarTiempoForm.seleccionados.value == "")) {
					alert('<vgcutil:message key="jsp.seleccionar.noseleccion" />');
					return;
				}
				this.opener.document.<bean:write name="seleccionarTiempoForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarTiempoForm" property="nombreCampoValor" scope="session" />.value=document.seleccionarTiempoForm.valoresSeleccionados.value;
				this.opener.document.<bean:write name="seleccionarTiempoForm" property="nombreForma" scope="session" />.<bean:write name="seleccionarTiempoForm" property="nombreCampoOculto" scope="session" />.value=document.seleccionarTiempoForm.seleccionados.value;				
				<logic:notEmpty name="seleccionarTiempoForm" property="funcionCierre" scope="session">					
					this.opener.<bean:write name="seleccionarTiempoForm" property="funcionCierre" scope="session" />;
				</logic:notEmpty>
				this.close();
			}
			
			function eventoClickFilaSeleccion(idsSeleccionados, valoresSeleccionados, nombreTabla, fila) {	
		       if (seleccionMultiple == '1') {
		         eventoClickFilaSeleccionMultiple(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);										 
		       } else {
		         eventoClickFilaV2(idsSeleccionados, valoresSeleccionados, nombreTabla, fila);
		       }		   		
		    }

			function eventoMouseFueraFilaSeleccion(idsSeleccionados, fila) {	
		       if (seleccionMultiple == '1') {
		   	      eventoMouseFueraFilaSeleccionMultiple(idsSeleccionados, fila);		      
		       } else {
		          eventoMouseFueraFilaV2(idsSeleccionados, fila);
		       }		   		
		    }           
           
			function eventoMouseEncimaFilaSeleccion(idsSeleccionados, fila) {
		       if (seleccionMultiple == '1') {
		   	      eventoMouseEncimaFilaSeleccionMultiple(idsSeleccionados, fila);		      
		       } else {
		          eventoMouseEncimaFilaV2(idsSeleccionados, fila);
		       }		   		
		    }           		   
		   	
		   	function inicializarVisorListaSeleccion(idsSeleccionados, nombreTabla) {			
   		   	   if (seleccionMultiple == '1') {
		   		  inicializarVisorListaSeleccionMultiple(idsSeleccionados, nombreTabla);			      
		       } else {
		          inicializarVisorListaSeleccionSimple(idsSeleccionados, nombreTabla);
		       }		   		
		   	}
		   	
		   	function limpiarFiltros() {
				seleccionarTiempoForm.periodoInicial.value = "";
				seleccionarTiempoForm.anoInicial.value = "";
				seleccionarTiempoForm.periodoFinal.value = "";
				seleccionarTiempoForm.anoFinal.value = "";
				seleccionarTiempoForm.submit();
			}

			function buscar() {
				seleccionarTiempoForm.submit();
			}	
                        
		</script>

		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<script language="javascript" src="<html:rewrite page='/componentes/visorLista/visorListaJsTag.jsp'/>"></script>


		<%-- Representaci�n de la Forma --%>
		<html:form action="/vistasdatos/seleccionarTiempo" styleClass="formaHtml">

			<%-- Atributos de la Forma --%>
			<html:hidden property="pagina" />
			<html:hidden property="atributoOrden" />
			<html:hidden property="tipoOrden" />
			<html:hidden property="seleccionados" />
			<html:hidden property="valoresSeleccionados" />
			<html:hidden property="nombreForma" />
			<html:hidden property="nombreCampoOculto" />
			<html:hidden property="nombreCampoValor" />
			<html:hidden property="seleccionMultiple" />
			<html:hidden property="funcionCierre" />

			<vgcinterfaz:contenedorForma esSelector="true" comandoAceptarSelector="seleccionar()" comandoCancelarSelector="window.close()">

				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.seleccionartiempo.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<%-- Bot�n Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:document.seleccionarTiempoForm.submit();
				</vgcinterfaz:contenedorFormaBotonActualizar>

				<%-- Barra Gen�rica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
				
					<%-- Barra de Herramientas --%>
					<vgcinterfaz:barraHerramientas nombre="barraSeleccionarTiempo">
						<vgcinterfaz:barraHerramientasBoton nombreImagen="filtrar" pathImagenes="/componentes/barraHerramientas/" nombre="filtrar" onclick="javascript:buscar();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.buscar.alt" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
						<vgcinterfaz:barraHerramientasBoton nombreImagen="limpiar" pathImagenes="/componentes/barraHerramientas/" nombre="limpiar" onclick="javascript:limpiarFiltros();">
							<vgcinterfaz:barraHerramientasBotonTitulo>
								<vgcutil:message key="boton.limpiar.alt" />
							</vgcinterfaz:barraHerramientasBotonTitulo>
						</vgcinterfaz:barraHerramientasBoton>
					</vgcinterfaz:barraHerramientas>

					<%-- Filtros --%>
					<table width="100%" cellpadding="1" cellspacing="0">
						<tr class="barraFiltrosForma">
							<td colspan="2">
							<hr width="100%">
							</td>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.seleccionartiempo.frecuencia" /></td>

							<logic:equal name="seleccionarTiempoForm" property="frecuenciaBloqueada" value="1">
								<td><html:text styleClass="cuadroTexto" property="nombreFrecuencia" readonly="true"></html:text></td>
							</logic:equal>
							<logic:equal name="seleccionarTiempoForm" property="frecuenciaBloqueada" value="0">
								<td><html:select property="frecuencia" styleClass="cuadroTexto">
									<html:optionsCollection property="frecuencias" value="frecuenciaId" label="nombre" />
								</html:select></td>
							</logic:equal>
						</tr>
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.seleccionartiempo.desde" /></td>

							<td width="10px">

							<table width="100%" cellpadding="1" cellspacing="0">
								<tr>
									<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.periodo" /></td>
									<td><html:select property="periodoInicial" styleClass="cuadroTexto">
										<html:option value=""></html:option>
										<html:optionsCollection property="listaPeriodos" value="valor" label="nombre" />
									</html:select></td>
									<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.ano" /></td>
									<td><html:select property="anoInicial" styleClass="cuadroTexto">
										<html:option value=""></html:option>
										<html:optionsCollection property="listaAnos" value="valor" label="nombre" />
									</html:select></td>
								</tr>
							</table>

							</td>

						</tr>
						<tr class="barraFiltrosForma">
							<td width="10px"><vgcutil:message key="jsp.seleccionartiempo.hasta" /></td>

							<td width="10px">

							<table width="100%" cellpadding="1" cellspacing="0">
								<tr>
									<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.periodo" /></td>
									<td><html:select property="periodoFinal" styleClass="cuadroTexto">
										<html:option value=""></html:option>
										<html:optionsCollection property="listaPeriodos" value="valor" label="nombre" />
									</html:select></td>
									<td class="barraFiltrosForma"><vgcutil:message key="jsp.seleccionartiempo.ano" /></td>
									<td><html:select property="anoFinal" styleClass="cuadroTexto">
										<html:option value=""></html:option>
										<html:optionsCollection property="listaAnos" value="valor" label="nombre" />
									</html:select></td>
								</tr>
							</table>

							</td>

						</tr>
					</table>					

				</vgcinterfaz:contenedorFormaBarraGenerica>


				<html:hidden property="frecuenciaBloqueada" />

				<%-- Visor Tipo Lista --%>
				<table class="listView" cellpadding="5" id="tablaTiempo" cellspacing="1">

					<%-- Encabezado del "Visor Tipo Lista" --%>
					<tr class="encabezadoListView" height="20px">

						<td align="center" width="10px" class="mouseFueraEncabezadoListView"><img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10"></td>
						<td align="center" width="100%" style="cursor: pointer" onMouseOver="this.className='mouseEncimaEncabezadoListView'"
							onMouseOut="this.className='mouseFueraEncabezadoListView'" class="mouseFueraEncabezadoListView"><vgcutil:message key="jsp.seleccionartiempo.anoperiodo" /><img name="anoperiodo"
							src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.ascendentedescendente.alt" />"></td>
					</tr>

					<%-- Cuerpo del Visor Tipo Lista --%>
					<logic:iterate name="seleccionarTiempoForm" property="anosPeriodos" scope="session" id="tiempo">

						<tr onclick="eventoClickFilaSeleccion(document.seleccionarTiempoForm.seleccionados, document.seleccionarTiempoForm.valoresSeleccionados, 'tablaTiempo', this)"
							id="<bean:write name="tiempo" property="valor" />" class="mouseFueraCuerpoListView" onMouseOver="eventoMouseEncimaFilaSeleccion(document.seleccionarTiempoForm.seleccionados, this)"
							onMouseOut="eventoMouseFueraFilaSeleccion(document.seleccionarTiempoForm.seleccionados, this)" height="20px">
							<td width="10" valign="middle" align="center"><img name="img<bean:write name="tiempo" property="valor" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0"
								width="10" height="10"></td>
							<td id="valor<bean:write name="tiempo" property="valor" />"><bean:write name="tiempo" property="nombre" /></td>
						</tr>

					</logic:iterate>

				</table>

			</vgcinterfaz:contenedorForma>

			<%-- Funciones JavaScript locales de la p�gina Jsp --%>
			<script language="JavaScript" type="text/javascript">

				// Invoca la funci�n que hace el ordenamiento de las columnas
				inicializarVisorListaSeleccion(document.seleccionarTiempoForm.seleccionados, 'tablaTiempo');

			</script>

		</html:form>

	</tiles:put>

</tiles:insert>
