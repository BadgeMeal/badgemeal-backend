package hack.badgemeal.apis.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 404 NOT_FOUND */
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴 정보를 찾을 수 없습니다."),

    /* 409 CONFLICT */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
