//
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
//
// import org.junit.Test;
//
// import com.alibaba.fastjson.JSON;
// import com.jacliu.test.vo.ScheduleJobVo;
//
// import cn.hutool.core.bean.BeanUtil;
// import cn.hutool.http.HttpUtil;
// import cn.hutool.json.JSONUtil;
//
// public class HttpTest {
//
// @Test
// public void bbb() {
//
// Map<String, Object> reqParam = new HashMap<String, Object>();
// reqParam.put("", "");
// String res =
// HttpUtil.post("http://localhost:8080/platformTimingTasks/schedule/addOrUpdate.shtml",
// reqParam);
//
// // String jsonStr = JSONUtil.toJsonStr(scheduleJobVo);
// // System.out.println("jsonStr ,,, " + jsonStr);
// }
//
// @Test
// public void aaa() {
// ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
// scheduleJobVo.setCompanyCode("FDE");
// scheduleJobVo.setFunctionalName("站点资料");
// scheduleJobVo.setCronExpression("*/20 * * * * ?");
// scheduleJobVo.setTaskUrl("http://192.168.8.33:8081/oms-web/batchCarrierSite/1.0?companyNo=FDE");
// scheduleJobVo.setCreateUser("platform");
// scheduleJobVo.setFunctionalType("1");
// // scheduleJobVo.setGmtCreate(new Date());
// scheduleJobVo.setIsSync(true);
//
// String jsonStr = JSONUtil.toJsonStr(scheduleJobVo);
// System.out.println("jsonStr ,,, " + jsonStr);
// }
//
// @Test
// public void testAdd() {
// ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
// scheduleJobVo.setCompanyCode("FDE");
// scheduleJobVo.setFunctionalName("站点资料11");
// scheduleJobVo.setCronExpression("*/15 * * * * ?");
// // http://192.168.8.33:8081/oms-web/batchCarrierSite/1.0?companyNo=FDE
// scheduleJobVo.setTaskUrl("https://www.baidu.com/");
// scheduleJobVo.setCreateUser("platform");
// scheduleJobVo.setFunctionalType("1");
// // scheduleJobVo.setGmtCreate(new Date());
// scheduleJobVo.setIsSync(true);
// Map<String, Object> reqParam = BeanUtil.beanToMap(scheduleJobVo);
// String res =
// HttpUtil.post("http://localhost:8080/platformTimingTasks/schedule/addOrUpdate.shtml",
// reqParam);
// System.out.println(res);
// }
//
// @Test
// public void testUpdate() {
// ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
// scheduleJobVo.setScheduleJobId(10L);
// scheduleJobVo.setCronExpression("*/30 * * * * ?");
// scheduleJobVo.setModifiedUser("fardar");
// scheduleJobVo.setIsActived(0);
// Map<String, Object> reqParam = BeanUtil.beanToMap(scheduleJobVo);
// String res =
// HttpUtil.post("http://localhost:8080/platformTimingTasks/schedule/addOrUpdate.shtml",
// reqParam);
// System.out.println(res);
// }
//
// @Test
// public void testList() {
// ScheduleJobVo scheduleJobVo = new ScheduleJobVo();
// scheduleJobVo.setCompanyCode("FDE");
// scheduleJobVo.setFunctionalName("站点资料");
// Map<String, Object> reqParam = BeanUtil.beanToMap(scheduleJobVo);
// String res =
// HttpUtil.post("http://localhost:8080/platformTimingTasks/schedule/list.shtml",
// reqParam);
// System.out.println(res);
// List<ScheduleJobVo> scheduleJobVos = JSON.parseArray(res,
// ScheduleJobVo.class);
// System.out.println();
// for (ScheduleJobVo jobVo : scheduleJobVos) {
// System.out.println(jobVo.getScheduleJobId() + ",,," + jobVo.getJobGroup() +
// ",,," + jobVo.getJobName());
// }
//
// }
// }
