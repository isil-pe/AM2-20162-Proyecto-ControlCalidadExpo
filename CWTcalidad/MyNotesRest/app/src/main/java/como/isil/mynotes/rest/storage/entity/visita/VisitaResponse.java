package como.isil.mynotes.rest.storage.entity.visita;

/**
 * Created by Fabricio on 28/11/2016.
 */
public class VisitaResponse {
    private int idvisita;
    private String semana;
    private String idfundo;
    private String idcalificacion;
    private String fecvisita;
    private String contenedor;
    private String comentario;
    private String objectId;
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

    private static final int SUCCESS=0;
    private int code;

    public boolean isSuccess()
    {
        if(this.code==SUCCESS)return true;
        return false;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public int getIdvisita() {
        return idvisita;
    }

    public void setIdvisita(int idvisita) {
        this.idvisita = idvisita;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getIdfundo() {
        return idfundo;
    }

    public void setIdfundo(String idfundo) {
        this.idfundo = idfundo;
    }

    public String getIdcalificacion() {
        return idcalificacion;
    }

    public void setIdcalificacion(String idcalificacion) {
        this.idcalificacion = idcalificacion;
    }

    public String getFecvisita() {
        return fecvisita;
    }

    public void setFecvisita(String fecvisita) {
        this.fecvisita = fecvisita;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
