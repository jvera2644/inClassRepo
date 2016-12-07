/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.inclass.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 *
 * @author Edu
 */
public class DateUtil {
    
    final static long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
        
    private DateUtil(){}
    
    public static Date getPrimerDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMinimum(Calendar.DAY_OF_MONTH),
                cal.getMinimum(Calendar.HOUR_OF_DAY),
                cal.getMinimum(Calendar.MINUTE),
                cal.getMinimum(Calendar.SECOND));
        return cal.getTime();
    }

    public static Date getUltimoDiaDelMes() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.getActualMaximum(Calendar.DAY_OF_MONTH),
                cal.getMaximum(Calendar.HOUR_OF_DAY),
                cal.getMaximum(Calendar.MINUTE),
                cal.getMaximum(Calendar.SECOND));
        return cal.getTime();
    }
      
    public static int calcularEdad(Date fechaNacimiento){
        
        try {
            
            Calendar birth = new GregorianCalendar();
            Calendar today = new GregorianCalendar();
            
            int age = 0;
            int factor = 0;
            
            Date currentDate = new Date(); //current date
            birth.setTime(fechaNacimiento);
            today.setTime(currentDate);
            
            if (today.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
                if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {
                    if (today.get(Calendar.DATE) > birth.get(Calendar.DATE)) {
                        factor = -1; //Aun no celebra su cumpleaÃ±os
                    }
                } else {
                    factor = -1; //Aun no celebra su cumpleaÃ±os
                }
            }
            
            age = (today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)) + factor;
            return age;
            
        } catch (Exception e) {
            return -1;
        }
        
    }
    
    public static String formaterDateToString(Date fecha){
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String date = DATE_FORMAT.format(fecha);
        return date;
    }
    
    
    public static Date formaterStringToDate(String fecha) throws ParseException{
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        Date date = DATE_FORMAT.parse(fecha);
        return date;
    }
    
    
    public static String obtenerDiaSemana(Date fecha) {
        String[] dias = {"domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int numeroDia = cal.get(Calendar.DAY_OF_WEEK);
        return dias[numeroDia - 1];
    }
    

}
