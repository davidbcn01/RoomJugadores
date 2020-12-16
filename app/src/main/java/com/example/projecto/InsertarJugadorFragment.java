package com.example.projecto;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.projecto.databinding.FragmentInsertarJugadorBinding;
import com.example.projecto.databinding.FragmentListaJugadoresBinding;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;


public class InsertarJugadorFragment extends Fragment {

    private FragmentInsertarJugadorBinding binding;
    private ListaJugadoresViewModel listaJugadoresViewModel;
    private NavController navController;
    String imagenSeleccionada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentInsertarJugadorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        listaJugadoresViewModel = new ViewModelProvider(requireActivity()).get(ListaJugadoresViewModel.class);


        listaJugadoresViewModel.imagenSeleccionada.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String imagen) {
                imagenSeleccionada = imagen;
                Glide.with(requireView()).load(imagen).into(binding.previsualizarPortada);
            }
        });

        binding.previsualizarPortada.setOnClickListener(v -> {
            abrirGaleria();
        });

        binding.insertar.setOnClickListener(v -> {
                String nombre = binding.nombre.getText().toString();
                String equipo = binding.equipo.getText().toString();

                listaJugadoresViewModel.insertar(nombre,equipo,imagenSeleccionada);
                listaJugadoresViewModel.establecerImagenSeleccionada(null);
                navController.popBackStack();
        });
    }

    private void abrirGaleria(){
        if (checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            lanzadorGaleria.launch("image/*");
        } else {
            lanzadorPermisos.launch(READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> lanzadorGaleria =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if(uri != null) {
                    listaJugadoresViewModel.establecerImagenSeleccionada(uri.toString());
                }
            });

    private final ActivityResultLauncher<String> lanzadorPermisos =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    lanzadorGaleria.launch("image/*");
                }
            });

     /*   @Override
        public void onClick(View v) {

            if (checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
                abrirGaleria();
            } else {
                lanzadorPermisos.launch(READ_EXTERNAL_STORAGE);
            }


        }
    });

    listaJugadoresViewModel.imagenSelecionada.observe(getViewLifecycleOwner(),uri -> {
                Glide.with(requireView()).load(uri).into(binding.previsualizarPortada);
            }
            );
    } */


   /* private void abrirGaleria(){
        lanzadorGaleria.launch("image/*");
    }*/
}