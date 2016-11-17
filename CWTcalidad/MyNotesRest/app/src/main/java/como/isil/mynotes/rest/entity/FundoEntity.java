package como.isil.mynotes.rest.entity;

import java.io.Serializable;

/**
 * Created by Fabricio on 11/11/2016.
 */
public class FundoEntity  implements Serializable {

    private int idproductor;
    private String nombreproductor;
    private String objectId;


    public FundoEntity(int idproductor, String nombreproductor, String objectId) {
        this.idproductor = idproductor;
        this.nombreproductor = nombreproductor;
        this.objectId = objectId;
    }

    public FundoEntity() {

    }


    public FundoEntity(int idproductor, String nombreproductor) {
        this.idproductor = idproductor;
        this.nombreproductor = nombreproductor;
    }

    public FundoEntity(String nombreproductor) {
        this.nombreproductor = nombreproductor;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "FundoEntity{" +
                "id=" + idproductor +
                ", name='" + nombreproductor + '\'' +

                '}';
    }




}
