import net.intelink.oms.core.dto.order.Remote.InsertCustomsParam;
import net.intelink.oms.core.util.ObjectUtil;
import net.intelink.openapi.client.kit.JsonKit;
import net.intelink.openapi.client.sdk.IRemoteTestService;
import net.intelink.openapi.client.sdk.JsonClientSdk;
import net.intelink.zmframework.util.ImagesUtil;
import net.intelink.zmframework.util.PDFUtil;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {

    private static JsonClientSdk<IRemoteTestService> sdk;


    public static void testJsonClientSdk() {
        InsertCustomsParam insertCustomsParam = new InsertCustomsParam();
        ObjectUtil.setDefaultValue(insertCustomsParam);
        System.out.println(insertCustomsParam.getCargoNameCn());
    }


    public static void main(String[] args) throws Exception {

        testJsonClientSdk();
    }
}
