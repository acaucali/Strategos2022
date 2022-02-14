<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (29/10/2012) --%>

<tiles:insert definition="doc.mainLayout" flush="true">

	<%-- T�tulo --%>
	<tiles:put name="title" type="String">
		..:: Inicio de Strategos
	</tiles:put>
	
	<%-- Cuerpo vacio --%>
	<tiles:put name="body" type="String">	
		Prueba de Cuerpo de Home de Strategos
		<!-- Este es el Cuerpo y est� vac�o -->
	</tiles:put>
	
</tiles:insert>
