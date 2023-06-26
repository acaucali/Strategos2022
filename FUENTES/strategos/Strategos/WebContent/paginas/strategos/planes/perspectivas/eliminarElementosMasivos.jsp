<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
<%@ taglib uri="/tags/sslext" prefix="sslext"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Andres Martinez (09/06/2023) --%>

<tiles:insert definition="doc.modalLayout" flush="true">

	<tiles:put name="title" type="String">
		<vgcutil:message key="jsp.eliminarperspectiva.masivo" />
	</tiles:put>


	<tiles:put name="body" type="String">
		<script type="text/javascript">
		
		var listaPadresHijosPermisos = '';
		var checkeds = [];
		function seleccionarNodo(nodoId, marcadorAncla) 
		{
			//window.location.href='<html:rewrite action="/planes/perspectivas/eliminarElementosMasivos"/>?selectedPerspectivaId=' + nodoId + marcadorAncla;
		}

		function openNodo(nodoId, marcadorAncla) 
		{		
			window.location.href='<html:rewrite action="/planes/perspectivas/eliminarElementosMasivos"/>?seleccionados='+ checkeds + '&openPerspectivaId=' + nodoId  + marcadorAncla;
		}
		

		function closeNodo(nodoId, marcadorAncla) 
		{
			window.location.href='<html:rewrite action="/planes/perspectivas/eliminarElementosMasivos"/>?seleccionados='+ checkeds + '&closePerspectivaId=' + nodoId  + marcadorAncla;
		}

		function selectUnselectTodosPerspectivas(){
			var forma = self.document.editarPerspectivaForm;
			var total = forma.elements.length;
			var campoControl = self.document.editarPerspectivaForm.perspectivaControl;
			var seleccionado; 
			
			console.log(campoControl);
			
			if(campoControl.checked)
				seleccionado = true;
			else
				seleccionado = false;
			
			for (var indice = 0; indice < total; indice++ ) 
			{
				if (forma.elements[indice].name == 'perspectiva') 
				{
					if (forma.elements[indice].value == 'PERSPECTIVAS'){ 
						forma.elements[indice].checked = false;
						
					}
					else{
						forma.elements[indice].checked = seleccionado;
						if(seleccionado == true){
							checkeds.push(forma.elements[indice].value);
						}else if(seleccionado == false){
							checkeds = []
						}
					}
				}
			}
		}
		
		function setSeleccionadosPadreHijo(objetoInputCheckbox) 
		{
			
			
			if (objetoInputCheckbox.checked) 
			{		
				checkeds.push(objetoInputCheckbox.value);
			}else{
				var indice = checkeds.indexOf(objetoInputCheckbox.value);
				if (indice > -1) {
					checkeds.splice(indice, 1);
				}
			}
		}
		
		function inicializarPerspectivasSeleccionadas(){
			var forma = self.document.editarPerspectivaForm;
			
			var total = forma.elements.length;
			
			var seleccionados = document.editarPerspectivaForm.perspectivas.value	
	
			for (var indice = 0; indice < total; indice++) 
			{
				if (forma.elements[indice].name == 'perspectiva') 
				{
					if(seleccionados.indexOf(forma.elements[indice].value)> -1){
						forma.elements[indice].checked = true;
						checkeds.push(forma.elements[indice].value);
					}
					else if(seleccionados.indexOf(',' + forma.elements[indice].value +  ',' ) == 0){
						forma.elements[indice].checked = true;
						checkeds.push(forma.elements[indice].value);
					}else
						forma.elements[indice].checked = false;
				}
			}
		}
		
		
		
		function validar(forma) 
		{
			
			if (validateEditarPerspectivaForm(forma)) 
			{
				setPerspectivasSeleccionadas();
				if (forma.perspectivas.value == '') 
				{
					alert('<vgcutil:message key="jsp.framework.editargrupo.validacion.sinpermisos" />');
					return false;
				}
				// window.document.editarPerspectivaForm.action = '<sslext:rewrite  action="/framework/grupos/guardarGrupo"/>' + '?ts=<%= (new java.util.Date()).getTime() %>';
				return true;
			} 
			else 
				return false;
		}
		
		function guardar() 
		{
			if (validar(document.editarPerspectivaForm)) {			
				window.document.editarPerspectivaForm.submit();
				activarBloqueoEspera();									
			}
		}
		
		function setPerspectivasSeleccionadas(fieldOutput, fieldName){
			
			var forma = self.document.editarPerspectivaForm
			var total = forma.elements.length;
			var objetoInput = '';
			
			
			
			for(var indice = 0; indice < total; indice++) 
			{
				
				if (forma.elements[indice].name == 'perspectiva') 
				{
					if (forma.elements[indice].checked) 
						objetoInput.value = objetoInput.value + forma.elements[indice].value + ':';
				}
			}
		}
		
		function aceptar(){
			window.location.href='<html:rewrite action="/planes/perspectivas/eliminarElementosMasivos"/>?seleccionados='+ checkeds + '&eliminar=true'
			activarBloqueoEspera();
		}
		
		
		function cancelar(){
			window.location.href='<html:rewrite action="/planes/perspectivas/eliminarElementosMasivos"/>?cancelar=true';
		}
		
		</script>
		
		<%-- Tag que devuelve el foco al objeto indicado --%>
		<vgcinterfaz:setFocoObjetoInterfaz objetoInputHtml="document.editarPerspectivaForm.nombre" />
		
		
		<html:form action="/planes/perspectivas/eliminarElementosMasivos" >
		
			<html:hidden property="perspectivas" />
			<vgcinterfaz:contenedorForma width="1200px" height="465px">
				<vgcinterfaz:contenedorFormaTitulo>
					<vgcutil:message key="jsp.eliminarperspectiva.masivo" />
				</vgcinterfaz:contenedorFormaTitulo>
				<%-- Ficha de datos --%>
				
				  <table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="395" border="0" width="1150">
					<tr>
						<td colspan="2" height="20"><input name='perspectivaControl'
							type='checkbox' styleClass="botonSeleccionMultiple"
							onClick="javascript:selectUnselectTodosPerspectivas()">
							<vgcutil:message key="jsp.eliminarperspectiva.selectunselectall" />
						</td>
					</tr>
					
					<tr>
						<td colspan="2" height="310">
							<treeview:treeview
								name="arbolPerspectivas" 
								scope="session"
								baseObject="perspectivaRoot" 
								isRoot="true"
								fieldName="Nombre" 
								fieldId="perspectivaId"
								idType="Long" 
								fieldChildren="hijos"
								lblUrlObjectId="perspectivaId" 
								styleClass="treeview" 
								checkbox="true"
								checkboxName="perspectiva"
								checkboxOnClick="javascript:setSeleccionadosPadreHijo(this);"
								urlJs="/componentes/visorArbol/visorArbol.js"
								pathImages="/componentes/visorArbol/" 
								urlConnectorOpen="javascript:openNodo(perspectivaId, marcadorAncla);" 
								urlConnectorClose="javascript:closeNodo(perspectivaId, marcadorAncla);" 
								lblUrlAnchor="marcadorAncla"
								urlName="javascript:seleccionarNodo(perspectivaId, marcadorAncla);" 
								nameSelectedId="perspectivaId"
								useFrame="true"/>
								
						</td>
					</tr>
				</table>
				 
				
				<%-- Barra Inferior --%>
				<vgcinterfaz:contenedorFormaBarraInferior
					idBarraInferior="barraInferior">
					
						<img
							src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>"
							border="0" width="10" height="10">
						<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
							onMouseOut="this.className='mouseFueraBarraInferiorForma'"
							href="javascript:aceptar();" class="mouseFueraBarraInferiorForma">
						<vgcutil:message key="boton.aceptar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
					
					<img
						src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>"
						border="0" width="10" height="10">
					<a onMouseOver="this.className='mouseEncimaBarraInferiorForma'"
						onMouseOut="this.className='mouseFueraBarraInferiorForma'"
						href="javascript:cancelar();"
						class="mouseFueraBarraInferiorForma"><vgcutil:message
						key="boton.cancelar" /></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</vgcinterfaz:contenedorFormaBarraInferior>
				
				
			</vgcinterfaz:contenedorForma>
			
			<script type="text/javascript">
				inicializarPerspectivasSeleccionadas();
			</script>
		</html:form>
	</tiles:put>

</tiles:insert>

