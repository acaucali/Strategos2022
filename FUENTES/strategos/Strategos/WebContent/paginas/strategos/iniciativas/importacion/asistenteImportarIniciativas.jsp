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

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<%-- Titulo --%>
	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.asistente.importacion.iniciativa.titulo" />
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<link rel="stylesheet" type="text/css" href="<html:rewrite page="/componentes/estilos/browserFile.css" />">
		<script type="text/javascript" src="<html:rewrite page='/componentes/comunes/XmlTextWriter.js'/>"></script>
		<jsp:include page="/paginas/strategos/planes/planesJs.jsp"></jsp:include>

		<%-- Funciones JavaScript para el wizzard --%>
		<script>
			var _pasoActual = 1;
			var _archivoVerificado = false;
			var _configuracionSalvada = false;
			var _setCloseParent = false;
			var _verificarTabla = false;
			
			function init()
			{				
				limpiarArchivo();
			}
			
			function siguiente() 
			{
				var valido = true; 
			
				switch (_pasoActual) 
				{
					case 2:
						valido = validar(_pasoActual);
						break;
					case 3:
						valido = validar(_pasoActual);
						break;
					case 4:
						valido = validar(_pasoActual);
						if (valido)
							salvar();
						break;					
				}
				if (valido) 
				{
					_pasoActual = _pasoActual + 1;
					mostrarBotones(_pasoActual);
				}
				mostrarTitulo();
			}
			
			function salvar()
			{
				var xml = '?funcion=importar';
																		
				xml = xml + '&archivo=' + document.importarIniciativasForm.fuenteSeleccionArchivo.value;				
				xml = xml + '&logErrores=' + (document.importarIniciativasForm.logErrores.checked ? 1 : 0);				
				xml = xml + '&showPresentacion=' + (document.importarIniciativasForm.showPresentacion.checked ? 1 : 0);		
				xml = xml + '&tipoImportacion=' + 7;

				activarBloqueoEspera();

				document.importarIniciativasForm.action = '<html:rewrite action="/iniciativas/importarSalvar"/>' + xml;
				document.importarIniciativasForm.submit();
			}
			
			function onSalvar()
			{
				if (document.importarIniciativasForm.respuesta.value == "Error")
					return;
				else if (document.importarIniciativasForm.respuesta.value == "action.guardarmetas.mensaje.guardarmetas.relacionados")
					return;
				else if (document.importarIniciativasForm.respuesta.value == "action.guardarmediciones.mensaje.guardarmediciones.related")
					return;
				else if (document.importarIniciativasForm.respuesta.value.indexOf('FileError') > -1)
				{
					var valores = document.importarIniciativasForm.respuesta.value.split('|');
					alert('<vgcutil:message key="jsp.asistente.importacion.archivo.file.error" /> ' + ': ' + valores[1] + ' Columna: ' + valores[2]);
					return;
				}
				
				cancelar();
			}
			
			function previo() 
			{
				_pasoActual = _pasoActual - 1;
				mostrarBotones(_pasoActual);
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
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelPresentacion" />
						break;
					case 2:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelSeleccionTipo" />
						break;
					case 3:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						if (document.importarIniciativasForm.tipoFuente.value == "1" || document.importarIniciativasForm.tipoFuente.value == "2")
							<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelVerficar" />
						else
							<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelVerficarBD" />
						break;
					case 4:
						botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion;
						botones = botones + crearBoton(nombreBotonFinalizar, accionBotonFinalizar) + separacion;
						botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar)+ separacion;
						<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelFin" />
						break;
					//case 5:
						//botones = botones + crearBoton(nombreBotonPrevio, accionBotonPrevio) + separacion ;
						//botones = botones + crearBoton(nombreBotonSiguiente, accionBotonSiguiente) + separacion;
						//botones = botones + crearBoton(nombreBotonCancelar, accionBotonCancelar) + separacion;
						//<vgcinterfaz:mostrarPanelContenedorJs nombreContenedor="contenedorPanelesAsistente" nombrePanel="panelCalcular" />
						//break;
				
						
				}
				
				var barraBotones = document.getElementById('barraBotones');			
				barraBotones.innerHTML = botones;
			}
			
			function mostrarTitulo() 
			{
				var titulo = '..:: <vgcutil:message key="jsp.asistente.importacion.iniciativa.titulo" /> ' + (_pasoActual + ' <vgcutil:message key="jsp.asistente.importacion.titulo3" />');
				var celda = document.getElementById("tituloFicha");
				celda.innerHTML = titulo;
			}
			
			function validar(_pasoActual) 
			{
				if (_pasoActual == 2 && document.importarIniciativasForm.tipoFuente.value == "1" && document.importarIniciativasForm.separador.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.separador.vacio" /> ');
					return false;
				}

				if (_pasoActual == 2 && 
						(document.importarIniciativasForm.tipoFuente.value == "3" 
								|| document.importarIniciativasForm.tipoFuente.value == "4"
								|| document.importarIniciativasForm.tipoFuente.value == "5") && document.importarIniciativasForm.bdNombre.value == "")
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.nombre.vacio" /> ');
					return false;
				}
																					
				if (_pasoActual == 3 && !_archivoVerificado)
				{
					if (document.importarIniciativasForm.tipoFuente.value == "1" || document.importarIniciativasForm.tipoFuente.value == "2")
						alert('<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.verificar.archivo.false" /> ');					
					return false;
				}
				
				
				return true;
			}
			
			function cancelar() 
			{
				this.close();			
			}
					
			
			function seleccionarArchivo(obj)
			{
				var fileName = document.getElementById("file_browse_text");
				var file = obj.value;
				if (fileName != null)
					fileName.value = file;
			    var fileName = file.split("\\");
			    var archivo = fileName[fileName.length-1];
			    var extension = archivo.substring(archivo.indexOf('.') + 1, archivo.length); 

				if (document.importarIniciativasForm.tipoFuente.value == "1" && extension.toUpperCase() != 'TXT')
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.tipo.txt.noexiste" /> ');
					limpiarArchivo();
					return
				}
				else if (document.importarIniciativasForm.tipoFuente.value == "2" && document.importarIniciativasForm.excelTipo[0].checked && extension.toUpperCase() != 'XLS')
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.tipo.excel.2007.noexiste" /> ');
					limpiarArchivo();
					return
				}
				else if (document.importarIniciativasForm.tipoFuente.value == "2" && document.importarIniciativasForm.excelTipo[1].checked && extension.toUpperCase() != 'XLSX')
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.tipo.excel.2010.noexiste" /> ');
					limpiarArchivo();
					return
				}
							    
				document.getElementById("btnVerificar").disabled = false; 
				window.document.importarIniciativasForm.fuenteSeleccion.value = obj.value;
				window.document.importarIniciativasForm.fuenteSeleccionArchivo.value = archivo;
			}
			
			function limpiarArchivo()
			{
				window.document.importarIniciativasForm.fuenteSeleccion.value = '';
				window.document.importarIniciativasForm.fuenteSeleccionArchivo.value = '';
				document.getElementById("btnVerificar").disabled = true;
				document.getElementById("file_browse_text").value = "";
				document.getElementById("file_browse_wrapper").innerHTML=document.getElementById("file_browse_wrapper").innerHTML;
				
				var tablaListaInsumos = document.getElementById('tablaListaInsumos');
				
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);

				tablaListaInsumos = document.getElementById('tablaListaInsumosBd');
				
				// Se borra la lista de insumos
				while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
					tablaListaInsumos.deleteRow(0);
																
				
				_archivoVerificado = false;
				_verificarTabla = false;
			}
			
			function verificarArchivo()
			{
				if (document.importarIniciativasForm.fuenteSeleccion.value == "" && (document.importarIniciativasForm.tipoFuente.value == "1" || document.importarIniciativasForm.tipoFuente.value == "2"))
				{
					alert('<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.archivo.alerta.vacio" /> ');
					return;
				}				
				
				var xml = '?funcion=verificar';
				if (document.importarIniciativasForm.tipoFuente.value == "1" || document.importarIniciativasForm.tipoFuente.value == "2")
					xml = xml + '&archivo=' + document.importarIniciativasForm.fuenteSeleccionArchivo.value;
				else
				{
					xml = xml + '&password=' + document.importarIniciativasForm.bdPassword.value;
					if (_verificarTabla)
						xml = xml + '&verificarTabla=true';					
				}
				_archivoVerificado = false;
				
				document.importarIniciativasForm.action = '<html:rewrite action="/iniciativas/importarSalvar"/>' + xml;
				document.importarIniciativasForm.submit();
				
			}
			
			function onVerificarArchivo()
			{
				var respuesta = document.importarIniciativasForm.respuesta.value;

				if (document.importarIniciativasForm.tipoFuente.value == "1" || document.importarIniciativasForm.tipoFuente.value == "2")
				{
					if (respuesta == "Error")
					{
						alert('<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.verificar.archivo.error" /> ');
						return;
					}
					
					var tablaListaInsumos = document.getElementById('tablaListaInsumos');
					
					
					
					// Se borra la lista de insumos
					while (tablaListaInsumos.getElementsByTagName("tr").length > 0) 
						tablaListaInsumos.deleteRow(0);
	
					var insumos = respuesta.split(',');
					var valores;
					var imagen;
					var hayCodigoOrganizacion = false;
					var hayCodigoIniciativa = false;
					var hayNombre = false;
					var hayDescripcion = false;
					var hayTipoProyecto = false;
					var hayAnio = false;
					var hayFrecuencia = false;
					var hayTipoMedicon = false;
					var hayAlertaVerde = false;
					var hayAlertaAmarilla = false;
					var hayCrearCuentas = false;
					var hayUnidadMedida = false;
										
					for (var i = 0; i < insumos.length; i++) 
					{
						var numFilas = tablaListaInsumos.getElementsByTagName("tr").length;
						var tbody = tablaListaInsumos.getElementsByTagName("TBODY")[0];
						var row = document.createElement("TR");
						row.valign = "top";
						var td1 = document.createElement("TD");
						var td2 = document.createElement("TD");

						if (insumos[i].length > 0) 
						{
							imagen = "No.gif";
							valores = insumos[i].split('=');
												
							if (valores[0].toUpperCase() == "CODIGO ORGANIZACION" && valores[1] == "true")
							{
								hayCodigoOrganizacion = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "CODIGO PROYECTO" && valores[1] == "true")
							{
								hayCodigoIniciativa = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "NOMBRE CORTO" && valores[1] == "true")
							{
								hayNombre = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "DESCRIPCION" && valores[1] == "true")
							{
								hayDescripcion = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "TIPO PROYECTO" && valores[1] == "true")
							{
								hayTipoProyecto = true;
								imagen = "Si.png";
							
							}
							if (valores[0].toUpperCase() == "ANIO" && valores[1] == "true")
							{
								hayAnio = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "FRECUENCIA" && valores[1] == "true")
							{
								hayFrecuencia = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "TIPO MEDICION" && valores[1] == "true")
							{
								hayTipoMedicon = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "ALERTA VERDE" && valores[1] == "true")
							{
								hayAlertaVerde = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "ALERTA AMARILLA" && valores[1] == "true")
							{
								hayAlertaAmarilla = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "CREAR CUENTAS PRESUPUESTO" && valores[1] == "true")
							{
								hayCrearCuentas = true;
								imagen = "Si.png";
							}
							if (valores[0].toUpperCase() == "UNIDAD MEDIDA PRESUPUESTO" && valores[1] == "true")
							{
								hayUnidadMedida = true;
								imagen = "Si.png";
							}
								
							td1.innerHTML = "<img src=\"<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/" + imagen +"'/>\" border=\"0\" width=\"16\" height=\"16\" />";
							td2.appendChild(document.createTextNode(valores[0]));
							td2.style.color = "blue";
							
							row.appendChild(td1);
							row.appendChild(td2);
							tbody.appendChild(row);
						}
					}
					
					var fileName = document.getElementById("file_browse_text");
					if (fileName != null)
						fileName.value = document.importarIniciativasForm.fuenteSeleccion.value;
					
					if (hayCodigoOrganizacion && hayCodigoIniciativa && hayNombre && hayDescripcion && hayTipoProyecto && hayAnio && hayFrecuencia && hayTipoMedicon && hayAlertaVerde && hayAlertaAmarilla)
						_archivoVerificado = true;
					else
						alert('<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.verificar.archivo.invalido" /> ');
				}				
				_verificarTabla = false;
			}
						

			function selectRowColors(indexRow, oTable, fieldRowSelected, colorSelected, bgColorSelected, colorNoSelected, bgColorNoSelected) 
			{
				var rowSelected = fieldRowSelected.value / 1;
				var i = 0;

				indexRow = indexRow / 1;

				if (oTable.getElementsByTagName("tr").item(rowSelected) != null) 
				{
					for (i = 0; i < oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").length; i++) 
					{
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.color = colorNoSelected;
						oTable.getElementsByTagName("tr").item(rowSelected).getElementsByTagName("td").item(i).style.backgroundColor = bgColorNoSelected;
					};
				}
			
				for (i = 0; i < oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").length; i++) 
				{
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.color = colorSelected;
					oTable.getElementsByTagName("tr").item(indexRow).getElementsByTagName("td").item(i).style.backgroundColor = bgColorSelected;
				}
			
				fieldRowSelected.value = indexRow;
			}
			
			
			
			function eventoOnClickTodosLosPlanes()
			{
				limpiarPlan();				
			}
			
			function onClose()
			{
				if (_setCloseParent)
					cancelar();
			}
			
			function eventoCambioFuente(limpiar)
			{
				if (limpiar)
					limpiarArchivo();
				var tr = document.getElementById("trTipoPlan");
				tr.style.display = "none";
				tr = document.getElementById("trTipoExcel");
				tr.style.display = "none";
				tr = document.getElementById("trTipoOthers");
				tr.style.display = "none";
				
				switch (document.importarIniciativasForm.tipoFuente.value) 
				{
					case "1":
						tr = document.getElementById("trTipoPlan");
						tr.style.display = "";
						break;
					case "2":
						tr = document.getElementById("trTipoExcel");
						tr.style.display = "";
						break;
					case "3":
					case "4":
					case "5":
						tr = document.getElementById("trTipoOthers");
						tr.style.display = "";
						break;
				}
			}
			
			function chequearArchivo()
			{
				_verificarTabla = true;
				verificarArchivo();
			}			
		</script>

		<%-- Funciones JavaScript externas de la pï¿½gina Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>

		<%-- Forma asociada al Action - Jsp --%>
		<html:form action="/iniciativas/importar" styleClass="formaHtmlCompleta" enctype="multipart/form-data" method="POST">
		
			<html:hidden property="respuesta" />
			<html:hidden property="status" />		
			<html:hidden property="fuenteSeleccionArchivo" />
			<html:hidden property="fuenteSeleccion" />
			
			<input type="hidden" name="insumoSeleccionado" id="insumoSeleccionado">
			
			<%-- Campos hidden para el manejo de la insumos --%>
			<vgcinterfaz:contenedorForma width="570px" height="440px" bodyAlign="center" bodyValign="center" scrolling="none">
				<vgcinterfaz:contenedorFormaTitulo nombre="tituloFicha">..::</vgcinterfaz:contenedorFormaTitulo>
					<table class="bordeFichaDatostabla bordeFichaDatos">
						<tr height=355px>
	
							<%-- Imï¿½gen del asistente --%>
							<td width="315px">
								<img src="<html:rewrite page='/paginas/strategos/iniciativas/importacion/imagenes/WZImportacion.gif'/>" border="0" width="150px" height="330px">
							</td>
	
							<td>
								<vgcinterfaz:contenedorPaneles height="340px" width="390px" nombre="contenedorPanelesAsistente" mostrarSelectoresPaneles="false">
	
									<%-- Panel Presentacion --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelPresentacion" mostrarBorde="false">
										<table class="tablaSpacing0Padding0Width100Collapse">
											<tr>
												<td>
													<table class="tabla contenedorBotonesSeleccion">
														<tr>
															<td>
																<b><vgcutil:message key="jsp.asistente.importacion.presentacion.importante" /></b><br><br> 
																<vgcutil:message key="jsp.asistente.importacion.iniciativa.presentacion.1" /><br><br>
																<vgcutil:message key="jsp.asistente.importacion.iniciativa.presentacion.2" /><br><br>
																<vgcutil:message key="jsp.asistente.importacion.presentacion.3" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													
													<br><br><br>
												</td>
											</tr>
											<tr>
												<td class="contenedorTextoSeleccion">
													<html:checkbox property="showPresentacion" styleClass="botonSeleccionMultiple">
														<vgcutil:message key="jsp.asistente.importacion.presentacion.mostrar" />
													</html:checkbox>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>

									<%-- Panel Tipo de Importacion --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelSeleccionTipo" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.tipos" /></td>
												<td>
													<html:select property="tipoFuente"
														styleClass="cuadroTexto" size="1"
														onchange="eventoCambioFuente(true)">
														<html:optionsCollection property="tiposFuentes"
															value="id" label="nombre" />
													</html:select>
													<br><br>
												</td>
											</tr>
											<tr id="trTipoPlan" style="display:none">
												<td colspan="3">
													<table class="tabla contenedorBotonesSeleccion">
														<tr valign="top">
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.separador" /></td>
															<td>
																<html:text property="separador" size="5" maxlength="1" styleClass="cuadroTexto"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr id="trTipoExcel" style="display:none">
												<td colspan="3">
													<table class="tabla contenedorBotonesSeleccion">
														<tr valign="top">
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.excel.tipo" /></td>
														</tr>
														<tr>
															<td>
																<html:radio property="excelTipo" value="0">
																	<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.excel.tipo.2003" />
																</html:radio>
															</td>
														</tr>
														<tr>
															<td>
																<html:radio property="excelTipo" value="1">
																	<vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.excel.tipo.2010" />
																</html:radio>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr id="trTipoOthers" style="display:none">
												<td colspan="3">
													<table class="tabla contenedorBotonesSeleccion">
														<tr valign="top">
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.titulo" /></td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.nombre" /></td>
															<td>
																<html:text property="bdNombre" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.usuario" /></td>
															<td>
																<html:text property="bdUsuario" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.password" /></td>
															<td>
																<html:password property="bdPassword" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.servidor" /></td>
															<td>
																<html:text property="bdServidor" size="15" maxlength="50" styleClass="cuadroTexto"/>
															</td>
														</tr>
														<tr>
															<td colspan="2"><vgcutil:message key="jsp.asistente.importacion.archivo.seleccion.bd.puerto" /></td>
															<td>
																<html:text property="bdPuerto" size="10" maxlength="6" styleClass="cuadroTexto"/>
															</td>
														</tr>
													</table>
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
									
									<%-- Panel Verificar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelVerficar" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.titulo" />
												</td>
											</tr>
											<tr>
												<td>
													<input type="text" readonly style="width: 250px;" id="file_browse_text" />
												</td>
												<td style="width: 80px;">
													<div id="file_browse_wrapper">
														<input type="file" id="file_browse" name="file_browse" title="<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.archivo" />" onchange="seleccionarArchivo(this);" />
													</div>
												</td>													
												<td style="width: 30px;">
													<img style="cursor: pointer" onclick="limpiarArchivo();" src="<html:rewrite page='/componentes/calendario/eliminar.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.limpiar.alt" />">
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.tabla" />
												</td>
											</tr>
											<tr height="200px" valign="top">
												<td colspan="2">
													<table class="tablainsumo contenedorBotonesSeleccion">
														<tr valign="top">
															<td>
																<table id="tablaListaInsumos">
																	<tbody class="cuadroTexto">
																	</tbody>
																</table>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr align="left">
												<td>
													<input id="btnVerificar" name="btnVerificar" type="button" style="width:150px" class="cuadroTexto" value="<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.verificar" />" onclick="verificarArchivo();" disabled="false">
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
									
									<%-- Panel Verificar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50px" nombre="panelVerficarBD" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr valign="top" align="left">
												<td>
													<input id="btnVerificar" name="btnVerificar" type="button" style="width:150px" class="cuadroTexto" value="<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.verificar" />" onclick="verificarArchivo();">
												</td>
											</tr>
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.medicion.seleccion.seleccionar.tabla.bd" />
												</td>
											</tr>
											<tr height="200px" valign="top">
												<td colspan="2">
													<table class="tablainsumo contenedorBotonesSeleccion">
														<tr valign="top">
															<td>
																<div style="overflow:auto; height:200px; padding:0">
																	<table id="tablaListaInsumosBd">
																		<tbody class="cuadroTexto">
																		</tbody>
																	</table>
																</div>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td colspan="3">
													<html:text property="bdTabla" size="30" styleClass="cuadroTexto" disabled="false"/>
													<img style="cursor: pointer" onclick="chequearArchivo();" src="<html:rewrite page='/componentes/fichaDatos/selector.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="boton.chequear.alt"/>">&nbsp;
													<img id="bdStatusCheck" style="cursor: pointer" src="<html:rewrite page='/paginas/strategos/indicadores/importacion/imagenes/No.gif'/>" border="0" width="16" height="16">
												</td>
											</tr>
										</table>
									</vgcinterfaz:panelContenedor>
																		
									
									<%-- Panel Calcular --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelCalcular" mostrarBorde="false">
										<table class="tabla contenedorBotonesSeleccion">
											<tr>
												<td>
													<b><vgcutil:message key="jsp.asistente.importacion.calcular.mensaje1" /></b><br><br> 
													<vgcutil:message key="jsp.asistente.importacion.calcular.mensaje2" /><br>
													<vgcutil:message key="jsp.asistente.importacion.calcular.mensaje3" /><br>
												</td>
											</tr>
											<tr>
												<td>
													<br><br><br>
													<br><br><br>
													<br><br><br>
												</td>
											</tr>
											<%-- 
											<tr>
												<td>
													<vgcutil:message key="jsp.asistente.importacion.calcular" />
												</td>
											</tr>
											<tr>
												<td>
													<table class="tabla contenedorBotonesSeleccion">
														<tr>
															<td colspan="2">
																<vgcutil:message key="jsp.asistente.importacion.calcular.titulo" />
															</td>
														</tr>
														<tr>
															<td>
																<html:checkbox property="calcularMediciones" styleClass="botonSeleccionMultiple" >
																	<vgcutil:message key="jsp.asistente.importacion.calcular.mediciones" />
																</html:checkbox>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											--%>
										</table>
									</vgcinterfaz:panelContenedor>
									
									<%-- Panel Finalizar --%>
									<vgcinterfaz:panelContenedor anchoPestana="50" nombre="panelFin" mostrarBorde="false">
									<table class="tabla contenedorBotonesSeleccion">
										<tr>
											<td><b>Se van a importar iniciativas</b><br>
											<br> Presione finalizar para empezar la importación de
												las iniciativas<br>
											<br></td>
										</tr>
										<tr>
											<td><vgcutil:message
													key="jsp.asistente.importacion.fin.reportar" /></td>
										</tr>
										<tr>
											<td>
												<table class="tabla contenedorBotonesSeleccion">
													<tr>
														<td colspan="2"><vgcutil:message
																key="jsp.asistente.importacion.fin.reportar.titulo" />
														</td>
													</tr>
													<tr>
														<td><html:checkbox property="logMediciones"
																styleClass="botonSeleccionMultiple">
																	Iniciativas importadas
																</html:checkbox></td>
														<td><html:checkbox property="logErrores"
																styleClass="botonSeleccionMultiple">
																<vgcutil:message
																	key="jsp.asistente.importacion.fin.reportar.errores" />
															</html:checkbox></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</vgcinterfaz:panelContenedor>
	
								</vgcinterfaz:contenedorPaneles>
							</td>
						</tr>
					</table>
				<vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraBotones">
				</vgcinterfaz:contenedorFormaBarraInferior>
			</vgcinterfaz:contenedorForma>
		</html:form>
		<script>
			//Inicio
			<logic:equal name="importarIniciativasForm" property="status" value="0">
				if (document.importarIniciativasForm.showPresentacion.checked)
					_pasoActual = 2;
				eventoCambioFuente(true);
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				init();
			</logic:equal>
			
			//Verificar Conexion, Archivo y Tabla
			<logic:equal name="importarIniciativasForm" property="status" value="2">
				_pasoActual=3;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				eventoCambioFuente(false);
				onVerificarArchivo();
			</logic:equal>

			// Importar
			<logic:equal name="importarIniciativasForm" property="status" value="4">
				_pasoActual=6;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				desactivarBloqueoEspera();
				if (document.importarIniciativasForm.respuesta.value == "Successful")
					_setCloseParent = true;
				else
					onSalvar();
			</logic:equal>
			
			// Servicio no configurado
			<logic:equal name="importarIniciativasForm" property="status" value="5">
				_setCloseParent = true;
			</logic:equal>
			
			// Leer configuracion
			<logic:equal name="importarIniciativasForm" property="status" value="7">
				_pasoActual=3;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				eventoCambioFuente(false);
			</logic:equal>

			// File Error
			<logic:equal name="importarIniciativasForm" property="status" value="8">
				_pasoActual=5;
				mostrarTitulo();
				mostrarBotones(_pasoActual);
				onSalvar();
			</logic:equal>
			
		</script>
	</tiles:put>
</tiles:insert>
