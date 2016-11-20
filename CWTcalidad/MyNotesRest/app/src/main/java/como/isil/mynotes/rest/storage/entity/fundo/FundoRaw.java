package como.isil.mynotes.rest.storage.entity.fundo;

/**
 * Created by em on 8/06/16.
 */
public class FundoRaw {

    private int idproductor;
    private String nombreproductor;
    private String estado;
    private String sincro;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getSincro() {
        return sincro;
    }

    public void setSincro(String sincro) {
        this.sincro = sincro;
    }

    public int getIdproductor() {
        return idproductor;
    }

    public void setIdproductor(int idproductor) {
        this.idproductor = idproductor;
    }

    public String getNombreproductor() {
        return nombreproductor;
    }

    public void setNombreproductor(String nombreproductor) {
        this.nombreproductor = nombreproductor;
    }
}
