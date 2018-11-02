package mboss.tsm.Model;

import java.io.Serializable;

public class Tag implements Serializable {
    private String avatar;
    private String name;

    public Tag(String avatar, String name) {
        this.avatar = avatar;
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
