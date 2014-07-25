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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.UserInfo;

/**
 * Simple implementation for {@link UserInfo}.
 * 
 * @author Cristian Sulea ( http://cristian.sulea.net )
 * @version 1.0, November 6, 2013
 */
public class SftpClientJCraftUserInfo implements UserInfo {

	private static final Log LOGGER = LogFactory.getLog(SftpClientJCraftUserInfo.class);

	private String password;

	public SftpClientJCraftUserInfo(String password) {
		this.password = password;
	}

	@Override
	public boolean promptPassword(String message) {
		LOGGER.info(message);
		LOGGER.info("No need to prompt for password, the password was passed through constructor.");
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean promptYesNo(String message) {
		LOGGER.info(message);
		LOGGER.info("Auto accepting the fingerprint, because I was told to do so.");
		return true;
	}

	@Override
	public void showMessage(String message) {
		LOGGER.info(message);
	}

	@Override
	public boolean promptPassphrase(String message) {
		LOGGER.info(message);
		LOGGER.info("No need to prompt for passphrase, the method #getPassphrase() will always return null.");
		return true;
	}

	@Override
	public String getPassphrase() {
		return null;
	}

}
