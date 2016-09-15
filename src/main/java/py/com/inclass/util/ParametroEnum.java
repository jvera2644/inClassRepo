/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.inclass.util;

/**
 *
 * @author Edu
 */
public enum ParametroEnum {
    
    PAR_CAN_INT_FAL("Cantidad de Intentos Fallidos",3);
    
    private final String label;
    private final int valor;
    
    private ParametroEnum(String label, int valor){
        this.label = label;
        this.valor = valor;
    }

    public String getLabel() {
        return label;
    }

    public int getValor() {
        return valor;
    }

}
