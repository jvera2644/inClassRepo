package py.com.inclass.security;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author DiegoY
 */
public class AppContextListener implements ServletContextListener {
    ServletContext context;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        String rootPath = context.getRealPath("/");
        String propertyFile = rootPath + "/WEB-INF/conf/logconf.properties";        
        PropertyConfigurator.configure(propertyFile);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        context = sce.getServletContext();
    }
    
}
