package py.com.inclass.converter;

import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public abstract class MapConverter implements Converter {

    protected abstract Map<Object, String> getMap();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        synchronized (getMap()) {
            if (!getMap().containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                getMap().put(entity, uuid);
//                System.out.println(">>>>> " + entity + " = " + uuid);
                return uuid;
            } else {
//                System.out.println(">>>>> " + entity + " = " + getMap().get(entity));
                return getMap().get(entity);
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        for (Entry<Object, String> entry : getMap().entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
