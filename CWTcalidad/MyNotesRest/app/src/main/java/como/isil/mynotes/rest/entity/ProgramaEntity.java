package como.isil.mynotes.rest.entity;

import java.io.Serializable;

/**
 * Created by Fabricio on 15/11/2016.
 */
public class ProgramaEntity implements Serializable {

    private int    idprograma;
    private String nombreprograma;
    private String objectId;

    public ProgramaEntity(int idprograma, String nombreprograma) {
        this.idprograma = idprograma;
        this.nombreprograma = nombreprograma;
    }

    public ProgramaEntity() {
    }

    public ProgramaEntity(String nombreprograma) {
        this.nombreprograma = nombreprograma;
    }

    public int getIdprograma() {
        return idprograma;
    }

    public void setIdprograma(int idprograma) {
        this.idprograma = idprograma;
    }

    public String getNombreprograma() {
        return nombreprograma;
    }

    public void setNombreprograma(String nombreprograma) {
        this.nombreprograma = nombreprograma;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "FundoEntity{" +
                "id=" + idprograma +
                ", name='" + nombreprograma + '\'' +

                '}';
    }
}
