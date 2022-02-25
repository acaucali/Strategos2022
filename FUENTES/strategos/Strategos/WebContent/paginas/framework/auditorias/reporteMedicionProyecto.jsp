<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
  <%@ taglib uri="/tags/struts-bean" prefix="bean"%>
    <%@ taglib uri="/tags/struts-html" prefix="html"%>
      <%@ taglib uri="/tags/struts-logic" prefix="logic"%>
        <%@ taglib uri="/tags/vgc-treeview" prefix="treeview"%>
          <%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
            <%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
              <%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>
                <%@page import="java.util.Date"%>

                  <%-- Creado por: Gustavo Chaparro (01/09/2013) --%>

                    <tiles:insert definition="doc.modalWindowLayout" flush="true">

                      <%-- T�tulo --%>
                        <tiles:put name="title" type="String">
                          ..::
                          <vgcutil:message key="reporte.framework.auditorias.proyecto.detallado.titulo" />
                        </tiles:put>

                        <%-- Cuerpo --%>
                          <tiles:put name="body" type="String">

                            <%-- Funciones JavaScript locales de la p�gina Jsp --%>
                              <script type="text/javascript">
                                << << << < HEAD


                                  ===
                                  === =
                                  var _fechaDesde;
                                var _fechaHasta;


                                function cancelar() {
                                  window.close();
                                }

                                <<
                                << << < HEAD

                                function generarReporte() {


                                  ===
                                  === =
                                  function validar() {
                                    if (document.reporteAuditoriaForm.fechaDesde.value == "") {
                                      alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.vacio" /> ');
                                      return false;
                                    }

                                    if (document.reporteAuditoriaForm.fechaHasta.value == "") {
                                      alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.vacio" /> ');
                                      return false;
                                    }

                                    if (!fechaValida(document.reporteAuditoriaForm.fechaDesde)) {
                                      alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechadesde.invalido" /> ');
                                      return;
                                    }

                                    if (!fechaValida(document.reporteAuditoriaForm.fechaHasta)) {
                                      alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.fechahasta.invalido" /> ');
                                      return;
                                    }

                                    var fecha1 = document.reporteAuditoriaForm.fechaDesde.value.split("/");
                                    var fecha2 = document.reporteAuditoriaForm.fechaHasta.value.split("/");

                                    var diaDesde = fecha1[0];
                                    var diaHasta = fecha2[0];

                                    var mesDesde = fecha1[1];
                                    var mesHasta = fecha2[1];

                                    var anoDesde = fecha1[2];
                                    var anoHasta = fecha2[2];

                                    var desde = parseInt(anoDesde + "" + (mesDesde.length == 1 ? ("0" + mesDesde) : mesDesde) + "" + (diaDesde.length == 1 ? ("0" + diaDesde) : diaDesde));
                                    var hasta = parseInt(anoHasta + "" + (mesHasta.length == 1 ? ("0" + mesHasta) : mesHasta) + "" + (diaHasta.length == 1 ? ("0" + diaHasta) : diaHasta));

                                    if (hasta < desde) {
                                      alert('<vgcutil:message key="jsp.framework.gestionarauditorias.alerta.rango.fechas.invalido" /> ');
                                      return false;
                                    }

                                    return true;
                                  }

                                  function generarReporte() {
                                    if (validar()) {
                                      var url = '?fechaDesde=' + document.reporteAuditoriaForm.fechaDesde.value;
                                      url = url + '&fechaHasta=' + document.reporteAuditoriaForm.fechaHasta.value;

                                      url = url + '&organizacionId=' + document.reporteAuditoriaForm.organizacionId.value;

                                      if (document.reporteAuditoriaForm.tipoReporte[0].checked)
                                        abrirReporte('<html:rewrite action="/framework/auditorias/reporteMedicionProyectoPdf"/>' + url);

                                      else if (document.reporteAuditoriaForm.tipoReporte[1].checked)
                                        abrirReporte('<html:rewrite action="/framework/auditorias/reporteMedicionProyectoXls"/>' + url);

                                      window.close();
                                    }


                                    >>>
                                    >>> > 824 b907b281afd4fab3ea5cbb8c17b196a44d297
                                  }

                                  function seleccionarOrganizaciones() {
                                    abrirSelectorOrganizaciones('reporteAuditoriaForm', 'nombreOrganizacion', 'organizacionId', null);
                                  } <<
                                  << << < HEAD
                              </script>
                              <jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
                              ======= function seleccionarFechaDesde() { mostrarCalendario('document.reporteAuditoriaForm.fechaDesde' , document.reporteAuditoriaForm.fechaDesde.value, '
                              <vgcutil:message key="formato.fecha.corta" />'); } function limpiarFechaDesde() { document.reporteAuditoriaForm.fechaDesde.value = ''; } function seleccionarFechaHasta() { mostrarCalendario('document.reporteAuditoriaForm.fechaHasta' , document.reporteAuditoriaForm.fechaHasta.value,
                              '
                              <vgcutil:message key="formato.fecha.corta" />'); } function limpiarFechaHasta() { document.reporteAuditoriaForm.fechaHasta.value = ''; }

                              </script>
                              <jsp:include flush="true" page="/paginas/strategos/organizaciones/organizacionesJs.jsp"></jsp:include>
                              <jsp:include page="/componentes/calendario/calendario.jsp"></jsp:include>
                              >>>>>>> 824b907b281afd4fab3ea5cbb8c17b196a44d297

                              <%-- Forma asociada al Action - Jsp --%>
                                <html:form action="/framework/auditorias/reporteMedicionProyecto">

                                  <html:hidden property="source" />
                                  <html:hidden property="organizacionId" />
                                  <html:hidden property="nombreOrganizacion" />



                                  <vgcinterfaz:contenedorForma width="460px" height="460px" bodyAlign="center" bodyValign="middle" bodyCellpadding="20">

                                    <%-- T�tulo--%>
                                      <vgcinterfaz:contenedorFormaTitulo>..::
                                        <vgcutil:message key="reporte.framework.auditorias.proyecto.detallado.titulo" />
                                      </vgcinterfaz:contenedorFormaTitulo>

                                      <%-- Paneles --%>
                                        <vgcinterfaz:contenedorPaneles height="340px" width="400px" nombre="reporteAuditoriaMediciones">

                                          <%-- Panel: Parametros --%>


                                            <%-- Panel: Selector --%>
                                              <vgcinterfaz:panelContenedor anchoPestana="110px" nombre="selector">

                                                <%-- T�tulo del Panel: Datos B�sicos --%>
                                                  <vgcinterfaz:panelContenedorTitulo>
                                                    <vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.selector" />
                                                  </vgcinterfaz:panelContenedorTitulo>

                                                  <table class="panelContenedor panelContenedorTabla">
                                                    <!-- Organizacion Seleccionada-->
                                                    <tr>
                                                      <td align="right"><b><vgcutil:message key="jsp.editariniciativa.ficha.organizacion" /></b></td>
                                                      <<<<<<< HEAD <td align="right"><input type="button" style="width:40%" class="cuadroTexto" value="<vgcutil:message key=" jsp.seleccionarindicador.seleccionarorganizacion " />" onclick="seleccionarOrganizaciones();"></td>
                                                        =======
                                                        <td align="right"><input type="button" style="width:80%" class="cuadroTexto" value="<vgcutil:message key=" jsp.seleccionarindicador.seleccionarorganizacion " />" onclick="seleccionarOrganizaciones();"></td>

                                                        >>>>>>> 824b907b281afd4fab3ea5cbb8c17b196a44d297
                                                    </tr>

                                                    <tr>
                                                      <td colspan="3">
                                                        <hr width="100%">
                                                      </td>
                                                    </tr>

                                                    <<<<<<< HEAD <tr>
                                                      <!-- Encabezado selector de fechas -->

                                                      <td align="center" colspan="1"></td>
                                                      <td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.mes" /> </b> </td>
                                                      <td align="left" colspan="1"><b><vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.ano" /> </b> </td>

                                                      </tr>

                                                      <!-- Fecha Inicio -->
                                                      <tr>
                                                        <td align="left">
                                                          <vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.desde" /> : </td>
                                                        <td>
                                                          <html:select property="mesInicial" styleClass="cuadroTexto">
                                                            <html:optionsCollection property="grupoMeses" value="clave" label="valor" />
                                                          </html:select>
                                                        </td>
                                                        <td>
                                                          <html:select property="anoInicial" styleClass="cuadroTexto">
                                                            <html:optionsCollection property="grupoAnos" value="clave" label="valor" />
                                                          </html:select>

                                                        </td>
                                                      </tr>
                                                      <!-- Fecha Final -->
                                                      <tr>
                                                        <td align="left">
                                                          <vgcutil:message key="jsp.reportes.iniciativa.ejecucion.plantilla.hasta" /> : </td>
                                                        <td>
                                                          <html:select property="mesFinal" styleClass="cuadroTexto">
                                                            <html:optionsCollection property="grupoMeses" value="clave" label="valor" />
                                                          </html:select>
                                                        </td>
                                                        <td>
                                                          <html:select property="anoFinal" styleClass="cuadroTexto">
                                                            <html:optionsCollection property="grupoAnos" value="clave" label="valor" />
                                                          </html:select>
                                                        </td>
                                                        =======
                                                        <tr>
                                                          <tr class="barraFiltrosForma">
                                                            <td align="right">
                                                              <vgcutil:message key="jsp.framework.gestionarauditorias.filtro.fechadesde" />
                                                            </td>
                                                            <td align="right">
                                                              <html:text property="fechaDesde" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaDesde();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="
                                                                boton.calendario.alt " />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaDesde()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
                                                            <td width="30px">&nbsp;</td>
                                                            <td>&nbsp;</td>
                                                          </tr>
                                                          <tr class="barraFiltrosForma">
                                                            <td align="right">
                                                              <vgcutil:message key="jsp.framework.gestionarauditorias.filtro.fechahasta" />
                                                            </td>
                                                            <td align="right">
                                                              <html:text property="fechaHasta" size="10" onfocus="this.blur();" maxlength="10" styleClass="cuadroTexto" />&nbsp;<img style="cursor: pointer" onclick="seleccionarFechaHasta();" src="<html:rewrite page='/componentes/calendario/calendario.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key="
                                                                boton.calendario.alt " />">&nbsp;<img style="cursor:pointer" onclick="limpiarFechaHasta()" src="<html:rewrite page='/componentes/formulario/salir.gif'/>" border="0" width="10" height="10" title="<vgcutil:message key='boton.limpiar.alt' />"></td>
                                                            <td width="30px">&nbsp;</td>
                                                            <td>&nbsp;</td>
                                                            >>>>>>> 824b907b281afd4fab3ea5cbb8c17b196a44d297
                                                          </tr>

                                                          <tr>
                                                            <td colspan="3">
                                                              &nbsp;
                                                            </td>
                                                          </tr>
                                                          <tr>
                                                            <td colspan="3">
                                                              &nbsp;
                                                            </td>
                                                          </tr>
                                                          <tr>
                                                            <td colspan="3">
                                                              &nbsp;
                                                            </td>
                                                          </tr>
                                                          <tr>
                                                            <td colspan="3">
                                                              &nbsp;
                                                            </td>
                                                          </tr>
                                                          <tr>
                                                            <td colspan="3">
                                                              &nbsp;
                                                            </td>
                                                          </tr>
                                                  </table>
                                              </vgcinterfaz:panelContenedor>


                                              <%-- Panel: Salida --%>
                                                <vgcinterfaz:panelContenedor anchoPestana="110px" nombre="salida">

                                                  <%-- T�tulo del Panel: Datos B�sicos --%>
                                                    <vgcinterfaz:panelContenedorTitulo>
                                                      <vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo" />
                                                    </vgcinterfaz:panelContenedorTitulo>

                                                    <table class="panelContenedor panelContenedorTabla">

                                                      <tr>
                                                        <<<<<<< HEAD <td colspan="3">
                                                          =======
                                                          <td colspan="3">
                                                            >>>>>>> 824b907b281afd4fab3ea5cbb8c17b196a44d297
                                                            <html:radio property="tipoReporte" value="1" />
                                                            <vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.pdf" />&nbsp;&nbsp;&nbsp;
                                                            <html:radio property="tipoReporte" value="2" />
                                                            <vgcutil:message key="jsp.reportes.plan.meta.reporte.tipo.excel" />
                                                          </td>
                                                      </tr>
                                                      <tr>
                                                        <td colspan="3">
                                                          &nbsp;
                                                        </td>
                                                      </tr>
                                                      <tr>
                                                        <td colspan="3">
                                                          &nbsp;
                                                        </td>
                                                      </tr>
                                                      <tr>
                                                        <td colspan="3">
                                                          &nbsp;
                                                        </td>
                                                      </tr>
                                                      <tr>
                                                        <td colspan="3">
                                                          &nbsp;
                                                        </td>
                                                      </tr>
                                                      <tr>
                                                        <td colspan="3">
                                                          &nbsp;
                                                        </td>
                                                      </tr>
                                                    </table>
                                                </vgcinterfaz:panelContenedor>


                                        </vgcinterfaz:contenedorPaneles>

                                        <%-- Barra Inferior del "Contenedor Secundario o Forma" --%>
                                          <vgcinterfaz:contenedorFormaBarraInferior idBarraInferior="barraInferior">
                                            <%-- Aceptar --%>
                                              <img src="<html:rewrite page='/componentes/formulario/aceptar.gif'/>" border="0" width="10" height="10">
                                              <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:generarReporte();" class="mouseFueraBarraInferiorForma">
                                                <vgcutil:message key="boton.aceptar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                              <%-- Cancelar --%>
                                                <img src="<html:rewrite page='/componentes/formulario/cancelar.gif'/>" border="0" width="10" height="10">
                                                <a onMouseOver="this.className='mouseEncimaBarraInferiorForma'" onMouseOut="this.className='mouseFueraBarraInferiorForma'" href="javascript:cancelar();" class="mouseFueraBarraInferiorForma">
                                                  <vgcutil:message key="boton.cancelar" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                          </vgcinterfaz:contenedorFormaBarraInferior>

                                  </vgcinterfaz:contenedorForma>
                                </html:form>

                          </tiles:put>
                    </tiles:insert>