/**
 * @author CHCC
 * @FileName Test
 * @Date 2020/10/19 1:44 下午
 * @Version 1.0
 * @Description TODO
 */
public class Test {

    public static void main(String[] args){
        String s1 = new String("abc");
        String s2 = "abc";
        String s3 = new String("abc");

        String a1 = "java";
        String a2 = "ja" + "va";

        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s2 == s3);
        System.out.println(a1 == a2);
        System.out.println(a1.equals(a2));
    }
}
