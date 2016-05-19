package py.com.inclass.converter;

import java.util.HashMap;
import java.util.Map;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author LiLo
 */
@FacesConverter(value = "hashConverter")
public class HashConverter extends MapConverter {

    private static Map<Object, String> map = new HashMap<Object, String>();

    @Override
    protected Map<Object, String> getMap() {
        return map;

    }
}
