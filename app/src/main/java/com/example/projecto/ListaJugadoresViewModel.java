package com.example.projecto;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class ListaJugadoresViewModel  extends AndroidViewModel {


    private final JugadoresStorage jugadoresStorage;
    MutableLiveData<String> imagenSeleccionada = new MutableLiveData<>();

    public ListaJugadoresViewModel(@NonNull Application application) {
        super(application);
        jugadoresStorage = new JugadoresStorage(application);
    }

    void establecerImagenSeleccionada(String uri){
        imagenSeleccionada.setValue(uri);
    }

    public void insertar(String nombre, String equipo, String imagenSeleccionada) {
        jugadoresStorage.insertar(nombre,equipo,imagenSeleccionada);
    }

    public LiveData<List<Jugador>> obtener() {
      return jugadoresStorage.obtener();
    }
}