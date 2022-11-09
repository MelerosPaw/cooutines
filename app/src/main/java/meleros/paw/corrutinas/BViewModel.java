package meleros.paw.corrutinas;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;
import java.util.List;

import meleros.paw.corrutinas.business.LibroBO;
import meleros.paw.corrutinas.business.LibroDTO;
import meleros.paw.corrutinas.business.LibroVO;

public class BViewModel extends BaseViewModel {

    private final String loremIpsum = "NonGrouppedUniqueShippingMethods()`) SECUENCIALES pero muy seguidas en el tiempo " +
            "parecen estar provocando que la segunda dé un 500 y se queda el cargando pillado. 1. Se" +
            " hace una llamada cuando entras al `ShippingMethodFragment` de los métodos de envío " +
            "para tener la lista de métodos de envío y poder seleccionarlo." +
            "2. Seleccionas a domicilio y seleccionas la dirección y este mismo *fragment* hace " +
            "otra vez la misma llamada. ¿Para qué? ¿No puede ir directamente tras seleccionar la " +
            "dirección al *summary* e informar de la dirección seleccionada a quien sea que tiene " +
            "que informar? Hasta que no termina esa llamada no cambia el `ShippingMethodLiveData::" +
            "shippingDataHasBeenSelectedLiveData` cuyo *observer* manda abrir el " +
            "`OrderSummaryFragment`. Por lo tanto, al comprobación de que no se haga la llamada " +
            "mientras esté cargando es inútil pero no está de más." +
            "3. En el `OrderSummaryFragment` la lista de productos (`SummaryCartItemsFragment`) " +
            "inmediatamente hace otra vez la llamada para obtener los métodos de envío (que " +
            "deberíamos tener ya de la llamada anterior) para traer de nuevo todos los " +
            "`ShippingMethodBO`. Se queda con el que coincida con el `ShippingBundleBO` que haya " +
            "seleccionado en sesión con él calcula las fechas de entrega para pintar los artículos " +
            "divididos por fecha de entrega. Acabamos de entrar en la pantalla y los métodos están " +
            "cacheados. ¿Por qué no podemos usarlos? ¿Van a cambiar estando dentro del summary? " +
            "¿Se puede acceder al summary sin seleccionar método? Sería luego obligatorio pasar " +
            "por la pantalla de seleccionar método y, al volver, ya tendríamos los métodos " +
            "guardados para llamar de nuevo a lo que hubiera que llamar. ¿Hay algún peligro en " +
            "aceptar caché en esta llamada? 4. El resultado de esta llamada es devuelto a la " +
            "pantalla mediante el `linkDeliveryInfoByGroupCartItems`, que es un `MergedLiveData` " +
            "que contempla que, si falla la petición, tiene una *brand var* que dice que nanai de " +
            "la China de pintar los ítems sin los encabezados, pero que no vuelve a lanzar la " +
            "petición y deja el cargando puesto. 5. Cuando añades una tarjeta y sales, si el " +
            "cargando estaba puesto, se queda la pantalla completa cargando y no se puede avanzar.";
    @NonNull
    private final MutableLiveData<List<LibroVO>> librosLiveData = new MutableLiveData<>();

    @NonNull
    public LiveData<List<LibroVO>> getLibrosLiveData() {
        return librosLiveData;
    }

    public void buscarLibrosSinCancelar(final List<String> ids, Context context) {
        new Thread(() -> buscarLibros(ids, context)).start();
    }

    private void buscarLibros(final List<String> ids, Context context) {
        try {
            get_loadingLiveData().postValue(true);
            printAndPost("Mapeando identificadores...");
            final List<Integer> idsLibros = new LinkedList<>();

            for (final String id : ids) {
                idsLibros.add(Integer.valueOf(id));
            }

            printAndPost("Obteniendo libros...");
            final List<LibroDTO> dtos = getLibros(idsLibros);

            printAndPost("Mapeando a BO...");
            final LinkedList<LibroBO> bos = new LinkedList<>();

            for (final LibroDTO libro : dtos) {
                bos.add(toBo(libro));
            }

            printAndPost("Mapeando a VO...");
            final LinkedList<LibroVO> vos = new LinkedList<>();

            for (final LibroBO bo : bos) {
                vos.add(toVo(bo));
            }

            printWithTag("Ya está " + context.getString(R.string.app_name));
            librosLiveData.postValue(vos);
            get_loadingLiveData().postValue(false);
        } catch (Exception e) {

        }
    }

    @NonNull
    private List<LibroDTO> getLibros(final List<Integer> idLibros) throws InterruptedException {
        Thread.sleep(3000L);
        final List<LibroDTO> dtos = new LinkedList<>();
        for (final Integer id : idLibros) {
            for (final LibroDTO libro : OtherViewModel.Companion.getBiblioteca()) {
                if (libro.getId() == id) {
                    dtos.add(libro);
                    break;
                }
            }
        }

        return dtos;
    }

    private LibroBO toBo(final LibroDTO dto) throws InterruptedException {
        Thread.sleep(1000L);
        return new LibroBO(dto.getTitulo(), dto.getIsbn());
    }

    private LibroVO toVo(final LibroBO bo) throws InterruptedException {
        Thread.sleep(1000L);
        return new LibroVO(bo.getTitulo(), bo.getIsbn());
    }
}
