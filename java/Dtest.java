
public class Dtest {
	public static void main(String[] args) {
		/*String reg="^\\d+$";
		String integer="3";
		if(integer.matches(reg)) {
			System.out.println("是");
		}else {
			System.out.println("不是");
		}*/
		/*String s="92222.2364";
		  String regex="^\\d+(\\.\\d{1,3})?$";
		  if(s.matches(regex)==true){
		   System.out.println("匹配");
		  }else{
		   System.out.println("不匹配");
		  }*/
		String name="CN-中国";
		String str[]=name.split("-");
		String a=null;
		for (int i = 0; i < str.length; i++) {
			a=str[i];
		}
		System.out.println(a);
		
	}
}
