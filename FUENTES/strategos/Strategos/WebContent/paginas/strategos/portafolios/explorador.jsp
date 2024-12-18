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
		..:: <vgcutil:message key="jsp.gestionarportafolio.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
		<script type=text/javascript>
			var splitPortafolioVerticalAltoActual=0;
			var splitPortafolioVerticalPosicionActual=0;
			var splitPortafolioVerticalPosicionNueva=0;
			var splitPortafolioVerticalAltoPanelSuperior='400';
			var splitPortafolioVerticalMouseStatus='up';
			var anchoPagina = _myWidth;
			var altoPagina = _myHeight;
			var startVertical = false;
			
			function splitPortafolioVerticalSetPosicion(e) 
			{
				eventoActual = (typeof event == 'undefined'? e: event);
				splitPortafolioVerticalMouseStatus = 'down';
				splitPortafolioVerticalPosicionActual = eventoActual.clientY;
				altoTemp = document.getElementById('tdScreenSuperior').style.height;
				arregloAlto = altoTemp.split('p');
				splitPortafolioVerticalAltoActual = parseInt(arregloAlto[0]);
				startVertical = true;
			}
			
			function splitPortafolioVerticalGetPosicion(e) 
			{
				if (splitPortafolioVerticalMouseStatus == 'down') 
				{
					eventoActual = (typeof event == 'undefined'? e: event);
					splitPortafolioVerticalPosicionNueva = eventoActual.clientY;
					var movimientoPx = parseInt(splitPortafolioVerticalPosicionNueva - splitPortafolioVerticalPosicionActual);
					var altoNuevo = parseInt(splitPortafolioVerticalAltoActual + movimientoPx);
					altoNuevo = (altoNuevo < 70 ? 70 : altoNuevo > (altoPagina - 350) ? (altoPagina - 350) : altoNuevo);
					document.getElementById('tdScreenSuperior').style.height = altoNuevo + 'px';
					splitPortafolioVerticalAltoPanelSuperior = altoNuevo + 'px';
					
					if (typeof (resizeAltoFormaDividida) == "function")
						resizeAltoFormaDividida();
				}
			}
			
		</script>
	
		<style type=text/css>
			#splitPortafolioVerticalVSplit 
			{
				cursor: s-resize; 
				width: 1px; 
				padding-bottom: 2px; 
				padding-top: 2px; 
				padding-left: 2px; 
				padding-right: 2px; 
				background-color: #c0c0c0; 
				witdth: 100%;
			}
		</style>

		<%-- Split --%>
		<bean:define id="anchoPanel" toScope="page">
			<logic:notEmpty name="gestionarPortafoliosForm" property="anchoPorDefecto">
				<bean:write name="gestionarPortafoliosForm" property="anchoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarPortafoliosForm" property="anchoPorDefecto">
				400px
			</logic:empty>
		</bean:define>
		<bean:define id="altoPanel" toScope="page">
			<logic:notEmpty name="gestionarPortafoliosForm" property="altoPorDefecto">
				<bean:write name="gestionarPortafoliosForm" property="altoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarPortafoliosForm" property="altoPorDefecto">
				350px
			</logic:empty>
		</bean:define>

		<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoPanel %>" splitterId="splitPortafolios" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">

			<%-- Panel Izquierdo: Gestionar Portafolio --%>
			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitPortafolios">
				<jsp:include flush="true" page="/paginas/strategos/portafolios/gestionar.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>

			<%-- Panel Derecho:  --%>
			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitPortafolios">
				
					<table height="100%" width="100%" border="0" cellpadding="0" cellspacing="0" onmouseup="splitPortafolioVerticalMouseStatus='up'; setAltoPanel();" onmousemove="splitPortafolioVerticalGetPosicion(event)">					
						<tr height="50%">
							<td valign="top" id="tdScreenSuperior" class="panelSplit" style="height: <%= altoPanel %>" >
								<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIniciativas.jsp"></jsp:include>
							</td>
						</tr>
						<tr height="2px">
							<td onmousedown="splitPortafolioVerticalSetPosicion(event)" id="splitPortafolioVerticalVSplit"></td>
						</tr>
						<tr>
							<td id="tdScreenInferior" valign="top" class="panelSplit" style="height: 100%" >
								<jsp:include flush="true" page="/paginas/strategos/iniciativas/gestionarIndicadoresIniciativa.jsp"></jsp:include>
							</td>
						</tr>
					</table>
							
						
			
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	</tiles:put>
</tiles:insert>
