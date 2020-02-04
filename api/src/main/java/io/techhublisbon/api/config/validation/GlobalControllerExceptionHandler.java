package io.techhublisbon.api.config.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path.Node;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ParamError handleMismatch(MethodArgumentTypeMismatchException exception) {
		final String paramName = exception.getName();
		final String message;

		if (paramName.equals("startTime") || paramName.equals("endTime")) {
			message = paramName + " must be a date in the format yyyy-MM-dd";
		} else if (paramName.equals("vehicleId")) {
			message = paramName + " must be an integer";
		} else if (paramName.equals("operator")) {
			message = paramName + " must be a string with 2 characters";
		} else if (paramName.equals("atStop")) {
			message = paramName + ", if present, must be true";
		} else {
			message = "Unsupported param";
		}

		return new ParamError(paramName, message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ParamError handleMismatch(MissingServletRequestParameterException exception) {
		final String paramName = exception.getParameterName();
		final String message = paramName + " query parameter is required";

		return new ParamError(paramName, message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public ParamError handleViolation(ConstraintViolationException exception) {
		ConstraintViolation<?> violation = exception.getConstraintViolations().iterator().next();
		String field = null;

		for (Node node : violation.getPropertyPath()) {
			field = node.getName();
			if (field.equals("operator")) {
				return new ParamError(field, "operator must be a string with 2 characters");
			} else if (field.equals("atStop")) {
				return new ParamError(field, "atStop, if present, must be true");
			}
		}

		return new ParamError(field, "Unsupported param");
	}
}
