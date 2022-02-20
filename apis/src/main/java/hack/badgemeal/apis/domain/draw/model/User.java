package hack.badgemeal.apis.domain.draw.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bm_user")
public class User {
    // 유저 Address
    @Id
    private String address;
}
