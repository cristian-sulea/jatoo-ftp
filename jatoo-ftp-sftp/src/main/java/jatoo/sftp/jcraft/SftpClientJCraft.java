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

package jatoo.sftp.jcraft;

import jatoo.sftp.SftpClient;
import jatoo.sftp.SftpClientConnectionInfo;
import jatoo.sftp.SftpClientException;
import jatoo.sftp.SftpClientExceptionBadConnectionInfo;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * JCraft implementation for {@link SftpClient}.
 * 
 * @author Cristian Sulea ( http://cristian.sulea.net )
 * @version 1.1, November 11, 2013
 */
public class SftpClientJCraft extends SftpClient {

	private static final Log LOGGER = LogFactory.getLog(SftpClient.class);

	private Session session;
	private ChannelSftp sftp;

	@Override
	protected void connectImpl(SftpClientConnectionInfo connectionInfo) throws SftpClientException {

		JSch jsch = new JSch();

		//
		// connect

		try {
			session = jsch.getSession(connectionInfo.getUsername(), connectionInfo.getHost(), connectionInfo.getPort());
			session.setUserInfo(new SftpClientJCraftUserInfo(connectionInfo.getPassword()));
			session.connect();
		}

		catch (JSchException e) {
			throw new SftpClientExceptionBadConnectionInfo(e);
		}

		//
		// open SFTP connection

		try {

			Channel c = session.openChannel("sftp");
			c.connect();

			sftp = (ChannelSftp) c;
		}

		catch (JSchException e) {
			throw new SftpClientException(e);
		}
	}

	@Override
	public boolean isConnected() {
		return sftp != null && sftp.isConnected() && session != null && session.isConnected();
	}

	@Override
	public void disconnect() throws SftpClientException {

		if (sftp != null) {
			sftp.disconnect();
			sftp = null;
		} else {
			LOGGER.info("sftp == null (probably never connected)");
		}

		if (session != null) {
			session.disconnect();
			session = null;
		} else {
			LOGGER.info("session == null (probably never connected)");
		}
	}

	@Override
	public void upload(String srcFile, String dstFile, MODE mode) throws SftpClientException {
		try {
			sftp.put(srcFile, dstFile, null, getMode(mode));
		} catch (SftpException e) {
			throw new SftpClientException("failed to upload the file \"" + srcFile + "\" to file \"" + dstFile + "\" :: " + e.getMessage(), e);
		}
	}

	@Override
	public void upload(InputStream srcStream, String dstFile, MODE mode) throws SftpClientException {
		try {
			sftp.put(srcStream, dstFile, null, getMode(mode));
		} catch (SftpException e) {
			throw new SftpClientException("failed to upload the stream \"" + srcStream + "\" to file \"" + dstFile + "\" :: " + e.getMessage(), e);
		}
	}

	@Override
	public void download(String srcFile, String dstFile, MODE mode) throws SftpClientException {
		try {
			sftp.get(srcFile, dstFile, null, getMode(mode));
		} catch (SftpException e) {
			throw new SftpClientException("failed to download the file \"" + srcFile + "\" to file \"" + dstFile + "\" :: " + e.getMessage(), e);
		}
	}

	@Override
	public void download(String srcFile, OutputStream dstStream, MODE mode) throws SftpClientException {
		try {
			sftp.get(srcFile, dstStream, null, getMode(mode), 0);
		} catch (SftpException e) {
			throw new SftpClientException("failed to download the file \"" + srcFile + "\" to stream \"" + dstStream + "\" :: " + e.getMessage(), e);
		}
	}

	@Override
	public void deleteFile(String file) throws SftpClientException {
		try {
			sftp.rm(file);
		} catch (SftpException e) {
			throw new SftpClientException("failed to delete the file \"" + file + "\" :: " + e.getMessage(), e);
		}
	}

	@Override
	public void deleteDirectory(String directory) throws SftpClientException {
		try {
			sftp.rm(directory);
		} catch (SftpException e) {
			throw new SftpClientException("failed to delete the directory \"" + directory + "\" :: " + e.getMessage(), e);
		}
	}

	@Override
	public void createDirectory(String directory) throws SftpClientException {
		try {
			sftp.mkdir(directory);
		} catch (SftpException e) {
			throw new SftpClientException("failed to create the directory \"" + directory + "\" :: " + e.getMessage(), e);
		}
	}

	private int getMode(MODE mode) {

		switch (mode) {

			case OVERWRITE:
				return ChannelSftp.OVERWRITE;

			case RESUME:
				return ChannelSftp.RESUME;

			case APPEND:
				return ChannelSftp.APPEND;

			default:
				return ChannelSftp.OVERWRITE;
		}
	}

}