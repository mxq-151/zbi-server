package org.zbi.server.core.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zbi.server.dao.service.FolderDaoService;
import org.zbi.server.model.exception.AdminException;
import org.zbi.server.model.facade.FacadeFolder;
import org.zbi.server.rest.ZBiApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZBiApp.class)
public class UserMockActionTest extends ServiceTestBase {

	@Autowired
	private FolderDaoService folderDaoService;

	@Test
	public void createFolder() throws AdminException {
		this.developerLogin();
		FacadeFolder folder = this.folderDaoService.createFolder("测试文件夹", "test", 1);
		Assert.assertTrue(this.folderDaoService.getQueryFolders().size() == 1);
	}

}
