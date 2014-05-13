package by.creepid.jsfspring.demo.faces;

import java.io.IOException;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class FacesServletHelper {

	// You need an inner class to be able to call
	// FacesContext.setCurrentInstance
	// since it's a protected method
	private abstract static class InnerFacesContext extends FacesContext {
		protected static void setFacesContextAsCurrentInstance(
				FacesContext facesContext) {
			FacesContext.setCurrentInstance(facesContext);
		}
	}

	private FacesServletHelper() {
	}

	public static FacesContext getFacesContext(HttpServletRequest request,
			HttpServletResponse response) {

		FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder
				.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
				.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle = lifecycleFactory
				.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

		FacesContext facesContext = contextFactory
				.getFacesContext(request.getSession().getServletContext(),
						request, response, lifecycle);

		// Set using our inner class
		InnerFacesContext.setFacesContextAsCurrentInstance(facesContext);

		// set a new viewRoot, otherwise context.getViewRoot returns null
		UIViewRoot view = facesContext.getApplication().getViewHandler()
				.createView(facesContext, "");
		facesContext.setViewRoot(view);

		return facesContext;
	}

	public static void removeFacesContext() {
		InnerFacesContext.setFacesContextAsCurrentInstance(null);
	}

	public static Application getApplication(FacesContext facesContext) {
		return facesContext.getApplication();
	}

	protected Object getManagedBean(String beanName, FacesContext facesContext) {
		return getApplication(facesContext).getVariableResolver()
				.resolveVariable(facesContext, beanName);
	}
}
