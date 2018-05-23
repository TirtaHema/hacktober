package advprog.bot.feature.zonk;

import advprog.bot.feature.zonk.helper.Zonk;
import advprog.bot.line.AbstractLineChatHandlerDecorator;
import advprog.bot.line.LineChatHandler;

import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;


import java.util.*;
import java.util.logging.Logger;


public class ZonkChatHandler extends AbstractLineChatHandlerDecorator {
    private static final Logger LOGGER = Logger.getLogger(ZonkChatHandler.class.getName());
    private static  int counter = 0;
    private static  boolean inGame = false;
    private static SortedMap<String, Integer> scoreBoard = new TreeMap<>();
    private static boolean question = false;
    private static boolean answer = false;
    private static boolean chooseAnswer = false;
    private static boolean gantiJawaban = false;
    private static int option;
    private static String soal;
    private static ArrayList<String> listQuestion = new ArrayList<>();
    private static Zonk zonk = new Zonk("hahaha");



    public ZonkChatHandler(LineChatHandler decoratedHandler) {
        this.decoratedLineChatHandler = decoratedHandler;
        LOGGER.info("Zonk chat handler added!");
    }

    @Override
    protected boolean canHandleTextMessage(MessageEvent<TextMessageContent> event) {
        return event.getMessage().getText().split(" ")[0].equals("/echo");
    }

