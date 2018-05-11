package advprog.BillBoard.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BillBoardOperation {
    private ArrayList<String>  top100;

    public BillBoardOperation(){
        top100 = new ArrayList<>();
    }

    public ArrayList<String> top100Artist() throws IOException {
        Document dc = Jsoup.connect("https://www.billboard.com/charts/japan-hot-100").get();
        ArrayList<String> test = new ArrayList<>();
        return test;
    }


}
