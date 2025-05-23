<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Andres Martinez (04/06/2022) --%>

<tiles:insert definition="doc.selectorLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">..:: <vgcutil:message key="jsp.asignar.pesos.portafolio.titulo" />
	</tiles:put>
	
	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		
		<%-- Funciones JavaScript locales de la p�gina Jsp --%>
		<script type="text/javascript">
		
			var _setCloseParent = false;
		
			function cancelar() 
			{
				this.close();
			}	
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function validar() 
			{
				if (!actualizarPesoTotal(true)) 
					return false;	
				
				if (document.editarInstrumentosForm.pesoTotal.value != '') 
				{
					if (parseInt(document.editarInstrumentosForm.pesoTotal.value) == 100) 
						return true;
					else if (parseInt(document.editarInstrumentosForm.pesoTotal.value) >= 100)
					{
						alert('<vgcutil:message key="jsp.asignar.pesos.portafolio.mensaje.sumapesocienmayor" />');
						return false;
					}
					else
					{
						alert('<vgcutil:message key="jsp.asignar.pesos.portafolio.mensaje.sumapesocienmenor" />');
						return false;
					}					
				} 
				else 
					return true;
			}
			
			function guardar() 
			{
				if (validar()) 
				{
					window.document.editarInstrumentosForm.action='<html:rewrite action="/instrumentos/asignarPesos" />?id=' + document.editarInstrumentosForm.instrumentoId.value + '&funcion=guardar';
					window.document.editarInstrumentosForm.submit();
				}
			}
	
			function actualizar() 
			{
				window.document.editarInstrumentosForm.action='<html:rewrite action="/instrumentos/asignarPesos" />?id=' + document.editarInstrumentosForm.instrumentoId.value;
				window.document.editarInstrumentosForm.submit();
			}
			
			function actualizarPesoTotal(validar) 
			{
				var total = 0;
				var hayPesos = false;
				var totalIniciativas = 0;
				var totalConPeso = 0;
				for (var index = 0; index < window.document.editarInstrumentosForm.elements.length; index++) 
				{
					if (window.document.editarInstrumentosForm.elements[index].name.indexOf('pesoIniciativa') > -1) 
					{
						totalIniciativas++;
						if ((window.document.editarInstrumentosForm.elements[index].value != null) && (window.document.editarInstrumentosForm.elements[index].value != '')) 
						{
							hayPesos = true;
							total = total + convertirNumeroFormateadoToNumero(window.document.editarInstrumentosForm.elements[index].value, false);
							totalConPeso++;
						}
					}
				}
	
				if (hayPesos) 
					window.document.editarInstrumentosForm.pesoTotal.value = formatearNumero(total, false, 2);
				else 
					window.document.editarInstrumentosForm.pesoTotal.value = '';
				
				if ((validar != null) && (validar == true)) 
				{
					if ((totalConPeso > 0) && (totalConPeso != totalIniciativas)) 
					{
						alert('<vgcutil:message key="jsp.asignar.pesos.portafolio.mensaje.sinpeso" />');
						return false;
					} 
					else 
						return true;
				}
			}
		
			function limpiarPesos() 
			{
				if (!confirm('<vgcutil:message key="jsp.asignar.pesos.portafolio.mensaje.confirmarlimpiarpesos" />')) 
					return;
				
				for (var index = 0; index < window.document.editarInstrumentosForm.elements.length; index++) 
				{
					if (window.document.editarInstrumentosForm.elements[index].name.indexOf('pesoIniciativa') > -1) 
						window.document.editarInstrumentosForm.elements[index].value = '';
				}
				
				actualizarPesoTotal(false);
			}
		</script>
		
		<%-- Funciones JavaScript externas de la p�gina Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>
		
		<%-- Representaci�n de la Forma --%>
		<html:form action="/instrumentos/asignarPesos" styleClass="formaHtml">
		
			<html:hidden property="instrumentoId" />
			
			<vgcinterfaz:contenedorForma>
			
				<%-- T�tulo --%>
				<vgcinterfaz:contenedorFormaTitulo>..:: <vgcutil:message key="jsp.asignar.pesos.portafolio.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>
				
				<%-- Bot�n Actualizar --%>
				<vgcinterfaz:contenedorFormaBotonActualizar>
					javascript:actualizar();
				</vgcinterfaz:contenedorFormaBotonActualizar>
				
				<%-- Barra Gen�rica --%>
				<vgcinterfaz:contenedorFormaBarraGenerica height="20px">
					<table class="tablaSpacing0Padding0Width100" style="padding: 1px;">						
						 <tr class="barraFiltrosForma">
							<td width="100px">
								<logic:notEmpty name="editarInstrumentosForm" property="nombreCorto">
									<logic:notEmpty name="editarInstrumentosForm" property="nombreCorto">
										<b><vgcutil:message key="jsp.asignar.pesos.instrumento.instrumento" /></b>
									</logic:notEmpty>
								</logic:notEmpty>
							</td>
							<td>
								<logic:notEmpty name="editarInstrumentosForm" property="nombreCorto">
									<logic:notEmpty name="editarInstrumentosForm" property="nombreCorto">
										<bean:write name="editarInstrumentosForm" property="nombreCorto" />
									</logic:notEmpty>
								</logic:notEmpty>
							</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraGenerica>
						
				<%-- Encabezados --%>
				<div style="height: 390px; overflow: auto;">
					
					<vgcinterfaz:visorLista namePaginaLista="asignarPesosIniciativasInstrumento.paginaIniciativas" 
						nombre="visorIniciativasporInstrumento" 
						messageKeyNoElementos="jsp.asignar.pesos.portafolio.nohayelementos" 
						nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">
					
						<%-- Encabezados --%>
						<vgcinterfaz:columnaVisorLista nombre="nombre" width="580px">
							<vgcutil:message key="jsp.asignar.pesos.portafolio.columna.nombre" />
						</vgcinterfaz:columnaVisorLista>
	
						<vgcinterfaz:columnaVisorLista nombre="peso" width="100px">
							<vgcutil:message key="jsp.asignar.pesos.portafolio.columna.peso" />	
						</vgcinterfaz:columnaVisorLista>
					
						<%-- Filas --%>
						<vgcinterfaz:filasVisorLista nombreObjeto="instrumentoIniciativa">
							<vgcinterfaz:visorListaFilaId>
								<bean:write name="instrumentoIniciativa" property="iniciativa.iniciativaId" />
							</vgcinterfaz:visorListaFilaId>
							
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
								<bean:write name="instrumentoIniciativa" property="iniciativa.nombre" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
							
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="peso" align="center">
							<input 
								style="text-align: right" 
								onkeypress="validarEntradaNumeroEventoOnKeyPress(this, event, 3, false);" 
								onkeyup="validarEntradaNumeroEventoOnKeyUp(this, event, 3, false); actualizarPesoTotal();" 
								onblur="validarEntradaNumeroEventoOnBlur(this, event, 3, false)" 
								type="text" 
								name="pesoIniciativa<bean:write name="instrumentoIniciativa" property="iniciativa.iniciativaId" />" 
								value="<bean:write name="instrumentoIniciativa" property="peso" />" 
								class="cuadroTexto" 
								size="10" 
								maxlength="10" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						</vgcinterfaz:filasVisorLista>									
					</vgcinterfaz:visorLista>					
				</div>
				
				<table style="width: 100%;">
					<tr>
						<td>
							<hr style="width: 100%;">
						</td>
					</tr>
					<tr class="mouseFueraCuerpoListView">
						<td align="right">
							<b><vgcutil:message key="jsp.asignar.pesos.portafolio.pesototal" />:</b>&nbsp;&nbsp;&nbsp;&nbsp;
							<input 
								type="text" 
								name="pesoTotal" 
								class="cuadroTexto" 
								size="10" 
								maxlength="10" 
								disabled="disabled" 
								style="text-align: right; color: black;" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior>
					<table style="width: 100%;">
						<tr>
							<td align="right">
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:limpiarPesos();" class="mouseFueraBarraInferiorForma">
									<vgcutil:message key="jsp.asignar.pesos.portafolio.limpiarpesos" />
								</a>&nbsp;&nbsp;
								<img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:guardar();" class="mouseFueraBarraInferiorForma"> 
									<vgcutil:message key="boton.aceptar" />
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
								<img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10"> 
								<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
									<vgcutil:message key="boton.cancelar" />
								</a>&nbsp;&nbsp;
							</td>
						</tr>
					</table>
				</vgcinterfaz:contenedorFormaBarraInferior>
				
			</vgcinterfaz:contenedorForma>
		</html:form>
		
		<script type="text/javascript">
			for (var index = 0; index < window.document.editarInstrumentosForm.elements.length; index++) 
			{
				if (window.document.editarInstrumentosForm.elements[index].name.indexOf('pesoTotal') > -1) 
				{
					var numero = window.document.editarInstrumentosForm.elements[index].value;
					var numeroFormateado = formatearNumero(numero, false, 2);
					window.document.editarInstrumentosForm.elements[index].value = numeroFormateado;
				}
			}
	
			actualizarPesoTotal();		
		</script>
		<script>
			<logic:equal name="editarInstrumentosForm" property="status" value="0">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.actualizados.ok" />', 80, 300);
				refrescarPadre();
			</logic:equal>
			<logic:equal name="editarInstrumentosForm" property="status" value="1">
				_setCloseParent = false;
				showAlert('<vgcutil:message key="action.guardarregistro.actualizados.no.ok" />', 80, 300);
			</logic:equal>
			<logic:equal name="editarInstrumentosForm" property="status" value="2">
				_setCloseParent = true;
				showAlert('<vgcutil:message key="action.editarregistro.noencontrado" />', 80, 300);
			</logic:equal>
		</script>
		
		
	</tiles:put>

</tiles:insert>