package como.isil.mynotes.rest.storage.entity.visita;

import java.util.List;

import como.isil.mynotes.rest.entity.VisitaEntity;

/**
 * Created by Fabricio on 28/11/2016.
 */
public class VisitasResponse {

    private String message;

    private int offset;
    private List<VisitaEntity> data;

    private Object nextPage;
    private int totalObjects;


    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<VisitaEntity> getData() {
        return data;
    }

    public void setData(List<VisitaEntity> data) {
        this.data = data;
    }

    public Object getNextPage() {
        return nextPage;
    }

    public void setNextPage(Object nextPage) {
        this.nextPage = nextPage;
    }

    public int getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(int totalObjects) {
        this.totalObjects = totalObjects;
    }
}
