package springTest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import net.intelink.oms.core.domain.company.TmsMessageInfo;
import net.intelink.oms.core.dto.task.TaskResult;
import net.intelink.oms.service.bill.IUpdateBillService;
import net.intelink.oms.service.subject.ISubjectService;
import net.intelink.oms.service.tmsMessageInfo.ITmsMessageInfo;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring-private-context.xml"})  
public class SpringTest {
	
	 @Autowired 
     private  ITmsMessageInfo tmsMessageInfo; 
	 @Autowired
	 private ISubjectService iSubject;
	 @Test
	 public  void test() throws ParseException {
//		 Map<String, Object> paramMap = new HashMap<>();
//		    paramMap.put("companyCode", "JHD");
//		    paramMap.put("serviceType", 0);
////		    TmsMessageInfo messageInfo = tmsMessageInfo.find(paramMap);
//		   boolean  bill=iSubject.getSubjectByLastUpdateTime(paramMap);
////		    System.out.println(messageInfo.getCompanyCode() + " ,,,,, " + messageInfo.getTmsUrl());   
//		    System.out.println(bill);
		    
		    
		    
     }
    }
