package by.creepid.jsfspring.demo.faces;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class CustomViewHandler extends ViewHandlerWrapper {
	
	private static final String VIEW_PREFIX = "/WEB-INF/";

	private ViewHandler handler;

	public CustomViewHandler(ViewHandler handler) {
		this.handler = handler;
	}

	@Override
	public ViewHandler getWrapped() {
		return handler;
	}

	@Override
	public String getActionURL(FacesContext ctx, String viewId) {
		return viewId.startsWith(VIEW_PREFIX)
			? ((HttpServletRequest)ctx.getExternalContext().getRequest()).getRequestURI()
			: handler.getActionURL(ctx, viewId);
	}

}
