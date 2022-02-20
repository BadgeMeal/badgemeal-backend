package hack.badgemeal.apis.domain.draw.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class DrawResultKey implements Serializable {
    private static final long serialVersionUID = 1L;

    private String address;
    private long round;

    @Override
    public boolean equals(Object o) {
        return ((o instanceof DrawResult) && address == ((DrawResultKey)o).getAddress() && round == ((DrawResultKey) o).getRound());
    }

    @Override
    public int hashCode() {
        return (int)(Integer.valueOf(address) ^ round);
    }
}
