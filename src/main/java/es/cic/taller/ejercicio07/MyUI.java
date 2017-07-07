package es.cic.taller.ejercicio07;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        final TextField tfNombre = new TextField();
        tfNombre.setCaption("Nombre:");
        
        final TextField ap = new TextField();
        ap.setCaption("Apellido:");
        
        DateTimeField tfFecha = new DateTimeField();
        tfFecha.setCaption("Fecha:");
        
        Collection<Pais> listaPaises = getListaPaises();
        
        ComboBox<Pais> cPais = new ComboBox<>("Selecciona tu país", listaPaises);

        Button button = new Button("Aceptar");
        button.addClickListener( e -> {
        	Persona persona = new Persona();
        	persona.setNombre(tfNombre.getValue());
        	persona.setApellido(ap.getValue());
        	persona.setFecha(tfFecha.getValue());
        	persona.setPais(cPais.getValue());
        	
        	Notification.show(calcularMensaje(persona) , Type.TRAY_NOTIFICATION);
        });
        
        Button button2 = new Button("Cambia");
        button2.addClickListener( e -> {
        	button.setVisible(!button.isVisible());
        	
        	
        });
        
                
        cPais.setPlaceholder("No has seleccionado país");
        
        cPais.setItemCaptionGenerator(Pais::getNombreCompleto);
        
        cPais.setEmptySelectionAllowed(false);

        
        layout.addComponents(tfNombre, ap, tfFecha, cPais, button);
        
        setContent(layout);
    }
    
    private Collection<Pais> getListaPaises(){
    	List<Pais> listaPaises = new ArrayList<>();
    	
    	Pais pais1 = new Pais();
    	pais1.setNombreCompleto("España");
    	listaPaises.add(pais1);
    	
    	pais1 = new Pais();
    	pais1.setNombreCompleto("Francia");
    	listaPaises.add(pais1);
    	
    	return listaPaises;
    	
    }

	private String calcularMensaje(Persona persona) {
//		String sFecha = persona.getFecha().format(DateTimeFormatter.ofPattern("dd MM yyyy"));
		
		LocalDateTime fecha = persona.getFecha();
		
		String sFecha = "fecha no establecida";
		if (fecha!=null) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
			sFecha = fecha.format(dtf);
		}
		
//		return "Hola " + persona.getNombre() + " " + persona.getApellido() + " estamos a " + sFecha;
		String mensaje = String.format("Hola %s %s, estamos a %s en %s",
				persona.getNombre(), persona.getApellido(),sFecha, persona.getPais());
		return mensaje;
	}

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
