/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoofrontendcrud.auxiliar;

/**
 *
 * @author Agustín Pacheco
 */
public class Constants {
    public final static String PREFIX_REQUEST_URL = "http://localhost:8080/";
    
    //Backend Requests Paths for p2.zoobackendcrud.controllers.EmployeeController
    public final static String EMPLOYEE_REQUEST_URL = "empleado/";
    public final static String CREATE_EMPLOYEE_REQUEST_URL = "crear";
    public final static String GET_EMPLOYEE_BY_USERNAME_REQUEST_URL = "obtenerpornombredeusuario/";
    public final static String GET_ALL_EMPLOYEES_REQUEST_URL = "obtenertodos";
    public final static String GET_EMPLOYEE_BY_ID_REQUEST_URL = "obtenerporid/";
    public final static String DELETE_EMPLOYEE_BY_ID_REQUEST_URL = "borrar/";
    public final static String UPDATE_EMPLOYEE_BY_ID_REQUEST_URL = "modificarporid/";
    public final static String GET_EMPLOYEE_SPECIES_REQUEST_URL = "obtenerespecies";
    public final static String GET_EMPLOYEE_ITINERARIES_REQUEST_URL = "obteneritinerarios";
}
