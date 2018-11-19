package org.dynamic.datasource.common.exception;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 日志记录的异常类。
 *
 * @date 2018-10-26 11:04:07 <BR></>
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -1918382421601739908L;

	private String errorCode;

	private String errorMsg;

	private Object[] messageArguments;

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
		this.errorMsg = message;
	}

	public BusinessException(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}

	public BusinessException(String errorCode, Object ... messageArguments) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorCode;
		this.messageArguments = messageArguments;
	}

	public BusinessException(String errorCode, String message, Throwable cause, Object ... messageArguments) {
		super(message, cause);
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
		this.errorMsg = StringUtils.isEmpty(message) ? errorCode : message;
	}

	public BusinessException(String errorCode, String message, Object ... messageArguments) {
		super(message);
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
		this.errorMsg = StringUtils.isEmpty(message) ? errorCode : message;
	}

	public BusinessException(String errorCode, Throwable cause, Object ... messageArguments) {
		super(cause);
		this.errorCode = errorCode;
		this.messageArguments = messageArguments;
		this.errorMsg = errorCode;
	}

	/**
	 * print stacktrace to memory
	 * @param e
	 * @return
	 * @throws IOException
	 */
	public String getStackTraceMessage() throws IOException {
		StringWriter stringWriter = null;
		PrintWriter printWriter = null;
		try {
			stringWriter = new StringWriter();
			printWriter = new PrintWriter(stringWriter);
			this.printStackTrace(printWriter);
			stringWriter.flush();
			return stringWriter.toString();
		} finally {
			if (null != printWriter)
				printWriter.close();
			if (null != stringWriter)
				stringWriter.close();
		}
	}

	public String getErrorCode() {
		return errorCode;
	}

	public Object[] getMessageArguments() {
		return messageArguments;
	}

	@Override
	public String getMessage() {
		return errorMsg;
	}

}


