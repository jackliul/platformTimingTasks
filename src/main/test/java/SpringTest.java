
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jacliu.test.quartz.FaultJobsTasks;
import com.jacliu.test.redis.service.RedisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContextAaaBbb.xml" })
// @Transactional
public class SpringTest {

	@Autowired()
	@Qualifier("developOrTestRedisService")
	private RedisService developOrTestRedisService;

	@Autowired
	private FaultJobsTasks faultJobsTaks;

	@Test
	public void testService() {
		// 缓存获取的结果
		String key = "SESSION:GETTMSCOUNTRY_UPDATEMARK";
		String hashKey = "SESSION:DDS:GETTMSCOUNTRY_UPDATEMARK";
		// developOrTestRedisService.hput(key, hashKey, "1982-8-8");

		// companyCode:: DDS hostName:: www.i-oms.cn key::
		// SESSION:DDS:GETTMSCOUNTRY_UPDATEMARK hashKey::
		// SESSION:GETTMSCOUNTRY_UPDATEMARK

		// SESSION:DDS:FINDORDERBYTMS_UPDATEMARK hashKey::
		// SESSION:FINDORDERBYTMS_UPDATEMARK

		String string = developOrTestRedisService.hget(hashKey, key);
		System.out.println(string);
	}

	@Test
	public void aa() {
		System.out.println("xxx");

		String key = "UPDATE_AND_PUSH:" + "intelink02".toUpperCase();
		String hashKey = getInterfaceName("https://demo.i-oms.cn/oms-jobs/fetchClienteleData/1.0?companyNo=intelink02");
		System.out.println("222");

		System.out.println(key + " ,  " + hashKey);
		String string = developOrTestRedisService.hget(key, hashKey);
		System.out.println(string);

	}

	private String getInterfaceName(String taskUrl) {
		String[] split = taskUrl.split("//");
		String[] split2 = split[1].split("/");
		String interfaceName = split2[2];
		// return interfaceName.toUpperCase();
		return interfaceName;
	}

	@Test
	public void testTindAndUpdateFaultJobsRecord() {
		try {
			faultJobsTaks.findAndUpdateFaultJobsRecord();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
