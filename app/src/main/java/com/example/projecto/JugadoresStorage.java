package com.example.projecto;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JugadoresStorage {

    Executor executor = Executors.newSingleThreadExecutor();
    private final AppBaseDeDatos.JugadoresDao jugadoresDao;

    public JugadoresStorage(Application application) {
        jugadoresDao = AppBaseDeDatos.getInstance(application).obtenerJugadoresDao();
    }

    public void insertar(String nombre, String equipo, String imagenSeleccionada) {
        executor.execute(() -> {
            jugadoresDao.insertarJugador(new Jugador(nombre, equipo, imagenSeleccionada));
        });
    }

    public LiveData<List<Jugador>> obtener() {
         return jugadoresDao.obtenerJugadores();
    }
}
