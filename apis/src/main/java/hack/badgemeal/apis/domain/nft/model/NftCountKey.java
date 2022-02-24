package hack.badgemeal.apis.domain.nft.model;

import hack.badgemeal.apis.domain.draw.model.DrawResult;
import hack.badgemeal.apis.domain.draw.model.DrawResultKey;
import lombok.Setter;

import java.io.Serializable;

@Setter
public class NftCountKey implements Serializable {
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
