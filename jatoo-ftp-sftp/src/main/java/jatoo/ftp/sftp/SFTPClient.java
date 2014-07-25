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

package jatoo.ftp.sftp;

import jatoo.ftp.AbstractFTPClient;
import jatoo.ftp.FTPClientException;

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
 * SFTP implementation for JaToo FTP library.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.2, July 25, 2014
 */
public class SFTPClient extends AbstractFTPClient {

  private static final Log LOGGER = LogFactory.getLog(SFTPClient.class);

  private Session session;
  private ChannelSftp sftp;

  @Override
  protected final void connectImpl(final String host, final int port, final String username, final String password) throws FTPClientException {

    JSch jsch = new JSch();

    //
    // connect

    try {
      session = jsch.getSession(username, host, port);
      session.setUserInfo(new SFTPClientJCraftUserInfo(password));
      session.connect();
    }

    catch (JSchException e) {
      throw new SFTPClientExceptionBadConnectionInfo(e);
    }

    //
    // open SFTP connection

    try {

      Channel c = session.openChannel("sftp");
      c.connect();

      sftp = (ChannelSftp) c;
    }

    catch (JSchException e) {
      throw new FTPClientException(e);
    }
  }

  @Override
  public final boolean isConnected() {
    return sftp != null && sftp.isConnected() && session != null && session.isConnected();
  }

  @Override
  public final void disconnect() throws FTPClientException {

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
  public final void upload(final String srcFile, final String dstFile, final MODE mode) throws FTPClientException {
    try {
      sftp.put(srcFile, dstFile, null, getMode(mode));
    } catch (SftpException e) {
      throw new FTPClientException("failed to upload the file \"" + srcFile + "\" to file \"" + dstFile + "\" :: " + e.getMessage(), e);
    }
  }

  @Override
  public final void upload(final InputStream srcStream, final String dstFile, final MODE mode) throws FTPClientException {
    try {
      sftp.put(srcStream, dstFile, null, getMode(mode));
    } catch (SftpException e) {
      throw new FTPClientException("failed to upload the stream \"" + srcStream + "\" to file \"" + dstFile + "\" :: " + e.getMessage(), e);
    }
  }

  @Override
  public final void download(final String srcFile, final String dstFile, final MODE mode) throws FTPClientException {
    try {
      sftp.get(srcFile, dstFile, null, getMode(mode));
    } catch (SftpException e) {
      throw new FTPClientException("failed to download the file \"" + srcFile + "\" to file \"" + dstFile + "\" :: " + e.getMessage(), e);
    }
  }

  @Override
  public final void download(final String srcFile, final OutputStream dstStream, final MODE mode) throws FTPClientException {
    try {
      sftp.get(srcFile, dstStream, null, getMode(mode), 0);
    } catch (SftpException e) {
      throw new FTPClientException("failed to download the file \"" + srcFile + "\" to stream \"" + dstStream + "\" :: " + e.getMessage(), e);
    }
  }

  @Override
  public final void deleteFile(final String file) throws FTPClientException {
    try {
      sftp.rm(file);
    } catch (SftpException e) {
      if (e.getMessage().contains("No such file")) {
        LOGGER.info("No such file");
      } else {
        throw new FTPClientException("failed to delete the file \"" + file + "\" :: " + e.getMessage(), e);
      }
    }
  }

  @Override
  public final void deleteDirectory(final String directory) throws FTPClientException {
    try {
      sftp.rm(directory);
    } catch (SftpException e) {
      throw new FTPClientException("failed to delete the directory \"" + directory + "\" :: " + e.getMessage(), e);
    }
  }

  @Override
  public final void createDirectory(final String directory) throws FTPClientException {
    try {
      sftp.mkdir(directory);
    } catch (SftpException e) {
      throw new FTPClientException("failed to create the directory \"" + directory + "\" :: " + e.getMessage(), e);
    }
  }

  private int getMode(final MODE mode) {

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
