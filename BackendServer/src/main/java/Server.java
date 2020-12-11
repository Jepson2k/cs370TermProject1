import spark.Spark;
import spark.Request;
import spark.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Server{

    private final Logger log = LoggerFactory.getLogger(Server.class);

    public Server(){
        this.configureRestfulApiServer();
        this.processRestfulApiRequests();
    }

    private void configureRestfulApiServer(){
        Spark.port(8080);
        System.out.println("Server configured to listen on port 8080");
    }

    private void processRestfulApiRequests(){
        Spark.get("/", this::echoRequest);
        Spark.post("/", this::RestfulApiRequestPost);
    }

    private String RestfulApiRequestPost(Request req, Response res){
        System.out.println(req.body());
        return req.body();
    }

    private String echoRequest(Request request, Response response){
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.status(200); //OK
        response.body(passwordStrength(request.body()));
        return HttpRequestToJson(request);
    }

    private String HttpRequestToJson(Request request){
        return "{\n"
                + "\"attributes\":\"" + request.attributes() + "\",\n"
                + "\"body\":\"" + request.body() + "\",\n"
                + "\"contentLength\":\"" + request.contentLength() + "\",\n"
                + "\"contentType\":\"" + request.contentType() + "\",\n"
                + "\"contextPath\":\"" + request.contextPath() + "\",\n"
                + "\"cookies\":\"" + request.cookies() + "\",\n"
                + "\"headers\":\"" + request.headers() + "\",\n"
                + "\"host\":\"" + request.host() + "\",\n"
                + "\"ip\":\"" + request.ip() + "\",\n"
                + "\"params\":\"" + request.params() + "\",\n"
                + "\"pathInfo\":\"" + request.pathInfo() + "\",\n"
                + "\"serverPort\":\"" + request.port() + "\",\n"
                + "\"protocol\":\"" + request.protocol() + "\",\n"
                + "\"queryParams\":\"" + request.queryParams() + "\",\n"
                + "\"requestMethod\":\"" + request.requestMethod() + "\",\n"
                + "\"scheme\":\"" + request.scheme() + "\",\n"
                + "\"servletPath\":\"" + request.servletPath() + "\",\n"
                + "\"session\":\"" + request.session() + "\",\n"
                + "\"uri()\":\"" + request.uri() + "\",\n"
                + "\"url()\":\"" + request.url() + "\",\n"
                + "\"userAgent\":\"" + request.userAgent() + "\",\n"
                + "}";
    }

    private String passwordStrength(String password){
        int length = password.length();
        List<Float> alphanumericProbability = characterProbability(password, length);
        float entropy = 0;

        // Calculate Shannon entropy
        for (int i = 0; i < alphanumericProbability.size(); i++) {
            entropy += alphanumericProbability.get(i) * Math.log(alphanumericProbability.get(i)) / Math.log(2);
        }

        int entropyRounded = 0;
        if
        entroyRounded = Math.round((Math.round(entropy * 4)/4f) * 100);
        String verbalRating = "Abysmal";
        switch (entropyRounded) {
            case 25:
                verbalRating = "Heinous";
                break;
            case 50:
                verbalRating = "Revolting";
                break;
            case 75:
                verbalRating = "Vile";
                break;
            case 100:
                verbalRating = "Horrendous";
                break;
            case 125:
                verbalRating = "Atrocious";
                break;
            case 150:
                verbalRating = "Appalling";
                break;
            case 175:
                verbalRating = "Abominable";
                break;
            case 200:
                verbalRating = "Deplorable";
                break;
            case 225:
                verbalRating = "Repulsive";
                break;
            case 250:
                verbalRating = "Despicable";
                break;
            case 275:
                verbalRating = "Dreadful";
                break;
            case 300:
                verbalRating = "Horrid";
                break;
            case 325:
                verbalRating = "Horrible";
                break;
            case 350:
                verbalRating = "Awful";
                break;
            case 375:
                verbalRating = "Terrible";
                break;
            case 400:
                verbalRating = "Shameful";
                break;
            case 425:
                verbalRating = "Very Bad";
                break;
            case 450:
                verbalRating = "Really Bad";
                break;
            case 475:
                verbalRating = "Rubbish";
                break;
            case 500:
                verbalRating = "Unsatisfactory";
                break;
            case 525:
                verbalRating = "Bad";
                break;
            case 550:
                verbalRating = "Poor";
                break;
            case 575:
                verbalRating = "Quite Bad";
                break;
            case 600:
                verbalRating = "Pretty Bad";
                break;
            case 625:
                verbalRating = "Somewhat Bad";
                break;
            case 650:
                verbalRating = "Below Average";
                break;
            case 675:
                verbalRating = "Appalling";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
            case 25:
                verbalRating = "Horrible";
                break;
        }



        return "Shannon Entropy: -entropy;
    }

    private List<Float> characterProbability(String password, int passwordLength) {
        float[] byteOccurance = new float[256];
        for (int i = 0; i < byteOccurance.length; i++) {
            byteOccurance[i] = 0;
        }
        for (int i = 0; i < passwordLength; i++) {
            byteOccurance[password.charAt(i)]++;
        }

        for (int i = 0; i < byteOccurance.length; i++) {

        }

        var a = 0, u = 0, n = 0, ns = 0, r = 0, sp = 0, s = 0, chars = 0;

        for (var i = 0 ; i < pass.length; i ++)
        {
            var c = pass.charAt(i);

            if (a == 0 && 'abcdefghijklmnopqrstuvwxyz'.indexOf(c) >= 0)
            {
                chars += 26;
                a = 1;
            }
            if (u == 0 && 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.indexOf(c) >= 0)
            {
                chars += 26;
                u = 1;
            }
            if (n == 0 && '0123456789'.indexOf(c) >= 0)
            {
                chars += 10;
                n = 1;
            }
            if (ns == 0 && '!@#$%^&*()'.indexOf(c) >= 0)
            {
                chars += 10;
                ns = 1;
            }
            if (r == 0 && "`~-_=+[{]}\\|;:'\",<.>/?".indexOf(c) >= 0)
            {
                chars += 22;
                r = 1;
            }
            if (sp == 0 && c == ' ')
            {
                chars += 1;
                sp = 1;
            }
            if (s == 0 && (c < ' ' || c > '~'))
            {
                chars += 32 + 128;
                s = 1;
            }
        }

        return chars;

        List<Float> byteProbability = new ArrayList<>();
        for (int i = 0; i < byteOccurance.length; i++) {
            if (byteOccurance[i] > 0) byteProbability.add(byteOccurance[i] / passwordLength);
        }
        return byteProbability;



    }

    public static void main(String[] programArgs){
        Server restfulServer = new Server(); //Never returns!
    }
}