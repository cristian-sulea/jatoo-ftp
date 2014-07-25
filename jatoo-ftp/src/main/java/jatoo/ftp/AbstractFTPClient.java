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

package jatoo.ftp;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class provides a skeletal implementation of the {@link FTPClient}
 * interface.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.2, July 25, 2014
 */
public abstract class AbstractFTPClient implements FTPClient {

  @Override
  public final void connect(final String host, final int port, final String username, final String password) throws FTPClientException {

    if (isConnected()) {
      disconnect();
    }

    connectImpl(host, port, username, password);
  }

  protected abstract void connectImpl(String host, int port, String username, String password) throws FTPClientException;

  @Override
  public final void connect(final FTPClientConnectionInfoProvider connectionInfoProvider) throws FTPClientException {

    final FTPClientConnectionInfo connectionInfo = connectionInfoProvider.getConnectionInfo();

    connect(connectionInfo.getHost(), connectionInfo.getPort(), connectionInfo.getUsername(), connectionInfo.getPassword());
  }

  @Override
  public final void upload(final File srcFile, final String dstFile) throws FTPClientException {
    upload(srcFile.getAbsolutePath(), dstFile, MODE.OVERWRITE);
  }

  @Override
  public final void upload(final File srcFile, final String dstFile, final MODE mode) throws FTPClientException {
    upload(srcFile.getAbsolutePath(), dstFile, mode);
  }

  @Override
  public final void upload(final String srcFile, final String dstFile) throws FTPClientException {
    upload(srcFile, dstFile, MODE.OVERWRITE);
  }

  @Override
  public final void upload(final InputStream srcStream, final String dstFile) throws FTPClientException {
    upload(srcStream, dstFile, MODE.OVERWRITE);
  }

  @Override
  public final void download(final String srcFile, final File dstFile) throws FTPClientException {
    download(srcFile, dstFile.getAbsolutePath(), MODE.OVERWRITE);
  }

  @Override
  public final void download(final String srcFile, final File dstFile, final MODE mode) throws FTPClientException {
    download(srcFile, dstFile.getAbsolutePath(), mode);
  }

  @Override
  public final void download(final String srcFile, final String dstFile) throws FTPClientException {
    download(srcFile, dstFile, MODE.OVERWRITE);
  }

  @Override
  public final void download(final String srcFile, final OutputStream dstStream) throws FTPClientException {
    download(srcFile, dstStream, MODE.OVERWRITE);
  }

}
