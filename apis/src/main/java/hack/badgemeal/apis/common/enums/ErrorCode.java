package hack.badgemeal.apis.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 404 NOT_FOUND */
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 메뉴 정보를 찾을 수 없습니다."),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 주소에 매핑된 정보를 찾을 수 없습니다."),
    DRAW_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "현재 회차, 해당 주소에 매핑된 뽑기 결과가 존재하지 않습니다."),

    /* 409 CONFLICT */
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다."),

    /* 500 INTERNAL_SERVER_ERROR */
    KAS_API(HttpStatus.INTERNAL_SERVER_ERROR, "KAS API Request 도중 에러가 발생했습니다."),
    KAS_METADATA_API(HttpStatus.INTERNAL_SERVER_ERROR, "KAS METADATA API Request 도중 에러가 발생했습니다."),
    CAVER_OWNEROF_CALL(HttpStatus.INTERNAL_SERVER_ERROR, "CAVER OwnerOf Call 도중 에러가 발생했습니다."),
    MINT_DATA_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "Mint Data 저장이 실패하였습니다."),
    MINT_DATA_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "현재 회차, 해당 주소에 매핑된 메타데이터가 이미 존재합니다."),
    RECEIPT_IMAGE_IS_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "첨부된 이미지 파일이 없습니다."),
    IPFS_IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "IPFS 이미지 업로드에 실패하였습니다."),
    DRAW_PUT_COUNT_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "랜덤 뽑기 수정 횟수가 이전 횟수보다 2 이상 클 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String detail;
}
