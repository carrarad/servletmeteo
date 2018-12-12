import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    ArrayList<String> conditions;
    public Servlet(){
        conditions = new ArrayList<>();
        conditions.add("Sunny");
        conditions.add("Cloudy");
        conditions.add("Rainy");
        conditions.add("Snowy");

    }

    String getConditionXML(int day){
        String temp = day == 0 ? "<condition>" : "<forecast>";
        temp += day !=0 ? "<day>" + day + "</day>": "" ;
        int low = (int)(Math.random()*60);
        int high = (int)(Math.random()*(60-low))+low-20;
        low -= 20;
        temp += "<low>" + low + "</low>";
        temp += "<high>" + high + "</high>";
        int code = (int)(Math.random()*conditions.size());
        temp += "<text>" + conditions.get(code) + "</text>";
        temp += "<code>" + code + "</code>";
        temp += day == 0 ? "</condition>" : "</forecast>";
        return temp;
    }

    String getConditionJSON(int day){
        String temp;
        if(day == 0){
            temp = "\"condition\":{";
        }else if(day == 1){
            temp =  "\"" + day + "\":{";
        }else{
            temp =  ",\"" + day + "\":{";
        }
        int low = (int)(Math.random()*60);
        int high = (int)(Math.random()*(60-low))+low-20;
        low -= 20;
        temp += "\"low\":\"" + low + "\",";
        temp += "\"high\":\"" + high + "\",";
        int code = (int)(Math.random()*conditions.size());
        temp +="\"text\":\"" + conditions.get(code) + "\",";
        temp +="\"code\":\"" + code + "\"";
        temp += "}";
        return temp;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /*
        Esempi richiesta:
        ?format=xml&city=bergamo&forecast=4
        ?format=json&city=brescia&forecast=2
        Se la citta non e' specificata o e' vuota e se il numero di giorni richiesti sono inferiori a 0 o maggiori di 5 non verra erogato il servizio
        //TODO: Gestire i casi di errore

        Con forecast == 0 viene visualizzata solo la condizione meteo del giorno attuale
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("city"), format = request.getParameter("format");
        int forecast = Integer.parseInt(request.getParameter("forecast"));

        if(forecast >= 0 && forecast <=5){
            if(city!=null && city!= ""){
                if(format.equals("xml")){
                    response.setContentType("application/xml");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                        out.println("<query>");
                        out.println("<created>" + new Date().toString() + "</created>");
                        out.println(getConditionXML(0));
                        for(int i=0;i<forecast;i++){
                            out.println(getConditionXML(i+1));
                        }
                        out.println("</query>");
                    }
                }else if(format.equals("json")){
                    response.setContentType("application/json");
                    try (PrintWriter out = response.getWriter()) {
                        out.println("{");
                        out.println("\"query\":{");
                        out.println("\"created\": \"" + new Date().toString() + "\",");
                        out.println(getConditionJSON(0));
                        out.println(",\"forecast\":{");
                        for(int i=0;i<forecast;i++){
                            out.println(getConditionJSON(i+1));
                        }
                        out.println("}}}");
                    }
                }else{
                    //TODO: Gestire il caso di errore (format)
                }
            }else{
                //TODO: Gestire il caso di errore (Citta)
            }
        }else{
            //TODO: Gestire il caso di errore (Nr giorni richiesti)
        }
    }
}
