package hack.badgemeal.apis.common.exceptions;


public class BadRequestParamException extends RuntimeException {
  public BadRequestParamException(String msg) {
    super(msg);
  }
}
