/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.util;

import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Edu
 */

@ManagedBean
@ApplicationScoped
public class ChannelsBean {
    Map<String, String> channels = new HashMap<String, String>();

    public void addChannel(String user, String channel) {
        channels.put(user, channel);
    }

    public String getChannel(String user) {
        return channels.get(user);
    }
}
