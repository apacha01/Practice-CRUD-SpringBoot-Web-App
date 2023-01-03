/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Agustín Pacheco
 */
@Entity
@Data
@NoArgsConstructor
public class Employee implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Date firstDay;

    public Employee(TYPE_ENUM type, String userName, String password, String name, String address, String phone, Date firstDay) {
        this.type = type;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.firstDay = firstDay;
    }
    
}

public enum TYPE_ENUM {
    ADMIN,
    CUIDADOR,
    GUIA
}