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

/**
 * Basic implementation for {@link FTPClientConnectionInfoProvider}. All the
 * info needed to create the {@link FTPClientConnectionInfo} object is received
 * through the constructor.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.1, July 25, 2014
 */
public class FTPClientConnectionInfoProviderConstructor implements FTPClientConnectionInfoProvider {

  private final FTPClientConnectionInfo connectionInfo;

  public FTPClientConnectionInfoProviderConstructor(final String host, final int port, final String username, final String password) {
    this.connectionInfo = new FTPClientConnectionInfo(host, port, username, password);
  }

  @Override
  public final FTPClientConnectionInfo getConnectionInfo() {
    return connectionInfo;
  }

}
