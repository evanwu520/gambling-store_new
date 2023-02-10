import com.alibaba.fastjson.JSON;
import com.ampletec.gambling.report.entity.Wager;

public class main {

    public  static void main(String[]args) {


        String s = "{\"systemid\":1,\"userstatus\":0,\"betpoint\":\"BANKER\",\"type\":11,\"gamecode\":\"G-F1FDBDA1TFGF\",\"userid\":324135,\"parentid\":323795,\"commamount\":50.0,\"gameroundid\":204907097,\"currency\":203,\"id\":876763,\"state\":1,\"bettype\":0,\"settledatetime\":1675767502,\"createtime\":1675767461,\"loginname\":\"DNova88_127592\",\"betodds\":1.95,\"gametitle\":\"Baccarat EU01\",\"gameshape\":1,\"gameresult\":\"21;26;47;13;0;30\",\"winlost\":47.5,\"ploginname\":\"DNova88_127592\",\"gametype\":4,\"updatetime\":0,\"betamount\":50.0}";


        Wager w = JSON.parseObject(s.toString(), Wager.class);


        System.out.println(w);


        String c = JSON.toJSONString(w);

        System.out.println(c);





    }
}
