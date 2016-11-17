package como.isil.mynotes.rest.entity;

import java.io.Serializable;

/**
 * Created by Fabricio on 11/11/2016.
 */
public class ErrorEntity implements Serializable {

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "FundoEntity{" +
                "code=" + code +
                ", message='" + message + '\'' +

                '}';
    }
}
