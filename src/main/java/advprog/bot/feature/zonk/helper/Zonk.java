package advprog.bot.feature.zonk.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Zonk {

    public static ArrayList<String> questionList = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public static void buatSoal(String[] soal) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(new FileReader("./Question.json"));
            JSONArray arr = new JSONArray();
            String question =  soal[0];
            for (int i = 1; i < soal.length; i++) {
                String temp = soal[i];
                arr.add(temp);
            }
            questionList.add(question);
            obj.put(question,arr);
            FileWriter fw = new FileWriter("./Question.json");
            fw.write(obj.toString());
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("unchecked")
    public static String option(String soal) {
        String option = "";
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("./Question.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get(soal);
            int j = 1;
            for (int i = 0;i < arr.size() - 1;i++) {
                option += j + " " + arr.get(i).toString() + "\n";
                j++;
            }
            return option;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return option;

    }

    @SuppressWarnings("unchecked")
    public static void gantiSoal(String soal,String jawaban) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("./Question.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get(soal);
            ArrayList<String> temp = new ArrayList<>();
            String ans = arr.get(4).toString();
            for (int  i = 0;i < arr.size();i++) {
                temp.add(arr.get(i).toString());

            }
            arr.removeAll(temp);
            for (int i = 0;i < temp.size();i++) {
                if (temp.get(i).equals(ans)) {
                    temp.set(i,jawaban);
                }
                arr.add(temp.get(i));
            }
            jsonObject.remove(soal);
            jsonObject.put(soal,arr);
            FileWriter fw = new FileWriter("./Question.json");
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

    @SuppressWarnings("unchecked")
    public static String jawab(int input, String soal) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader("./Question.json"));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObject.get(soal);
            String jawaban = arr.get(4).toString();
            String masukan = arr.get(input).toString();
            if (jawaban.equals(masukan)) {
                return "benar";
            } else {
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


    public Zonk(String input) {
        String masukan = input;
        questionList.add("deandrophobia fear of what ?");
        questionList.add("what is the dog name?");
        questionList.add("capital city of indonesia?");
        questionList.add("mc donald phone number ?");
        questionList.add("brand of phone ?");
        questionList.add("where is fasilkom ?");

    }

    public ArrayList<String> randomQuestion(ArrayList<String> e) {
        Collections.shuffle(e);
        return e;

    }

    public ArrayList<String> getQuestionList() {
        return questionList;
    }




}
