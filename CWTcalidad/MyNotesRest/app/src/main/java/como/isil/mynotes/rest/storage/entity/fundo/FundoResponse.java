package como.isil.mynotes.rest.storage.entity.fundo;

/**
 * Created by em on 8/06/16.
 *
 * {
 "created": 1465426355000,
 "name": "nota desde android",
 "___class": "Notes",
 "description": "esta es una nota de prueba",
 "ownerId": null,
 "updated": null,
 "objectId": "F9E7E58F-4409-08CB-FFCB-64D5BB741100",
 "__meta": "{\"relationRemovalIds\":{},\"selectedProperties\":[\"created\",\"___saved\",\"name\",\"___class\",\"description\",\"ownerId\",\"updated\",\"objectId\"],\"relatedObjects\":{}}"
 }
 */
public class FundoResponse {


    private int idproductor;
    private String nombreproductor;
    private String objectId;


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
}
