<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (03/07/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- Título --%>
	<tiles:put name="title" type="String">
		..:: <vgcutil:message key="jsp.gestionarindicadores.titulo" /> [<bean:write name="organizacion" scope="session" property="nombre" /> ]
	</tiles:put>

	<%-- Cuerpo --%>
	<tiles:put name="body" type="String">
				
		<%-- Split --%>
		<bean:define id="anchoPanel" toScope="page">
			<logic:notEmpty name="gestionarIndicadoresForm" property="anchoPorDefecto">
				<bean:write name="gestionarIndicadoresForm" property="anchoPorDefecto" />px
			</logic:notEmpty>
			<logic:empty name="gestionarIndicadoresForm" property="anchoPorDefecto">
				400px
			</logic:empty>
		</bean:define>		
		
		<vgcinterfaz:splitterHorizontal anchoPorDefecto="<%= anchoPanel %>" splitterId="splitIndicadores" overflowPanelDerecho="hidden" overflowPanelIzquierdo="hidden">
			<vgcinterfaz:splitterHorizontalPanelIzquierdo splitterId="splitIndicadores">
				<jsp:include flush="true" page="/paginas/strategos/indicadores/clasesindicadores/gestionarClasesIndicadores.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelIzquierdo>
			<vgcinterfaz:splitterHorizontalPanelDerecho splitterId="splitIndicadores">
				<jsp:include flush="true" page="/paginas/strategos/indicadores/gestionarIndicadores.jsp"></jsp:include>
			</vgcinterfaz:splitterHorizontalPanelDerecho>
		</vgcinterfaz:splitterHorizontal>
	</tiles:put>

</tiles:insert>
