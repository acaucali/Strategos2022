<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Andres Martinez (22/06/2023) --%>

<script language="javascript">

function abrirSelectorCargos(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, funcionCierre) {		
	var parametroFuncionCierre = ''
	if ((funcionCierre != null) && (funcionCierre != '')) {
		parametroFuncionCierre = '&funcionCierre=' + funcionCierre;
	}				
	abrirVentanaModal('<html:rewrite action="/cargos/seleccionarCargo" />?nombreForma=' + nombreForma 
		+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto 
		+ '&seleccionados=' + seleccionados + parametroFuncionCierre, 'seleccionarCargos', '600', '400');		
}

</script>