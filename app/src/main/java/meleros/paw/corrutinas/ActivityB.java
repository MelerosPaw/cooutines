package meleros.paw.corrutinas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.LinkedList;
import java.util.List;

import meleros.paw.corrutinas.business.LibroVO;

public class ActivityB extends BaseActivity {

    private BViewModel viewModel;
    private List<String> ids;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment();

        viewModel.getLibrosLiveData().observe(this, libros -> {
            final StringBuilder listadoLibros = new StringBuilder();
            for (LibroVO libro : libros) {
                listadoLibros.append(libro.getTitulo());
                listadoLibros.append("\n");
            }
            getTextView().setText(listadoLibros);
        });

        ids = new LinkedList<>();
        ids.add("1");
        ids.add("3");
        ids.add("5");
//        JEsplatoon.noGuardar = () -> Log.i("WIP", "En la lambda");
//        new JEsplatoon().nuevoHilo(JEsplatoon.noGuardar);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_b;
    }

    @NonNull
    @Override
    public BaseViewModel getBaseViewModel() {
        viewModel = new ViewModelProvider(this).get(BViewModel.class);
        return viewModel;
    }

    private void loadFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment, new MainFragment())
                .commit();
    }
}
