<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (23/07/2012) -->

<html>

<head>

<%-- T�tulo de la p�gina --%>
<title><vgcutil:message key="aplicacion.nombre" />&nbsp;-&nbsp;<tiles:insert attribute='title' /></title>

<%-- Estilos de la aplicaci�n --%>
<tiles:insert attribute="styleCss" />

<%-- Js comunes --%>
<tiles:insert attribute="jsCommons" />

</head>

<body class="cuerpo" onload="inicializarComunesEventoWindowOnLoad();" oncontextmenu="return false;" >
<div id="fondo"></div>
<div align="center"><tiles:insert attribute='body' /> <tiles:insert attribute="messages" /></div>

<%-- Mensajes de error de la aplicaci�n --%>
<tiles:insert attribute="errorJsp" />

<%-- Funciones de Cierre que se ejecutan al final de la p�gina Html --%>
<tiles:insert attribute="funcionesCierre" />

</body>
</html>
