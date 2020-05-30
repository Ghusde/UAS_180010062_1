package com.bc181.jaya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "iHeartMusic";
    private final static String TABLE_BERITA = "t_berita";
    private final static String KEY_ID_BERITA = "ID_Berita";
    private final static String KEY_JUDUL = "Judul";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_CAPTION = "Caption";
    private final static String KEY_PENULIS = "Penulis";
    private final static String KEY_ISI_BERITA = "Isi_Berita";
    private final static String KEY_LINK = "Link";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx) {

        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_BERITA = " CREATE TABLE " + TABLE_BERITA
                + "(" + KEY_ID_BERITA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_CAPTION + " TEXT, "
                + KEY_PENULIS + " TEXT, " + KEY_ISI_BERITA + " TEXT, "
                + KEY_LINK + " TEXT);";

        db.execSQL(CREATE_TABLE_BERITA);
        inisialisasiBeritaAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_BERITA;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahBerita(Berita dataBerita) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());

        db.insert(TABLE_BERITA, null, cv);
        db.close();
    }

    public void tambahBerita(Berita dataBerita, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());

        db.insert(TABLE_BERITA, null, cv);
    }

    public void editBerita(Berita dataBerita) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataBerita.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataBerita.getTanggal()));
        cv.put(KEY_GAMBAR, dataBerita.getGambar());
        cv.put(KEY_CAPTION, dataBerita.getCaption());
        cv.put(KEY_PENULIS, dataBerita.getPenulis());
        cv.put(KEY_ISI_BERITA, dataBerita.getIsiBerita());
        cv.put(KEY_LINK, dataBerita.getLink());

        db.update(TABLE_BERITA, cv, KEY_ID_BERITA + "=?", new String[]{String.valueOf(dataBerita.getIdBerita())});

        db.close();
    }

    public void hapusBerita(int idBerita) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BERITA, KEY_ID_BERITA + "=?", new String[]{String.valueOf(idBerita)});
        db.close();
    }

    public ArrayList<Berita> getAllBerita() {
        ArrayList<Berita> dataBerita = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_BERITA;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do{
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString( 2));
                } catch (ParseException er) {
                    er.printStackTrace();
                }

                Berita tempBerita = new Berita(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataBerita.add(tempBerita);
            } while (csr.moveToNext());
        }

        return dataBerita;
    }

    private String storeImageFile(int id) {
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private  void inisialisasiBeritaAwal(SQLiteDatabase db){
        int idBerita = 0;
        Date tempDate = new Date();

        //menambah data berita 1
        try{
            tempDate = sdFormat.parse("22/05/2020 14:39");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Berita berita1 = new Berita(
                idBerita,
                "Matt. Shadows Avenged Sevenvold ",
                tempDate,
                storeImageFile(R.drawable.berita1),
                "Matt Shadows. (live concert 2015 Florida ~ Miami)",
                "Ricard Federic",
                "Matthew Charles Sanders (born July 31, 1981), known as M. Shadows, is an American singer, songwriter, and musician. He is best known as the lead vocalist, songwriter, and a founding member of the American heavy metal band Avenged Sevenfold.\n" +
                        "\n" +
                        "California and raised in Huntington Beach, California. His interest in rock was from listening to bands like Guns N' Roses earlier in life after his father gave him his first cassette and his interest in heavy metal.\n" +
                        "\n" +
                        "Shadows, like the other members of Avenged Sevenfold, uses a stage name. In an interview, Shadows says that he chose his stage name because he thought of himself as the darker character in the group.\n" +
                        "\n" +
                        "Matt Shadows, popularly known by his stage name M. Shadows, is an American singer,musician, and songwriter. He is the lead vocalist of the band Avenged Sevenfold, which he had \n" +
                        "\n" +
                        "formed along with his middle school friends. The American heavy metal band tries to infuse classic metal sound into their music, as the members admire the traditional classic sound that\n" +
                        "\n" +
                        "bands like Metallica and Megadeth had introduced. Voted third in the list of Top 25 Greatest Modern Frontmen by Ultimate Guitar, Matt’s singing and performing style is heavily drawn from \n" +
                        "\n" +
                        "classic metal bands. Guns N' Roses has been his all time favorite. He readily admits that Metallica, Megadeth, Slayer, Iron Maiden, Pantera, Ozzy Osbourne, and some others have influenced his style. \n" +
                        "\n" +
                        "Till date, Avenged Sevenfold has released seven studio albums, 24 singles, two compilation albums,\n" +
                        " and one live album. They have sold over 8 million albums worldwide. The band was ranked No. 47 on \n" +
                "\n" +
                        "Loudwire's list of Top 50 Metal Bands of All Time. Shadows had a fierce baritone voice and he was able to perfect it to the point where he could sing comfortably and convincingly in the tenor range needed. \n" +
                "\n" +
                        "By heavy metal music. Vocal Shadows continues to evolve over time, on every Avenged Sevenfold album release, M. Shadows was one of the first steps which led to Avenged Sevenfold self-producing their 2007 self-titled album.\n",
                "https://www.thefamouspeople.com/profiles/m-shadows-13034.php"
        );

        tambahBerita(berita1, db);
        idBerita++;

        //data berita 2
        try{
            tempDate = sdFormat.parse("14/05/2020 09:34");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Berita berita2 = new Berita(
                idBerita,
                "Takahiro Moriuchi One Ok Rock Biography",
                tempDate,
                storeImageFile(R.drawable.berita2),
                "Taka Moriuchi (live concert 2017 Yokohama Stadium ~ Japan)",
                "Hisharomito fuji",
                "Takahiro Moriuchi (Japanese: 森内 貴寛, Hepburn: Moriuchi Takahiro, born April 17, 1988, in Tokyo), known professionally as Taka, is a Japanese musician who is the lead vocalist of the Japanese rock band One Ok Rock.\n" +
                        "\n" +
                        "Moriuchi is the main lyricist and composer of his band. In 2017, Kerrang! magazine placed him at number 27 on their list of the \"50 Greatest Rockstars in the World\". He was also listed by Rock Sound magazine as one of \"50 Most Influential Figures in Rock\".\n" +
                        "\n" +
                        "Moriuchi was born on April 17, 1988, the eldest son of famous Japanese singers Masako Mori and Shinichi Mori. He has two younger brothers named Tomohiro Moriuchi who works on TV Tokyo, and Hiroki Moriuchi who is the lead singer of the band MY FIRST STORY.\n" +
                        "\n" +
                        "He attended Keio Elementary School and graduated in March 2001. Then he began attending Keio Middle School in April 2001 and continued his studies at Keio High School in April 2004. In his first year of high school (March 2005), he dropped out to pursue his musical career..\n" +
                        "\n" +
                        "In 2005, he changed his last name from Moriuchi (森内) to Morita (森田) due to the divorce of his parents. However, in the June 2012 issue of the magazine, Rockin'On Japan, he stated in an interview that his real name is Takahiro Moriuchi. In 2013, he is shown again in a photo using his last name, Moriuchi, where all ONE OK ROCK.\n" +
                        "\n" +
                        "Moriuchi signed with Johnny's Entertainment in 2002 and joined the boy band, NEWS, in 2003. Owing to a sex scandal in which pictures were taken of him in bed with a young lady, he left in 2004 to ostensibly \"focus on his studies\". He was briefly in a band called Chivalry of Music in 2004.\n" +
                        "\n" +
                        "Moriuchi was invited by guitarist Toru Yamashita to join One Ok Rock in 2005. The band released their first independent CD in 2006 and were signed by Amuse, Inc., whom they made their major debut with..\n" +
                        "\n" +
                        "The band did moderately well with music sales until they hit with major success in August 2012 upon the release of \"The Beginning\", which was the theme song for the live action movie adaption of the manga and anime, Rurouni Kenshin.\n" +
                        "\n" +
                        "The band's concerts have been sold out many times with high attendance and constantly adding new tour dates in Japan. ONE OK ROCK has also held concert tours overseas in Asia, U.S., Europe and South America..\n" +
                        "\n" +
                        "In March 2013, Simple Plan announced a new version of their song \"Summer Paradise\" with Moriuchi for a Japanese-only release They later performed together at the music festival, Punkspring 2013 in Tokyo, Japan.\n" +
                        "\n" +
                        "In November 2013, Moriuchi helped Pay Money to My Pain to sing one of their songs on their tribute album \"Gene\" after their vocalist, K (Kei Goto), died due to heart failure..\n" +
                        "\n" +
                        "In July 2017, Moriuchi was featured on the track \"Don't Let Me Go\" from Goldfinger's album The Knife as a guest vocalist. On 27 October 2017, he appeared at Linkin Park and Friends – Celebrate Life in Honor of Chester Bennington, performing \"Somewhere I Belong\" with the remaining members of Linkin Park.\n" +
                        "\n" +
                        "His greatest influences are Linkin Park, Good Charlotte, The Used, Red Hot Chili Peppers, Thirty Seconds to Mars, hide, Maroon 5, Adele, Rize, Issues, Sum 41, Green Day and Simple Plan. His favorite artists are X Japan.\n",
                "https://en.wikipedia.org/wiki/Takahiro_Moriuchi"
        );

        tambahBerita(berita2, db);
        idBerita++;

        //data berita 3
        try{
            tempDate = sdFormat.parse("10/03/2020 18:02");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Berita berita3 = new Berita(
                idBerita,
                "My First Story Band Stories",
                tempDate,
                storeImageFile(R.drawable.berita3),
                "MHS Younku fujin Album.(ASV PHOTO)",
                "Fujitora kenshin",
                "My First Story (stylized as MY FIRST STORY) is a Japanese rock band from Shibuya, Tokyo. Formed in 2011, the band currently consists of vocalist Hiroki Moriuchi.\n" +
                        "\n" +
                        "Guitarist Teruki Nishizawa, bassist Nobuaki Katou, and drummer Shouhei Sasaki. Their debut album My First Story (2012) caught people's attention and put them in lineups of major music festivals.\n" +
                        "\n" +
                        "Their fourth studio album Antithese (2016) ranked 4th on Oricon chart,[8] and directed their first Nippon Budokan live show, gathering 12,000.\n" +
                        "\n" +
                        "Having known Hiro through Pay Money to My Pain's vocalist K, producer Nori Outani suggested him to Sho in August 2011, when he was still mainly active in his previous band Fromus together.\n" +
                        "\n" +
                        "Only a little later, the newly founded formation decided to add another guitarist and Hiro remembered Teru who he had already tried to start a band with in their highschool days.\n" +
                        "\n" +
                        "\"In October 2011, they appeared in a music event sponsored by Sanei Shobo Co., which publishes the street fashion magazine Book (Ohlie), and performed in public for the first time.\n" +
                        "\n" +
                        "The band joined Swanky Dank, Blue Encount and Air Swell, 3 other indie bands, to make a split album project, titled Boneds in the beginning of 2014. In conjunction with the album release.\n" +
                        "\n" +
                        "Antithese, as well as the newly released single \"We're Just Waiting 4 You\" that they played together with Sho who joined them onstage for encore. Before finishing off their setlist, Hiro announced the last song.\n" +
                        "\n" +
                        "Overcoming the Night Without You\" (君のいない夜を越えて, Kimi No Inai Yoru O Koete) which will be the theme song of Ochanomizu Rock. Fans guessed that while All Lead Tracks was named after the fact that every track was used for another project.\n" +
                        "\n" +
                        "with a live orchestra and choir. Another limited single, “Merry Christmas” was released that day and sold at the venue only. Apart from the title song \"Merry Christmas\", the CD also included an acoustic version of \"Fukagyaku Replace\".\n" +
                        "\n" +
                        "On January 25, 2018, the band announced a new CD titled The Premium Symphony. It will contain eight previously released songs, rearranged with a full orchestra. The CD released March 28, 2018.\n" +
                        "\n" +
                        "Was a second DVD including bassist Nob's one man show ~This is Truly Alone~ (〜これがホントのALONEや〜, ~Kore ga honto no ALONE ya~) that was held on March 7, 2018 at Tsutaya O-East, Tokyo.\n" +
                        "\n" +
                        "\"The band's single \"Mukoku\", originally scheduled for release on July 31, 2019, was pushed back to August 14 in order to remove the track \"Underdog\" which featured a collaboration with Rize and The Bonez vocalist Jesse who had been arrested for marijuana possession.\n" +
                        "\n",
                "https://en.wikipedia.org/wiki/My_First_Story"

        );

        tambahBerita(berita3, db);
        idBerita++;

        //data berita 4
        try{
            tempDate = sdFormat.parse("19/04/2020 13:22");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Berita berita4 = new Berita(
                idBerita,
                "Simple Plan Career Until Now",
                tempDate,
                storeImageFile(R.drawable.berita4),
                "Simple Plan Save You Album (HD Photo)",
                "Mike Rayleight",
                "Simple Plan is a Canadian rock band from Montreal, Quebec formed in 1999. Since 2000, the band's lineup has consisted of Pierre Bouvier (lead vocals), Chuck Comeau (drums), Jeff Stinco (lead guitar), Sébastien Lefebvre (rhythm guitar, backing vocals).\n" +
                        "\n" +
                        "The band has released five studio albums: No Pads, No Helmets...Just Balls (2002), Still Not Getting Any... (2004), Simple Plan (2008), Get Your Heart On! (2011), and Taking One for the Team (2016).\n" +
                        "\n" +
                        "Performed at the Vans Warped Tour every year from 1999 to 2005, and in 2011, 2013, 2015, and 2018. The band also performed at the 2010 Winter Olympics closing ceremony.\n" +
                        "\n" +
                        "\"In 1993, lead vocalist Pierre Bouvier and drummer Chuck Comeau were in a band named Reset. In 1998, Comeau left soon after to go to college. In mid 1999.\n" +
                        "\n" +
                        "He met with high school friends guitarists Jeff Stinco and Sébastien Lefebvre who were in separate bands of their own, and combined to create Simple Plan. In late 1999, Bouvier and Comeau reacquainted at a Sugar Ray concert.\n" +
                        "\n" +
                        "In 2002, Simple Plan released their debut studio album, No Pads, No Helmets...Just Balls, which featured the singles \"I'm Just a Kid\", \"I'd Do Anything\", \"Addicted\", and \"Perfect\". The band was aiming for a pure pop punk record.\n" +
                        "\n" +
                        "The record was originally released in the United States with 12 tracks, ending with \"Perfect\". Enhanced and foreign editions came in several different versions .\n" +
                        "\n" +
                        "After nearly a year and a half in support of Still Not Getting Any..., the band ended most touring in February 2006, playing only a few shows, taking some time off, and beginning work on the third studio album.\n" +
                        "\n" +
                        "Simple Plan held an extensive tour schedule in support of the album. After completing an around-the-world promotional tour, Simple Plan played several December 2007 holiday shows.\n" +
                        "\n" +
                        "\"After continued promotional tours in January, Simple Plan played a triple bill in Camden Town, London on 27 January 2008, with the first show featuring songs from the band's first CD.\n" +
                        "\n" +
                        "Music In Minnesota reported that members of Simple Plan spent a day in Owatonna, Minnesota to appear in scenes of a punk rock musical titled Summertime Dropouts. .\n" +
                        "\n" +
                        "\"The members of Simple Plan created the Simple Plan Foundation, which focuses on teen problems ranging from suicide to poverty to drug addiction. As of 9 December 2005, the Simple Plan Foundation had raised more than $100,000.\n" +
                        "\n",
                "https://en.wikipedia.org/wiki/Simple_Plan"
        );

        tambahBerita(berita4, db);
        idBerita++;

        //data berita 5
        try{
            tempDate = sdFormat.parse("26/05/2020 05:49");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Berita berita5 = new Berita(
                idBerita,
                "Justin Drew Bieber Career Stories ",
                tempDate,
                storeImageFile(R.drawable.berita5),
                "Justin Bieber (live concert at BRIT AWWARDS 2016)",
                "Connor Drawless",
                "Justin Drew Bieber (born March 1, 1994) is a Canadian singer, songwriter and actor. Discovered at 13 years old by talent manager Scooter Braun after he had watched his YouTube cover song videos, Bieber was signed to RBMG Records in 2008.\n" +
                        "\n" +
                        "With Bieber's debut EP My World, released in late 2009, Bieber became the first artist to have seven songs from a debut record chart on the Billboard Hot 100.\n" +
                        "\n" +
                        "Bieber released his debut studio album My World 2.0 in 2010. Containing the hit single \"Baby\", the album debuted at number one on the US Billboard 200, making Bieber the youngest solo male act to top the chart in 47 years.\n" +
                        "\n" +
                        "\"Following his debut album and promotional tours, he released his 3D biopic-concert film Justin Bieber: Never Say Never, which was a box office success.\n" +
                        "\n" +
                        "In the years after the releases of the Under the Mistletoe (2011) and Believe (2012), Bieber had established himself as a teen idol and among the leading figures of the \"Canadian Invasion\", but faced several controversies, including run-ins with the law internationally before his first arrest in 2014.\n" +
                        "\n" +
                        "Bieber has sold more than 150 million records, making him one of the world's best-selling music artists. He has won numerous awards, including a Grammy Award, 15 American Music Awards, 20 Billboard Music Awards, two Brit Awards, a Latin Grammy Award, a record 21 MTV Europe Music Awards.\n" +
                        "\n" +
                        "To promote the album, Bieber appeared on several live programs including The View, the 2010 Kids' Choice Awards, Nightline, Late Show with David Letterman, The Dome and 106 & Park. Sean Kingston appeared on the album's next single, \"Eenie Meenie\" .\n" +
                        "\n" +
                        "On My World 2.0, Bieber's voice was noted to be deeper than it was in his debut EP, due to puberty. In April 2010, the singer remarked regarding his vocals: \"It cracks. Like every teenage boy, I'm dealing with it and I have the best vocal coach in the world.\n" +
                        "\n" +
                        "American crime drama CSI: Crime Scene Investigation, which aired on September 23, 2010. He played a \"troubled teen who is faced with a difficult decision regarding his only brother\", who is also a serial bomber.\n" +
                        "\n" +
                        "\"Love\", and briefly played the drums, at the 2010 MTV Video Music Awards on September 12, 2010.Bieber announced in October 2010 that he would be releasing an acoustic album, called My Worlds Acoustic.\n" +
                        "\n" +
                        "Bieber's music is mainly pop and R&B.[161] In 2010, Jody Rosen of Rolling Stone asserted that the content of his music was \"offering a gentle introduction to the mysteries and heartaches of adolescence: songs flushed with romance but notably free of sex itself\".\n" +
                        "\n" +
                        "\"Bieber has been referred to as the \"Prince of Pop\" and \"King of Teen Pop\" by contemporary journalists. Usher commented that while he and Bieber were both signed at the same age, \"I had the chance to ramp up my success, where this has happened to Bieber abruptly\".\n" +
                        "\n",
                "https://en.wikipedia.org/wiki/Justin_Bieber"
        );

        tambahBerita(berita5, db);
        idBerita++;

    }
}
