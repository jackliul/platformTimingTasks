package springTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jacliu.test.redis.service.RedisService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:applicationContextAaaBbb.xml" })
// @Transactional
public class SpringTest {

	@Autowired()
	@Qualifier("developOrTestRedisService")
	private RedisService developOrTestRedisService;

	@Test
	public void testService() {
		// 缓存获取的结果
		String key = "SESSION:" + "FWGJ" + ":FETCHCLIENTELEDATA_UPDATAMARK";
		developOrTestRedisService.hput(key, "SESSION:FETCHCLIENTELEDATA_UPDATAMARK", "1982-8-8");

		String string = developOrTestRedisService.hget(key, "SESSION:FETCHCLIENTELEDATA_UPDATAMARK");
		System.out.println(string);
	}
}
