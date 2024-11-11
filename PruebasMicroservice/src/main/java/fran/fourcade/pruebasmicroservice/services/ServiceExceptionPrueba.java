package fran.fourcade.pruebasmicroservice.services;

public class ServiceExceptionPrueba extends Throwable {
    public ServiceExceptionPrueba(String message) {
        super(message);
    }
    public ServiceExceptionPrueba(String message, Throwable cause) {
      super(message, cause);
    }
}
