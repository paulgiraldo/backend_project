package com.codigo.ms_login.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RolEntity {
    @Id
    @Column(name="rol_cod")
    private String rolCod;

}
