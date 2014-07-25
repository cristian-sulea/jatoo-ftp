/*
 * Copyright (C) 2014 Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jatoo.sftp;

/**
 * SFTP client general exception
 * 
 * @author Cristian Sulea ( http://cristian.sulea.net )
 * @version 1.0, November 6, 2013
 */
@SuppressWarnings("serial")
public class SftpClientException extends Exception {

	public SftpClientException() {
		super();
	}

	public SftpClientException(String message) {
		super(message);
	}

	public SftpClientException(Throwable cause) {
		super(cause);
	}

	public SftpClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public SftpClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
