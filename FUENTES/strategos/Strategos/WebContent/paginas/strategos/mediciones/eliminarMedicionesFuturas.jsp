<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-logica" prefix="vgclogica"%>

<%-- Modificado por: Kerwin Arias (3/11/2022) --%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<tiles:insert definition="doc.modalWindowLayout" flush="true">

	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.configuraredicionmediciones.titulo" />
	</tiles:put>

	<tiles:put name="body" type="String">

		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>

		<script type="text/javascript">
			var _setCloseParent = false;
			var errMsRango = '<vgcutil:message key="jsp.asistente.grafico.rango.alerta.invalido" />';

			var hastaAno = '<bean:write name="editarMedicionesForm" property="anoHasta" ignore="true"/>';
			var hastaMes = 12;

			function init() {
				eventoCambioFrecuencia();
				inicializarSeriesTiempo();
				<logic:equal name="editarMedicionesForm" property="desdePlanificacion" value="true">

				document.getElementById('selectPeriodoFinal').value = document.editarMedicionesForm.periodoHasta.value;
				document.getElementById('selectDefecto').value = document.editarMedicionesForm.periodoHasta.value;
				validarRango(document.editarMedicionesForm.anoDesde,
						document.editarMedicionesForm.anoHasta, document
								.getElementById('selectPeriodoInicial'),
						document.getElementById('selectPeriodoFinal'),
						errMsRango)
				</logic:equal>
			}

			function agregarPeriodos(frecuencia, limpiar) {
				if (limpiar == undefined)
					limpiar = false;

				if (frecuencia != 0 && frecuencia != 8) {
					var index = obtenerPeriodoActual(frecuencia);
				}

				var select = document.getElementById("selectPeriodoFinal");
				var max = select.options.length;
				if (max > 0)
					select.options.length = 0;

				for (var i = 0; i < numeroPeriodoMaximo(frecuencia); i++)
					addElementToSelect(select, i + 1, i + 1);

				if (frecuencia != 0 && frecuencia != 8) {
					select.selectedIndex = index;
				} else {
					select.selectedIndex = select.options.length - 1;
				}
			}

			function obtenerPeriodoActual(frecuencia) {
				var fechaHoy = new Date();
				var mes = fechaHoy.getMonth() + 1;
				var dia = fechaHoy.getDate();
				var año = fechaHoy.getFullYear();

				var fecha1 = año + "-" + "01" + "-" + "01";
				var fecha2 = año + "-" + mes + "-" + dia;

				var fechaInicio = new Date(fecha1).getTime();
				var fechaFin = new Date(fecha2).getTime();

				var mil = 86400000;
				var dif = fechaFin - fechaInicio;
				var dias = dif / mil;

				switch (frecuencia) {
				case "0":
					return 365;
					//diaria
				case "1":
					var sem = dias / 7;
					//semanal
					return Math.round(sem) - 1;
				case "2":
					if (mes > 1) {
						var semanas = (mes - 1) * 2;
						if (dia > 15) {
							semanas += 2;
						} else if (dia <= 15) {
							semanas += 1;
						}

						return semanas - 1;

					} else {

						if (dia > 15) {
							return 1;
						} else if (dia <= 15) {
							return 0;
						}
					}
					//quincenal

				case "3":
					//mensual
					return mes - 1;
				case "4":
					//bimestral
					if (mes <= 2) {
						return 0;
					} else if (mes > 2 && mes <= 4) {
						return 1;
					} else if (mes > 4 && mes <= 6) {
						return 2;
					} else if (mes > 6 && mes <= 8) {
						return 3;
					} else if (mes > 8 && mes <= 10) {
						return 4;
					} else if (mes > 10) {
						return 5;
					}

				case "5":

					if (mes <= 3) {
						return 0;
					} else if (mes > 3 && mes <= 6) {
						return 1;
					} else if (mes > 6 && mes <= 9) {
						return 2;
					} else if (mes > 9) {
						return 3;
					}
					//trimestral

				case "6":

					if (mes <= 4) {
						return 0;
					} else if (mes > 4 && mes <= 8) {
						return 1;
					} else if (mes > 8) {
						return 2;
					}

					//cuatrimestral

				case "7":
					if (mes <= 6) {
						return 0;
					} else if (mes > 6) {
						return 1;
					}
					//semestral

				case "8":
					//anual
					return 1;
				}
			}

			function numeroPeriodoMaximo(frecuencia) {
				switch (frecuencia) {
				case "0":
					return 365;
				case "1":
					return 52;
				case "2":
					return 24;
				case "3":
					return 12;
				case "4":
					return 6;
				case "5":
					return 4;
				case "6":
					return 3;
				case "7":
					return 2;
				case "8":
					return 1;
				}
				return -1;
			}

			function addElementToSelect(combo, texto, valor) {
				var idxElemento = combo.options.length; //Numero de elementos de la combo si esta vacio es 0

				//Este indice será el del nuevo elemento
				combo.options[idxElemento] = new Option();
				combo.options[idxElemento].text = texto; //Este es el texto que verás en la combo
				combo.options[idxElemento].value = valor; //Este es el valor que se enviará cuando hagas un submit del
			}

			function eventoCambioFrecuencia() {
				document.getElementById("trPeriodoFinal").style.display = "";

				switch (document.editarMedicionesForm.frecuencia.value) {
				case "0":

					document.getElementById("trPeriodoFinalDate").style.display = "";

					document.getElementById("trPeriodoFinal").style.display = "none";

					document.getElementById("trAnoFinal").style.display = "none";
					break;
				case "1":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semana" />';
					break;
				case "2":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.quincena" />';
					break;
				case "3":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.mes" />';
					break;
				case "4":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.bimestre" />';
					break;
				case "5":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.trimestre" />';
					break;
				case "6":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.cuatrimestre" />';
					break;
				case "7":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.semestre" />';
					break;
				case "8":

					document.getElementById("trPeriodoFinalDate").style.display = "none";

					document.getElementById("trPeriodoFinal").style.display = "";

					document.getElementById("trAnoFinal").style.display = "";

					var periodo = document.getElementById("tdPeriodoFinal");
					periodo.innerHTML = '<vgcutil:message key="jsp.asistente.grafico.ano" />';

					document.getElementById("trPeriodoFinal").style.display = "none";
					break;
				}

				agregarPeriodos(document.editarMedicionesForm.frecuencia.value);
			}

			function editarMediciones() {

				document.editarMedicionesForm.periodoHasta.value = document
						.getElementById('selectPeriodoFinal').value;				

				var xml = '&funcion=eliminar';
				activarBloqueoEspera();
				document.editarMedicionesForm.action = '<html:rewrite action="/mediciones/eliminarMedicionesFuturas"/>?anio=' + document.editarMedicionesForm.anoHasta.value
						+ xml + '&source=' + document.editarMedicionesForm.sourceScreen.value;
				document.editarMedicionesForm.submit();
			}

			function onAceptar() {
				this.opener.document.<bean:write name="editarMedicionesForm" property="nombreForma" scope="session" />.<bean:write name="editarMedicionesForm" property="nombreCampoOculto" scope="session" />.value = "Sucess";
				this.opener.<bean:write name="editarMedicionesForm" property="funcionCierre" scope="session" />;
				cancelar();
			}

			function cancelar() {
				this.close();
			}

			function onClose() {
				if (_setCloseParent)
					cancelar();
			}
		</script>


		<%-- Funciones JavaScript externas de la página Jsp --%>
		<jsp:include flush="true" page="/componentes/ajax/ajaxJs.jsp"></jsp:include>


		<html:form action="mediciones/eliminarMedicionParametros"
			focus="frecuencia" styleClass="formaHtml" style="overflow:hidden;"
			enctype="multipart/form-data" method="POST">

			<html:hidden property="numeroMaximoPeriodos" />
			<html:hidden property="iniciativaId" />
			<html:hidden property="organizacionId" />
			<html:hidden property="desdePlanificacion" />
			<html:hidden property="sourceScreen" />
			<html:hidden property="periodoDesde" />
			<html:hidden property="periodoHasta" />
			<html:hidden property="respuesta" />
			<html:hidden property="status" />
			<html:hidden property="desdeReal" />
			<html:hidden property="esAdmin" />
			<html:hidden property="anioFinal" />

			<input type="hidden" name="periodoDesdeAnt" value="1" />
			<input type="hidden" name="periodoHastaAnt" value="1" />

			<bean:define id="mostrarSeleccion" value="false"></bean:define>
			<logic:notEmpty name="editarMedicionesForm" property="claseId">
				<logic:equal name="editarMedicionesForm" property="desdeIndicador"
					value="true">
					<bean:define id="mostrarSeleccion" value="true"></bean:define>
				</logic:equal>
			</logic:notEmpty>
			<logic:notEmpty name="editarMedicionesForm" property="perspectivaId">
				<logic:equal name="editarMedicionesForm" property="desdeIndicador"
					value="true">
					<bean:define id="mostrarSeleccion" value="true"></bean:define>
				</logic:equal>
			</logic:notEmpty>
			<bean:define id="altoContenedor" value="400px"></bean:define>
			<logic:equal name="editarMedicionesForm"
				property="desdePlanificacion" value="true">
				<bean:define id="mostrarSeleccion" value="false"></bean:define>
				<bean:define id="altoContenedor" value="260px"></bean:define>
			</logic:equal>
			<logic:notEqual name="editarMedicionesForm"
				property="desdePlanificacion" value="true">
				<logic:equal name="mostrarSeleccion" value="true">
					<bean:define id="altoContenedor" value="500px"></bean:define>
				</logic:equal>
			</logic:notEqual>
			<bean:define id="bloquearPeriodosIniciales" toScope="page">
				<logic:equal name="editarMedicionesForm"
					property="desdePlanificacion" value="true">
					true
				</logic:equal>
				<logic:notEqual name="editarMedicionesForm"
					property="desdePlanificacion" value="true">
					false
				</logic:notEqual>
			</bean:define>

			<vgcinterfaz:contenedorForma width="400px" height="380"
				bodyAlign="center" bodyValign="middle" marginTop="10px"
				scrolling="hidden">

				<%-- Título --%>
				<vgcinterfaz:contenedorFormaTitulo>..::					
						<vgcutil:message key="jsp.configuraredicionmediciones.titulo" />
				</vgcinterfaz:contenedorFormaTitulo>

				<table class="bordeFichaDatos">
					<%-- Organización --%>
					<tr>
						<td align="right" valign="top"><b><vgcutil:message
									key="jsp.configuraredicionmediciones.ficha.organizacion" /></b></td>
						<td colspan="3"><bean:write name="editarMedicionesForm"
								property="organizacion" />&nbsp;</td>
					</tr>
					<logic:notEmpty name="editarMedicionesForm"
						property="perspectivaId">
						<%-- Plan --%>
						<tr>
							<td align="right" valign="top"><b><bean:write
										name="editarMedicionesForm" property="nombreObjetoPerspectiva" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm"
									property="perspectivaNombre" />&nbsp;</td>
						</tr>
					</logic:notEmpty>
					<logic:equal name="editarMedicionesForm" property="desdeIndicador"
						value="true">
						<%-- Clase de Indicadores --%>
						<tr>
							<td align="right"><b><vgcutil:message
										key="jsp.configuraredicionmediciones.ficha.clase" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm"
									property="clase" />&nbsp;</td>
						</tr>
					</logic:equal>
					<logic:equal name="editarMedicionesForm"
						property="desdePlanificacion" value="true">
						<%-- Actividades --%>
						<tr>
							<td align="right"><b><vgcutil:message
										key="jsp.configuraredicionmediciones.ficha.iniciativa" /></b></td>
							<td colspan="3"><bean:write name="editarMedicionesForm"
									property="iniciativa" />&nbsp;</td>
						</tr>
					</logic:equal>
					<tr>
						<td align="right"><b><vgcutil:message
									key="jsp.configuraredicionmediciones.ficha.frecuencia" /></b></td>
						<td align="left"><html:select property="frecuencia"
								styleClass="cuadroTexto" onchange="eventoCambioFrecuencia();"
								disabled="<%=Boolean.parseBoolean(bloquearPeriodosIniciales)%>">

								<html:optionsCollection property="frecuencias"
									value="frecuenciaId" label="nombre" />
							</html:select></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					
					<tr>
						<td colspan="4">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="4"><b><vgcutil:message
									key="jsp.configuraredicionmediciones.ficha.periodo.eliminar" /></b></td>
					</tr>


					<tr>
						<bean:define id="anoInicial" toScope="page">
							<bean:write name="editarMedicionesForm" property="anoInicial" />
						</bean:define>
						<bean:define id="anoFin" toScope="page">
							<bean:write name="editarMedicionesForm" property="anoFinal" />
						</bean:define>

						<td width="100%" colspan="2">
							<table class="tabla contenedorBotonesSeleccion">
								<tr id="trAnoFinal">
									<td><vgcutil:message
											key="jsp.configuraredicionmediciones.ficha.ano" /></td>
									<td><bean:define id="anoCalculoFinal" toScope="page">
											<bean:write name="editarMedicionesForm" property="anoHasta" />
										</bean:define> <html:select property="anoHasta" value="<%=anoCalculoFinal%>"
											styleClass="cuadroTexto">
											<%
											for (int i = Integer.parseInt(anoInicial); i <= Integer.parseInt(anoFin); i++) {
											%>
											<html:option value="<%=String.valueOf(i)%>">
												<%=i%>
											</html:option>
											<%
											}
											%>
										</html:select></td>
								</tr>
								<tr id="trPeriodoFinal">
									<td><span id="tdPeriodoFinal"></span></td>
									<td><select id="selectPeriodoFinal" class="cuadroTexto">
											<option selected id="selectDefecto"></option>
									</select></td>
								</tr>
								<tr id="trPeriodoFinalDate">
									<td><vgcutil:message
											key="jsp.configuraredicionmediciones.ficha.dia" /></td>
									<td><html:text property="fechaHasta" size="12"
											maxlength="10" styleClass="cuadroTexto"
											disabled="<%=Boolean.parseBoolean(bloquearPeriodosIniciales)%>" />
									</td>
								</tr>

							</table>
						</td>
					</tr>


					<logic:equal name="mostrarSeleccion" value="true">
						<%-- Espacio en Blanco --%>
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<!-- Campo: Solo Seleccionados / Todos los Seleccionados -->
						<tr>
							<td colspan="4">
								<table class="contenedorBotonesSeleccion" width="100%"
									cellpadding="3" cellspacing="3">
									<tr>
										<td colspan="2"><b><vgcutil:message
													key="jsp.configuraredicionmetas.ficha.seleccion" /></b></td>
									</tr>
									<tr>
										<td width="20px" align="center"><logic:equal
												name="editarMedicionesForm" property="soloSeleccionados"
												value="true">
												<input type="radio" name="soloSeleccionados" value="true"
													class="botonSeleccionSimple" checked>
											</logic:equal> <logic:notEqual name="editarMedicionesForm"
												property="soloSeleccionados" value="true">
												<input type="radio" name="soloSeleccionados" value="true"
													class="botonSeleccionSimple">
											</logic:notEqual></td>
										<td><b><vgcutil:message
													key="jsp.configuraredicionmediciones.ficha.seleccion.soloseleccionados" /></b></td>
									</tr>
									<tr>
										<logic:equal name="editarMedicionesForm"
											property="desdeIndicadorOrg" value="false">
											<td width="20px" align="center"><logic:equal
													name="editarMedicionesForm" property="soloSeleccionados"
													value="true">
													<input type="radio" name="soloSeleccionados" value="false"
														class="botonSeleccionSimple">
												</logic:equal> <logic:notEqual name="editarMedicionesForm"
													property="soloSeleccionados" value="true">
													<input type="radio" name="soloSeleccionados" value="false"
														class="botonSeleccionSimple" checked>
												</logic:notEqual></td>
											<logic:notEmpty name="editarMedicionesForm"
												property="perspectivaId">
												<td><b><vgcutil:message
															key="jsp.configuraredicionmediciones.ficha.seleccion.todosperspectiva" /></b></td>
											</logic:notEmpty>

											<logic:equal name="editarMedicionesForm"
												property="desdeIndicador" value="true">
												<logic:empty name="editarMedicionesForm"
													property="perspectivaId">
													<td><b><vgcutil:message
																key="jsp.configuraredicionmediciones.ficha.seleccion.todosclase" /></b></td>
												</logic:empty>
											</logic:equal>

											<logic:equal name="editarMedicionesForm"
												property="desdePlanificacion" value="true">
												<td><b><vgcutil:message
															key="jsp.configuraredicionmediciones.ficha.seleccion.todos.iniciativas" /></b>
												</td>
											</logic:equal>
										</logic:equal>

									</tr>
								</table>
							</td>
						</tr>
					</logic:equal>

					<logic:empty name="editarMedicionesForm" property="indicadores"
						scope="session">
						<tr>
							<td colspan="4"><b>&nbsp;</b></td>
						</tr>
						<tr>
							<td colspan="4"><html:checkbox
									property="visualizarIndicadoresCompuestos"></html:checkbox>&nbsp;<vgcutil:message
									key="jsp.configuraredicionmediciones.visualizarindicadorescompuestos" /></td>
						</tr>
					</logic:empty>
				</table>


				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					<img
						src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>"
						border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'"
						href="javascript:editarMediciones();"
						class="mouseFueraBarraInferiorForma"><vgcutil:message
							key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;				
						
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
		<script>
			<logic:equal name="editarMedicionesForm" property="status" value="0">
			init();
			</logic:equal>
		</script>
	</tiles:put>


</tiles:insert>