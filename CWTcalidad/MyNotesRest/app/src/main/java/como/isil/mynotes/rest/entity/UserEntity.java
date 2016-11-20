package como.isil.mynotes.rest.entity;

import java.io.Serializable;

/**
 * Created by em on 8/06/16.
 */
public class UserEntity implements Serializable {

    private int id;
    private String email;
    private String name;
    private String objectId;
    private String token;

    public UserEntity() {
    }

    public UserEntity(String email, String name, String token) {
        this.email = email;
        this.name = name;
        this.token = token;
    }

    public UserEntity(int id, String email, String name, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
