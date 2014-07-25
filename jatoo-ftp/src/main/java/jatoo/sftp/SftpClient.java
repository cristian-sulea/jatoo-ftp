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

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * SFTP client
 * 
 * @author Cristian Sulea ( http://cristian.sulea.net )
 * @version 1.1, November 11, 2013
 */
public abstract class SftpClient {

	public enum MODE {
		OVERWRITE,
		RESUME,
		APPEND
	};

	public void connect(String host, int port, String username, String password) throws SftpClientException {
		connect(new SftpClientConnectionInfoProviderConstructor(host, port, username, password));
	}

	public void connect(SftpClientConnectionInfoProvider connectionInfoProvider) throws SftpClientException {

		if (isConnected()) {
			disconnect();
		}

		connectImpl(connectionInfoProvider.getConnectionInfo());
	}

	protected abstract void connectImpl(SftpClientConnectionInfo connectionInfo) throws SftpClientException;

	public abstract boolean isConnected();

	public abstract void disconnect() throws SftpClientException;

	//
	// upload

	public void upload(File srcFile, String dstFile) throws SftpClientException {
		upload(srcFile.getAbsolutePath(), dstFile, MODE.OVERWRITE);
	}

	public void upload(File srcFile, String dstFile, MODE mode) throws SftpClientException {
		upload(srcFile.getAbsolutePath(), dstFile, mode);
	}

	public void upload(String srcFile, String dstFile) throws SftpClientException {
		upload(srcFile, dstFile, MODE.OVERWRITE);
	}

	public abstract void upload(String srcFile, String dstFile, MODE mode) throws SftpClientException;

	public void upload(InputStream srcStream, String dstFile) throws SftpClientException {
		upload(srcStream, dstFile, MODE.OVERWRITE);
	}

	public abstract void upload(InputStream srcStream, String dstFile, MODE mode) throws SftpClientException;

	//
	// download

	public void download(String srcFile, File dstFile) throws SftpClientException {
		download(srcFile, dstFile.getAbsolutePath(), MODE.OVERWRITE);
	}

	public void download(String srcFile, File dstFile, MODE mode) throws SftpClientException {
		download(srcFile, dstFile.getAbsolutePath(), mode);
	}

	public void download(String srcFile, String dstFile) throws SftpClientException {
		download(srcFile, dstFile, MODE.OVERWRITE);
	}

	public abstract void download(String srcFile, String dstFile, MODE mode) throws SftpClientException;

	public void download(String srcFile, OutputStream dstStream) throws SftpClientException {
		download(srcFile, dstStream, MODE.OVERWRITE);
	}

	public abstract void download(String srcFile, OutputStream dstStream, MODE mode) throws SftpClientException;

	//
	// delete file

	public abstract void deleteFile(String file) throws SftpClientException;

	//
	// delete directory

	public abstract void deleteDirectory(String directory) throws SftpClientException;

	//
	// create directory

	public abstract void createDirectory(String directory) throws SftpClientException;

}
