/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import p2.zoobackendcrud.auxiliar.TYPE_ENUM;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "employees")
public class Employee implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_employee", nullable = false)
    private Integer id;
    
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TYPE_ENUM type;
    
    @Column(name = "user_name", nullable = false, length = 16)
    private String userName;
    
    @Column(name = "password", nullable = false, length = 16)
    private String password;
    
    @Column(name = "name", nullable = false, length = 30)
    private String name;
    
    @Column(name = "address", nullable = false, length = 30)
    private String address;
    
    @Column(name = "phone", nullable = false, length = 20)
    private String phone;
    
    @Column(name = "first_day", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "UTC")
    @Temporal(TemporalType.DATE)
    private Date firstDay;

    //All atributes but id (autogenerated)
    public Employee(TYPE_ENUM type, String userName, String password, String name, String address, String phone, Date firstDay) {
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.firstDay = firstDay;
    }
    
    public String formatedFirstDayAsString(){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(this.firstDay);
    }
    
}