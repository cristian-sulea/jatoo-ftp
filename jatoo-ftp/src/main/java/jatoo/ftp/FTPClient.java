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
 * {@link FTPClient} defines the functionality necessary to store and retrieve
 * files from an FTP server.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, July 25, 2014
 */
public interface FTPClient {

  enum MODE {
    OVERWRITE,
    RESUME,
    APPEND
  };

  void connect(String host, int port, String username, String password) throws FTPClientException;

  void connect(FTPClientConnectionInfoProvider connectionInfoProvider) throws FTPClientException;

  void disconnect() throws FTPClientException;

  boolean isConnected();

  void upload(File srcFile, String dstFile) throws FTPClientException;

  void upload(File srcFile, String dstFile, MODE mode) throws FTPClientException;

  void upload(String srcFile, String dstFile) throws FTPClientException;

  void upload(String srcFile, String dstFile, MODE mode) throws FTPClientException;

  void upload(InputStream srcStream, String dstFile) throws FTPClientException;

  void upload(InputStream srcStream, String dstFile, MODE mode) throws FTPClientException;

  void download(String srcFile, File dstFile) throws FTPClientException;

  void download(String srcFile, File dstFile, MODE mode) throws FTPClientException;

  void download(String srcFile, String dstFile) throws FTPClientException;

  void download(String srcFile, String dstFile, MODE mode) throws FTPClientException;

  void download(String srcFile, OutputStream dstStream) throws FTPClientException;

  void download(String srcFile, OutputStream dstStream, MODE mode) throws FTPClientException;

  void deleteFile(String file) throws FTPClientException;

  void deleteDirectory(String directory) throws FTPClientException;

  void createDirectory(String directory) throws FTPClientException;

}
