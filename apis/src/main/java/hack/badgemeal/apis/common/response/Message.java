package hack.badgemeal.apis.common.response;

import hack.badgemeal.apis.common.enums.ResponseStatus;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Message {
    private ResponseStatus status;
    private Map<?, ?> data;
}

