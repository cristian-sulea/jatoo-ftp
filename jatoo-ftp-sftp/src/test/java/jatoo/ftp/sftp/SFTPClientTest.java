/*
 * Copyright (C) 2013 Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jatoo.ftp.sftp;

import jatoo.ftp.FTPClientException;

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for {@link SftpClient}.
 * 
 * @author Cristian Sulea ( http://cristian.sulea.net )
 * @version 1.0 November 11, 2013
 */
public class SFTPClientTest {

  private final SFTPClient sftpClient = new SFTPClient();

  private boolean connected = true;

  @Before
  public void initialize() throws Exception {

    try {
      sftpClient.connect("rdev00", 22, "dev8081", "dev8081");
    } catch (Exception e) {
      connected = false;
    }

    if (!connected) {
      return;
    }

    Assert.assertTrue(sftpClient.isConnected());
  }

  @After
  public void cleanup() throws Exception {

    if (!connected) {
      return;
    }

    Assert.assertTrue(sftpClient.isConnected());
    sftpClient.disconnect();
    Assert.assertFalse(sftpClient.isConnected());
  }

  @Test
  public void test() throws Exception {

    if (!connected) {
      return;
    }

    File file1 = new File(getClass().getResource(getClass().getSimpleName() + ".class").toURI());
    System.out.println(file1);
    File file2 = new File(file1.getName());

    file2.delete();

    Assert.assertFalse(file2.exists());

    sftpClient.upload(file1.getAbsolutePath(), file2.getName());
    sftpClient.download(file2.getName(), file2.getName());

    Assert.assertTrue(file2.exists());
    Assert.assertEquals(file2.length(), file1.length());

    file2.delete();
    Assert.assertFalse(file2.exists());

    sftpClient.deleteFile(file2.getName());

    try {
      sftpClient.download(file2.getName(), file2.getName());
      Assert.fail("we just deleted the file, download sould fail");
    } catch (FTPClientException e) {}
  }

}
