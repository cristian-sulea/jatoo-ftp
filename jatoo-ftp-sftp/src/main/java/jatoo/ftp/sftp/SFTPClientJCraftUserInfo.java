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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.UserInfo;

/**
 * Simple implementation for {@link UserInfo} to be used in {@link SFTPClient}.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.1, July 25, 2014
 */
public class SFTPClientJCraftUserInfo implements UserInfo {

  /** The logger. */
  private final Log logger = LogFactory.getLog(SFTPClientJCraftUserInfo.class);

  /** The password. */
  private String password;

  public SFTPClientJCraftUserInfo(final String password) {
    this.password = password;
  }

  @Override
  public final boolean promptPassword(final String message) {
    logger.info(message);
    logger.info("No need to prompt for password, the password was passed through constructor.");
    return true;
  }

  @Override
  public final String getPassword() {
    return password;
  }

  @Override
  public final boolean promptYesNo(final String message) {
    logger.info(message);
    logger.info("Auto accepting the fingerprint, because I was told to do so.");
    return true;
  }

  @Override
  public final void showMessage(final String message) {
    logger.info(message);
  }

  @Override
  public final boolean promptPassphrase(final String message) {
    logger.info(message);
    logger.info("No need to prompt for passphrase, the method #getPassphrase() will always return null.");
    return true;
  }

  @Override
  public final String getPassphrase() {
    return null;
  }

}
