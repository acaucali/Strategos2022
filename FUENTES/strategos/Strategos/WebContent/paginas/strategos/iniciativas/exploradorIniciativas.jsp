<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (18/12/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <bean:write scope="session" name="activarIniciativa" property="nombrePlural" />  [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
	
		<script type="text/javascript">
			var splitIniciativaVerticalAltoActual=0;
			var splitIniciativaVerticalPosicionActual=0;
			var splitIniciativaVerticalPosicionNueva=0;
			var splitIniciativaVerticalAltoPanelSuperior='400';
			var splitIniciativaVerticalMouseStatus='up';
			var anchoPagina = screen.width;
			var altoPagina = screen.height;
			var startVertical = false;
			
			function splitIniciativaVerticalSetPosicion(e) 
			{
				eventoActual = (typeof event == 'undefined'? e: event);
				splitIniciativaVerticalMouseStatus = 'down';
				splitIniciativaVerticalPosicionActual = eventoActual.clientY;
				altoTemp = document.getElementById('splitIniciativaVerticalPanelSuperior').style.height;
				arregloAlto = altoTemp.split('p');
				splitIniciativaVerticalAltoActual = parseInt(arregloAlto[0]);
				startVertical = true;
			}
			
			function splitIniciativaVerticalGetPosicion(e) 
			{
				if (splitIniciativaVerticalMouseStatus == 'down') 
				{
					eventoActual = (typeof event == 'undefined'? e: event);
					splitIniciativaVerticalPosicionNueva = eventoActual.clientY;
					var movimientoPx = parseInt(splitIniciativaVerticalPosicionNueva - splitIniciativaVerticalPosicionActual);
					var altoNuevo = parseInt(splitIniciativaVerticalAltoActual + movimientoPx);
					altoNuevo = (altoNuevo < 70 ? 70 : altoNuevo > (altoPagina - 250) ? (altoPagina - 250) : altoNuevo);
					document.getElementById('splitIniciativaVerticalPanelSuperior').style.height = altoNuevo + 'px';
					splitIniciativaVerticalAltoPanelSuperior = altoNuevo + 'px';
					
					if (typeof (resizePanelIniciativa) == "function")
						resizePanelIndicadores();
				}
			}
			
		</script>	
		
		<style type=text/css>
			#splitIniciativaVerticalVSplit 
			{
				cursor: s-resize; 
				width: 100px; 
				padding-bottom: 3px; 
				padding-top: 3px; 
				padding-left: 3px; 
				padding-right: 3px; 
				background-color: #c0c0c0; 				
			}
		</style>

		<%-- Split --%>
		<bean:define id="anchoPanel" toScope="page">
			<logic:notEmpty name="gestionarIniciativasForm" property="anchoPorDefecto">
				<bean:write name="gestionarIniciativasForm" property="anchoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarIniciativasForm" property="anchoPorDefecto">
				400px
			</logic:empty>
		</bean:define>
		<bean:define id="altoPanel" toScope="page">
			<logic:notEmpty name="gestionarIniciativasForm" property="altoPorDefecto">
				<bean:write name="gestionarIniciativasForm" property="altoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarIniciativasForm" property="altoPorDefecto">
				400px
			</logic:empty>
		</bean:define>
		
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoPanel %>" splitterId="splitIniciativas" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">

			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitIniciativas">
				<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIniciativas.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>

			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitIniciativas">
				<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" onmouseup="splitIniciativaVerticalMouseStatus='up'; setAltoPanel();" onmousemove="splitIniciativaVerticalGetPosicion(event)">
					<tr height="50%">
						<td valign="top" id="splitIniciativaVerticalPanelSuperior" class="panelSplit" style="height: <%= altoPanel %>">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIndicadoresIniciativa.jsp"></jsp:include>
						</td>
					</tr>
					<tr height="2px">
						<td onmousedown="splitIniciativaVerticalSetPosicion(event)" id="splitIniciativaVerticalVSplit"></td>
					</tr>
					<tr >
						<td valign="top" class="panelSplit" style="height: 100%">
							<jsp:include flush="true" page="/paginas/strategos/iniciativas/mostrarPlanesAsociadosIniciativa.jsp"></jsp:include>
						</td>
					</tr>					
				</table>
			</vgcinterfaz:splitterHorizontalPanelDerecho>

		</vgcinterfaz:splitterHorizontal>		
	</tiles:put>

</tiles:insert>
