package tutorial.MisionTIC.AN.seguridad.Exception;

import org.springframework.http.HttpStatus ;
import org.springframework.web.bind.annotation . ResponseStatus ;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private String msg;
    public ResourceNotFoundException(String msg){
        super(msg);
        this.msg = msg;
    }
}
