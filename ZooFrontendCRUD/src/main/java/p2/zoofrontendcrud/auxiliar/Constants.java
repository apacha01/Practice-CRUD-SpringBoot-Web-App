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
    //Frontend paths for jsp pages
    //Employee
    public final static String EMPLOYEE_VIEWS = "employeeViews/";
    //Species
    public final static String SPECIES_VIEWS = "speciesViews/";
    //Zones
    public final static String ZONE_VIEWS = "zoneViews/";
    //Zones
    public final static String HABITAT_VIEWS = "habitatViews/";
    //Itinerary
    public final static String ITINERARY_VIEWS = "itineraryViews/";
    //Assigning Entities to one another
    public final static String ASSIGN_VIEWS = "assignEntityViews/";
    
    //Common requests path for backend controllers
    public final static String PREFIX_REQUEST_URL = "http://localhost:8080/";
    public final static String CREATE_REQUEST_URL = "crear";
    public final static String GET_ALL_REQUEST_URL = "obtenertodos";
    public final static String GET_BY_ID_REQUEST_URL = "obtenerporid/";
    public final static String GET_BY_NAME_REQUEST_URL = "obtenerpornombre/";
    public final static String DELETE_BY_ID_REQUEST_URL = "borrar/";
    public final static String UPDATE_BY_ID_REQUEST_URL = "modificarporid/";
    public final static String GET_SPECIES_REQUEST_URL = "obtenerespecies";
    
    //Backend Requests Paths for p2.zoobackendcrud.controllers.EmployeeController
    public final static String EMPLOYEE_REQUEST_URL = "empleado/";
    public final static String GET_EMPLOYEE_BY_USERNAME_REQUEST_URL = "obtenerpornombredeusuario/";
    public final static String GET_EMPLOYEE_BY_TYPE_REQUEST_URL = "obtenerportipo/";
    public final static String GET_EMPLOYEE_ITINERARIES_REQUEST_URL = "obteneritinerarios";
    public final static String ADD_EMPLOYEE_SPECIES_REQUEST_URL = "agregarespecies";
    public final static String ADD_EMPLOYEE_ITINERARIES_REQUEST_URL = "agregaritinerarios";
    public final static String REMOVE_EMPLOYEE_SPECIES_REQUEST_URL = "removerespecies";
    public final static String REMOVE_EMPLOYEE_ITINERARIES_REQUEST_URL = "removeritinerarios";
    
    //Backend Requests Paths for p2.zoobackendcrud.controllers.SpeciesController
    public final static String SPECIES_REQUEST_URL = "especie/";
    public final static String GET_SPECIES_KEEPERS_REQUEST_URL = "obtenercuidadores";
    public final static String ADD_SPECIES_KEEPERS_REQUEST_URL = "agregarcuidadores";
    public final static String REMOVE_SPECIES_KEEPERS_REQUEST_URL = "removercuidadores";
    public final static String ADD_SPECIES_HABITATS_REQUEST_URL = "agregarhabitats";
    public final static String REMOVE_SPECIES_HABITATS_REQUEST_URL = "removerhabitats";
    
    //Backend Requests Paths for p2.zoobackendcrud.controllers.ItineraryController
    public final static String ITINERARIES_REQUEST_URL = "itinerario/";
    public final static String GET_ITINERARY_GUIDE_REQUEST_URL = "obtenerguia";
    
    //Backend Requests Paths for p2.zoobackendcrud.controllers.ZoneController
    public final static String ZONE_REQUEST_URL = "zona/";
    
    //Backend Requests Paths for p2.zoobackendcrud.controllers.HabitatController
    public final static String HABITAT_REQUEST_URL = "habitat/";
}
