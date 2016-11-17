package como.isil.mynotes.rest.entity;

import java.io.Serializable;

/**
 * Created by Fabricio on 15/11/2016.
 */
public class VisitaEntity implements Serializable {

    int idvisita;
    String semana;
    String idfundo;
    String idcalificacion;
    String fecvisita;
    String contenedor;
    String comentario;
    private String objectId;

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

    public String getContenedor() {
        return contenedor;
    }

    public void setContenedor(String contenedor) {
        this.contenedor = contenedor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public VisitaEntity() {
    }

    public VisitaEntity(int idvisita, String semana, String idfundo, String idcalificacion, String fecvisita, String contenedor, String comentario) {
        this.idvisita = idvisita;
        this.semana = semana;
        this.idfundo = idfundo;
        this.idcalificacion = idcalificacion;
        this.fecvisita = fecvisita;
        this.contenedor = contenedor;
        this.comentario = comentario;

    }

    public VisitaEntity(String semana, String idfundo, String idcalificacion, String fecvisita, String contenedor, String comentario) {
        this.semana = semana;
        this.idfundo = idfundo;
        this.idcalificacion = idcalificacion;
        this.fecvisita = fecvisita;
        this.contenedor = contenedor;
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "VisitaEntity{" +
                "idvisita=" + idvisita +
                ", semana'" + semana + '\'' +
                ", idfundo'" + idfundo + '\'' +
                ", idcalificacion'" + idcalificacion + '\'' +
                ", contenedor'" + contenedor + '\'' +
                ", comentario'" + comentario + '\'' +
                ", objectId'" + objectId + '\'' +
                '}';
    }

}
