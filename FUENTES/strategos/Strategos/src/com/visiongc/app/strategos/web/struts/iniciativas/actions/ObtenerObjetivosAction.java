package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class ObtenerObjetivosAction extends VgcAction{


		@Override
		public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
		 {
		 }

		 @Override
		public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
				    throws Exception
		 {
		    super.execute(mapping, form, request, response);
		    String forward = mapping.getParameter();



			return mapping.findForward(forward);
		 }
}
