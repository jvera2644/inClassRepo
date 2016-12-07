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
    
    PAR_CAN_INT_FAL("Cantidad de Intentos Fallidos","PAR_CAN_INT_FAL",0),
    PAR_TAM_ARC_JUS("Tamaño Archivo Justificativo","PAR_TAM_ARC_JUS",0),
    PAR_EXC_LLE_TAR("Tipo de excepción por llegada tardía","PAR_EXC_LLE_TAR",0),
    PAR_CAN_MIN_PRO("Cantidad de minutos por llegada tardía Profesor","PAR_CAN_MIN_PRO",0),
    PAR_CAN_MIN_ALU("Cantidad de minutos por llegada tardía Alumno","PAR_CAN_MIN_ALU",0);
    
    private final String label;
    private final String codigo;
    private final int valor;
    
    private ParametroEnum(String label, String codigo, int valor){
        this.label = label;
        this.valor = valor;
        this.codigo = codigo;
    }

    public String getLabel() {
        return label;
    }

    public int getValor() {
        return valor;
    }

    public String getCodigo() {
        return codigo;
    }
    
}