    @Override
    protected List<Message> handleTextMessage(MessageEvent<TextMessageContent> event) {
        if(event.getSource() instanceof UserSource) {

            if(event.getMessage().toString().equals("/add_question")) {
                question = true;
                return Collections.singletonList(
                        new TextMessage("apa pertanyaan kamu ? (pastikan chat selanjutnya mengandung tanda tanya)")
                );
            }
            else if(question) {
                if (event.getMessage().getText().endsWith("?")){
                    answer = true;
                    question = false;
                    return Collections.singletonList(
                            new TextMessage("pertanyaan kamu adalah "+event.getMessage().getText()+" sekarang masukan optionya, 4 option di spacebreak ya :)")
                    );
                }
                else {
                    return Collections.singletonList(
                            new TextMessage("formatnya salah sekarang masukin lagi ya jangan lupa pake tanda tanya")
                    );
                }

            }
            if (answer){
                String[] temp = event.getMessage().getText().toString().split("/n");
                if (temp.length == 4) {
                    for (int i = 0; i < temp.length; i++) {
                        listQuestion.add(temp[i]);
                    }
                    CarouselTemplate carouselTemplate = new CarouselTemplate(
                            Arrays.asList(new CarouselColumn("https://www.publicdomainpictures.net/pictures/60000/velka/question-mark-1376773633jUs.jpg","Option 1", temp[0],
                                            Collections.singletonList(new PostbackAction("Pilih", "0"))),
                                    new CarouselColumn("https://www.publicdomainpictures.net/pictures/60000/velka/question-mark-1376773633jUs.jpg","Option 1", temp[1],
                                            Collections.singletonList(new PostbackAction("Pilih", "1"))),
                                    new CarouselColumn("https://www.publicdomainpictures.net/pictures/60000/velka/question-mark-1376773633jUs.jpg","Option 1", temp[2],
                                            Collections.singletonList(new PostbackAction("Pilih", "2"))),
                                    new CarouselColumn("https://www.publicdomainpictures.net/pictures/60000/velka/question-mark-1376773633jUs.jpg","Option 1", temp[3],
                                            Collections.singletonList(new PostbackAction("Pilih", "3")))
                            )
                    );
                    chooseAnswer = true;
                    TemplateMessage templateMessage = new TemplateMessage("Pilih jawaban yang benar", carouselTemplate);
                    return Collections.singletonList(
                            templateMessage
                    );

                }
                else {
                    return Collections.singletonList(
                            new TextMessage("optionnya kurang atau kelebihan, masukan empat option saja")
                    );

                }
            }

            else if (chooseAnswer){
                chooseAnswer = false;
                return Collections.singletonList(
                        new TextMessage("jawaban anda berhasil disimpan !")
                );
            }

            else if  (event.getMessage().toString().equals("/ganti jawaban")) {
                String temp = "";
                int j = 1;
                for  (int i = 0 ; i < zonk.questionList.size() ; i++ ){
                    temp += j + zonk.questionList.get(i) + "/n";
                    j++;
                }
                gantiJawaban = true;
                return Collections.singletonList(
                        new TextMessage("ketik salah satu nomor diatas " + "/n" + temp)
                );
            }
            else if (gantiJawaban) {
                try {
                    int i = Integer.parseInt(event.getMessage().toString());
                    soal = zonk.questionList.get(i-1);
                    if (i-1 < zonk.questionList.size() ){
                        chooseAnswer = true;
                        return Collections.singletonList(
                                new TextMessage("silahkan masukan jawaban baru")
                        );
                    }
                    else {
                        return Collections.singletonList(
                                new TextMessage("ketik nya nomor yang ada aja ya")
                        );
                    }
                } catch (NumberFormatException nfe){
                    return Collections.singletonList(
                            new TextMessage("harus berupa angka bos")
                    );
                }
            }

            else if (gantiJawaban && chooseAnswer){
                zonk.gantiSoal(soal,event.getMessage().toString());
                return Collections.singletonList(
                        new TextMessage("jawabannya sudah diganti ya...")
                );
            }

            else {
                return Collections.singletonList(
                        new TextMessage("command salah :(")
                );
            }



        }
        else {
            if (event.getMessage().toString().equals("/start_zonk")){
                inGame = true;
                return Collections.singletonList(
                        new TextMessage("permainan dimulai ketik /stop_zonk untuk mengakhiri")
                );

            }
            else if (inGame){
                zonk.randomQuestion(zonk.getQuestionList());
                soal = zonk.getQuestionList().get(0);
                String option = zonk.option(soal);
                return Collections.singletonList(
                        new TextMessage("ketiklah angka jawabannya /n"+ option)
                );

            }
            else if (inGame && counter<3){
                try{
                    int pilihan = Integer.parseInt(event.getMessage().toString())-1;
                    String hasil = zonk.jawab(pilihan,soal);
                    if (hasil.equals("benar")) {
                        String nama = event.getSource().getSenderId().toString();
                        scoreBoard.put(nama,1);
                    }
                    else {
                        counter++;
                        return Collections.singletonList(
                                new TextMessage("jawbaan salah !")
                        );
                    }
                }catch (NumberFormatException e){
                    return Collections.singletonList(
                            new TextMessage("jawbaan harus dalam format angka")
                    );
                }
            }
            else if (inGame && counter==3){
                zonk.randomQuestion(zonk.getQuestionList());
                soal = zonk.getQuestionList().get(0);
                String option = zonk.option(soal);
                counter =0;
                return Collections.singletonList(
                        new TextMessage("kesempatan habis, soal selanjutnya ketiklah angka jawabannya /n"+ option)
                );
            }
            else if (event.getMessage().toString().equals("/stop_zonk")){
                String tempor ="";
                for (String nama : scoreBoard.keySet()){
                    tempor += nama + " " + scoreBoard.get(nama) + "/n";
                }
                return Collections.singletonList(
                        new TextMessage("game selesai hasilnya adalah /n"+tempor)
                );
            }
            else {
                return Collections.singletonList(
                        new TextMessage("mulai game dengan mengetik /start_zonk")
                );
            }
        }
        return Collections.singletonList(
                new TextMessage("halo")
        );

    }

    /**
     *check apakah start zonk, inGame = true; , masukin semua id sender ke sortedMap
     * while inGame = true, increment counter sampai 3
     * kalau sudah sampai 3 kasih soal baru
     * kalau ada jawabannya yang bener kasih soal baru, tambahin nilai di score board
     * kalau stop zonk keluarin hasil di sorted map
     */
    @Override
    public boolean canHandleImageMessage(MessageEvent<ImageMessageContent> event) {
        return false;
    }

    @Override
    public boolean canHandleAudioMessage(MessageEvent<AudioMessageContent> event) {
        return false;
    }

    @Override
    public boolean canHandleStickerMessage(MessageEvent<StickerMessageContent> event) {
        return false;
    }

    @Override
    public boolean canHandleLocationMessage(MessageEvent<LocationMessageContent> event) {
        return false;
    }

}
