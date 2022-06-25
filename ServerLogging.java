package serverpack;

import java.io.Serial;
import java.io.Serializable;

public class ServerLogging implements Serializable {

    @Serial
    private static final long serialVersionUID = 3162171813575997763L;
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
