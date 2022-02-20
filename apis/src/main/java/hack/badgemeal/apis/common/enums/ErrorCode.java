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
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),

    /* 500 INTERNAL_SERVER_ERROR */
    KAS_API(HttpStatus.INTERNAL_SERVER_ERROR, "KAS API Request 도중 에러가 발생했습니다."),
    KAS_METADATA_API(HttpStatus.INTERNAL_SERVER_ERROR, "KAS METADATA API Request 도중 에러가 발생했습니다."),
    CAVER_OWNEROF_CALL(HttpStatus.INTERNAL_SERVER_ERROR, "CAVER OwnerOf Call 도중 에러가 발생했습니다."),
    MINT_DATA_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "이미 현재 회차에 해당 주소로 등록된 Mint data가 존재합니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
