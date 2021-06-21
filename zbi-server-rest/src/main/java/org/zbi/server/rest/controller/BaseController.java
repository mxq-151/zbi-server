package org.zbi.server.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.zbi.server.model.exception.LoginException;
import org.zbi.server.model.exception.ParseException;
import org.zbi.server.model.exception.QueryException;

import com.google.common.base.Throwables;

/**
 */
public class BaseController {

	public Map<String, Object> dataMap = new HashMap<>();

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(QueryException.class)
	@ResponseBody
	ErrorResponse handleError(HttpServletRequest req, Exception ex) {
		logger.error("", ex);
		return new ErrorResponse(req.getRequestURL().toString(), ex);
	}

	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(LoginException.class)
	@ResponseBody
	ErrorResponse handleLogin(HttpServletRequest req, Exception ex) {
		return new ErrorResponse(req.getRequestURL().toString(), ex);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ParseException.class)
	@ResponseBody
	ErrorResponse handleParse(HttpServletRequest req, Exception ex) {
		return new ErrorResponse(req.getRequestURL().toString(), ex);
	}

	public static class ErrorResponse {

		public String stacktrace;

		public String exception;

		public String url;

		public String msg;

		public ErrorResponse(String url, Exception exception) {
			super();

			this.url = url;
			this.exception = exception.getLocalizedMessage();
			this.msg = exception.getLocalizedMessage();
			this.stacktrace = Throwables.getStackTraceAsString(exception);
		}
	}

}
