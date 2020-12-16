package com.example.projecto;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Jugador {
    @PrimaryKey(autoGenerate = true)
    int id;
    String nombre;
    String equipo;
    String imagen;

    public Jugador(String nombre, String equipo, String imagen) {
        this.nombre = nombre;
        this.equipo = equipo;
        this.imagen = imagen;
    }
}
