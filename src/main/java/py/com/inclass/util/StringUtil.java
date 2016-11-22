/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package py.com.inclass.util;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Edu
 */
public class StringUtil {
    
    private static final String PATTERN_DECIMAL = "###,###.##";
    
    private StringUtil(){}
    
    public static String converterString(String stringio){
        return stringio.replaceAll("\\.","");
    }
    
    public static String formaterDoubeToString(Double number){
        DecimalFormat formateador = new DecimalFormat(PATTERN_DECIMAL); 
        return formateador.format(number);
    }
    
    public static String formaterIntegerToString(Integer number){
        DecimalFormat formateador = new DecimalFormat(PATTERN_DECIMAL); 
        return formateador.format(number);
    }
    
    public static Date convertirHoraStringADate(String hora){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return(sdf.parse(hora, new ParsePosition(0)));
    }
}
