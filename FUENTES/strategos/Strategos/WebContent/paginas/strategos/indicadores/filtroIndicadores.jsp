<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%-- Modificado por: Andres Martinez (21/02/2024) --%>

<link rel="stylesheet" type="text/css" href="<html:rewrite page="/componentes/estilos/botones.css" />" media="screen"/>
<script type="text/javascript">
	var NO_MARCADO = 1;
	var MARCADO = 2;
</script>

<%-- Filtro --%>
<table id="tblFiltroInd" class="tablaSpacing0Padding0Width100Collapse" style="display: none; padding: 1px; width: 420px;">
	<tr class="barraFiltrosForma">
		<td style="width: 185px;">
			<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
				<%-- Frecuencia --%>			
				<tr class="barraFiltrosForma" style="height: 20px">
					<td style="width: 50px;"><vgcutil:message key="jsp.seleccionarindicador.filtrofrecuencia" /></td>
					<td style="width: 50px;">
						<bean:define id="filtroFrecuenciaValue">
							<logic:notEmpty name="gestionarIndicadoresForm">
								<logic:notEmpty name="gestionarIndicadoresForm" property="frecuencia">
									<bean:write name="gestionarIndicadoresForm" property="frecuencia" />
								</logic:notEmpty>
								<logic:empty name="gestionarIndicadoresForm" property="frecuencia">
									1000
								</logic:empty>
							</logic:notEmpty>
							<logic:empty  name="gestionarIndicadoresForm">
								<logic:notEmpty name="gestionarIndicadoresPlanForm">
									<logic:notEmpty name="gestionarIndicadoresPlanForm" property="frecuencia">
										<bean:write name="gestionarIndicadoresPlanForm" property="frecuencia" />
									</logic:notEmpty>
									<logic:empty name="gestionarIndicadoresPlanForm" property="frecuencia">
										1000
									</logic:empty>
								</logic:notEmpty>							
							</logic:empty>
						</bean:define>				
						<select class="cuadroCombinado" name="selectFrecuencia" id="selectFrecuencia">
							<option value="1000"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
							<logic:notEmpty name="gestionarIndicadoresForm">
								<logic:iterate id="filtrosFrec" name="gestionarIndicadoresForm" property="frecuencias">
									<bean:define id="frecuenciaId" toScope="page" name="filtrosFrec" property="frecuenciaId" type="Byte"></bean:define>
									<bean:define id="nombre" toScope="page"><bean:write name='filtrosFrec' property='nombre' /></bean:define>
									<logic:equal name="filtrosFrec" property="frecuenciaId" value="<%=filtroFrecuenciaValue%>">
										<option value="<%=frecuenciaId%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='filtrosFrec' property='frecuenciaId' value='<%=filtroFrecuenciaValue.toString()%>'>
										<option value="<%=frecuenciaId%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</logic:notEmpty>
							
							<logic:empty name="gestionarIndicadoresForm">
								<logic:notEmpty name="gestionarIndicadoresPlanForm">
									<logic:iterate id="filtrosFrec" name="gestionarIndicadoresPlanForm" property="frecuencias">
										<bean:define id="frecuenciaId" toScope="page" name="filtrosFrec" property="frecuenciaId" type="Byte"></bean:define>
										<bean:define id="nombre" toScope="page"><bean:write name="filtrosFrec" property="nombre" /></bean:define>
										<logic:equal name="filtrosFrec" property="frecuenciaId" value="<%=filtroFrecuenciaValue%>">
											<option value="<%=frecuenciaId%>" selected><%=nombre%></option>
										</logic:equal>
										<logic:notEqual name='filtrosFrec' property='frecuenciaId' value='<%=filtroFrecuenciaValue.toString()%>'>
											<option value="<%=frecuenciaId%>"><%=nombre%></option>
										</logic:notEqual>
									</logic:iterate>
								</logic:notEmpty>
							</logic:empty>
						</select>		
					</td>
				</tr>		
				
				<%-- Unidad Medida --%>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td style="width: 50px;"><vgcutil:message key="jsp.seleccionarindicador.filtrounidadmedida" /></td>
					<td style="width: 50px;">
						<bean:define id="filtroUnidadValue">
							<logic:notEmpty name="gestionarIndicadoresForm">
								<logic:notEmpty name="gestionarIndicadoresForm" property="unidadId">
									<bean:write name="gestionarIndicadoresForm" property="unidadId" />
								</logic:notEmpty>
								<logic:empty name="gestionarIndicadoresForm" property="unidadId">
									0
								</logic:empty>
							</logic:notEmpty>
							<logic:empty  name="gestionarIndicadoresForm">
								<logic:notEmpty name="gestionarIndicadoresPlanForm">
									<logic:notEmpty name="gestionarIndicadoresPlanForm" property="unidadId">
										<bean:write name="gestionarIndicadoresPlanForm" property="unidadId" />
									</logic:notEmpty>
									<logic:empty name="gestionarIndicadoresPlanForm" property="unidadId">
										0
									</logic:empty>
								</logic:notEmpty>							
							</logic:empty>
						</bean:define>	
						<select class="cuadroCombinado" name="selectUnidad" id="selectUnidad">
							<option value="0"><vgcutil:message key="jsp.gestionariniciativas.filtro.historico.todos" /></option>
							<logic:notEmpty name="gestionarIndicadoresForm">
								<logic:iterate id="filtrosUni" name="gestionarIndicadoresForm" property="unidadesMedida">
									<bean:define id="unidadId" toScope="page" name="filtrosUni" property="unidadId" type="Long"></bean:define>
									<bean:define id="nombre" toScope="page"><bean:write name='filtrosUni' property='nombre' /></bean:define>
									<logic:equal name="filtrosUni" property="unidadId" value="<%=filtroUnidadValue%>">
										<option value="<%=unidadId%>" selected><%=nombre%></option>
									</logic:equal>
									<logic:notEqual name='filtrosUni' property='unidadId' value='<%=filtroUnidadValue.toString()%>'>
										<option value="<%=unidadId%>"><%=nombre%></option>
									</logic:notEqual>
								</logic:iterate>
							</logic:notEmpty>
							
							<logic:empty name="gestionarIndicadoresForm">
								<logic:notEmpty name="gestionarIndicadoresPlanForm">
									<logic:iterate id="filtrosUni" name="gestionarIndicadoresPlanForm" property="unidadesMedida">
										<bean:define id="unidadId" toScope="page" name="filtrosUni" property="unidadId" type="Long"></bean:define>
										<bean:define id="nombre" toScope="page"><bean:write name="filtrosUni" property="nombre" /></bean:define>
										<logic:equal name="filtrosUni" property="unidadId" value="<%=filtroUnidadValue%>">
											<option value="<%=unidadId%>" selected><%=nombre%></option>
										</logic:equal>
										<logic:notEqual name='filtrosUni' property='unidadId' value='<%=filtroUnidadValue.toString()%>'>
											<option value="<%=unidadId%>"><%=nombre%></option>
										</logic:notEqual>
									</logic:iterate>
								</logic:notEmpty>
							</logic:empty>
						</select>		
					</td>
				</tr>	
				
			</table>					
		</td>
		
	
		<%-- Botones --%>
		<td style="width: 50px;">
			<table class="tablaSpacing0Padding0Width100Collapse" style="padding: 1px;">
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td colspan="2" style="width: 30px;">
						<a class="boton" title="<vgcutil:message key="boton.buscar.alt" />" onclick="refrescarInd()"><vgcutil:message key="boton.buscar.alt" /></a>
					</td>
				</tr>
				<tr class="barraFiltrosForma" style="height: 20px;">
					<td colspan="2" style="width: 30px;">
						<a class="boton" title="<vgcutil:message key="boton.limpiar.alt" />" onclick="limpiarFiltrosInd()"><vgcutil:message key="boton.limpiar.alt" /></a>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>