package by.creepid.jsfspring.demo.model;
 
import java.io.Serializable;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
 
@Component
//This way Spring will create a proxied bean which will call original every time a method is called.
@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class HelloWorld implements Serializable{
		
	private static final long serialVersionUID = -5533948041497052227L;
	@NotEmpty
	private String message;
	@Range(min = 1, max = 150) //between 1 and 150
    private Long counter;
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }

	public Long getCounter() {
		return counter;
	}

	public void setCounter(Long counter) {
		this.counter = counter;
	}

	    
}