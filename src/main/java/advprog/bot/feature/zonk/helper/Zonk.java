package advprog.bot.feature.zonk.helper;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;





public class Zonk {

    public static void buatSoal(String[] soal) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(new FileReader("C:\\Users\\tirta\\Desktop\\AP\\B3\\Question.json"));
            JSONArray arr = new JSONArray();
            String question =  soal[0];
            for (int i =1; i<soal.length; i++){
                arr.add(soal[i]);
            }
            obj.put(question,arr);
            FileWriter fw = new FileWriter("C:\\Users\\tirta\\Desktop\\AP\\B3\\Question.json");
            fw.write(obj.toString());
            fw.close();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


    }
    public static String option (String soal){
        String option = "";
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:\\Users\\tirta\\Desktop\\AP\\B3\\Question.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get(soal);
            for (int i=0;i<arr.size();i++){
                option += arr.get(i).toString();
            }
            return option;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return option;

    }


    public static void gantiSoal(String soal,String jawaban){
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("C:\\Users\\tirta\\Desktop\\AP\\B3\\Question.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get(soal);
            ArrayList<String> temp = new ArrayList<>();
            String ans = arr.get(4).toString();
            for (int i=0;i<arr.size();i++){
                temp.add(arr.get(i).toString());

            }
            arr.removeAll(temp);
            for (int i=0;i<temp.size();i++){
                if (temp.get(i).equals(ans)){
                    temp.set(i,jawaban);
                }
                arr.add(temp.get(i));
            }
            jsonObject.remove(soal);
            jsonObject.put(soal,arr);
            FileWriter fw = new FileWriter("C:\\Users\\tirta\\Desktop\\AP\\B3\\Question.json");
            fw.write(jsonObject.toString());
            fw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    public static String jawab(int input, String soal){
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("C:\\Users\\tirta\\Desktop\\AP\\B3\\Question.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get(soal);
            String jawaban = arr.get(4).toString();
            String masukan = arr.get(input).toString();
            if (jawaban.equals(masukan)){
                return "benar";
            }
            else {
                return "salah";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "lol";

    }
    public static void main (String[] args){
        String[] soal = {"who are","tony stark","budi","i dont know","hulk","tony stark"};
        gantiSoal("who are","boy");
        System.out.println("dor");

    }


    public Zonk(String input){
        String masukan = input;

    }


}
