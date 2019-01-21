package com.example.dragovicd.popis;
import com.example.dragovicd.popis.entity.OsnovnoSredstvo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandlerArtikli {

    List <OsnovnoSredstvo> artikli;
    private OsnovnoSredstvo artikal;
    private String text;
    private DatabaseHelper db;
    public XMLPullParserHandlerArtikli() {
        artikli = new ArrayList<OsnovnoSredstvo>();
    }

    public List<OsnovnoSredstvo> vratiArtikle() { return artikli; }

    public List<OsnovnoSredstvo> parse(InputStream is) {

        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);

            parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if(tagname.equalsIgnoreCase("artikal")) {
                            artikal = new OsnovnoSredstvo();
                        }
                        break;

                    case XmlPullParser.TEXT:

                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:

                        if(tagname.equalsIgnoreCase("artikal")) {
                            artikli.add(artikal);
                        }else if(tagname.equalsIgnoreCase("sifra")) {
                            artikal.setSifra(text);
                        }else if(tagname.equalsIgnoreCase("naziv")) {
                            artikal.setNaziv(text);
                        }else if(tagname.equalsIgnoreCase("lokacija")) {
                            artikal.setLokacija(text);
                        }else if(tagname.equalsIgnoreCase("slika")) {
                            artikal.setSifra(text);
                        }else if(tagname.equalsIgnoreCase("popisan")) {
                            artikal.setPopisan(text);
                        }


                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return artikli;
    }
}
