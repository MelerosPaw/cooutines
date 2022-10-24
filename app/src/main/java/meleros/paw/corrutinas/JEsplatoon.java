package meleros.paw.corrutinas;

public class JEsplatoon {

    public static EjecutarEnUnHilo noGuardar;

    public interface EjecutarEnUnHilo {
        void ejecutar();
    }

    public void nuevoHilo(EjecutarEnUnHilo ejecutarEnUnHilo) {
        new Thread(() -> {
            try {
                Thread.sleep(4000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ejecutarEnUnHilo.ejecutar();
        }).start();
    }
}
