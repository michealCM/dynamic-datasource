package org.dynamic.datasource.common.exception;

public class DynamicDataSourceException extends BusinessException{

	private static final long serialVersionUID = 1L;

	public DynamicDataSourceException(String errorMsg) {
		super("dynamic.datasource.exception",errorMsg);
	}

	public DynamicDataSourceException(Object... messageArguments) {
		super("dynamic.datasource.exception", messageArguments);
	}

	public DynamicDataSourceException(String message, Object... messageArguments) {
		super("dynamic.datasource.exception", message, messageArguments);
	}

	public DynamicDataSourceException(String message, Throwable cause, Object... messageArguments) {
		super("dynamic.datasource.exception", message, cause, messageArguments);
	}

	public DynamicDataSourceException(Throwable cause, Object... messageArguments) {
		super("dynamic.datasource.exception", cause, messageArguments);
	}

}
