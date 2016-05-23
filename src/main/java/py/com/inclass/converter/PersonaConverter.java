/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.inclass.converter;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import py.com.inclass.entities.Persona;
import py.com.inclass.facade.PersonaFacade;


/**
 *
 * @author Edu
 */
@ManagedBean
@RequestScoped
public class PersonaConverter implements Converter {
    
    @EJB
    PersonaFacade personaFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        
        try{
            if (value != null && !value.trim().equals("")) {
                List<Persona> personas = personaFacade.findByNroDocumentoOnComplete(value);
                return personas != null && !personas.isEmpty() ? personas.get(0) : null;
        } else {
            return null;
        }
        }catch(Exception e){}
        
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Persona persona = (Persona) value;
            return persona.getNumeroDocumento();
        } else {
            return null;
        }
    }
}
