package hasses.magical.tools.beans.controller;

import java.io.IOException;
import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MagicExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(InvalidParameterException.class)
    public void springHandleInvalidParameterException(HttpServletResponse response) throws IOException {
		  response.getWriter().write("aaaaaaaaappppppppppppppppppppaaaaaaaaaaaaa");
		response.sendError(HttpStatus.BAD_REQUEST.value());
      
    }
	
	@ExceptionHandler(Exception.class)
    public void springHandleException(HttpServletResponse response) throws IOException {
        response.getWriter().write("aaaaaaaaappppppppppppppppppppaaaaaaaaaaaaa");
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());

    }
}
