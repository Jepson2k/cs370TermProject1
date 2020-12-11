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
        Spark.get("/", this::RestfulApiRequestGet);//(request, response) -> passwordStrength(request.body()));
        Spark.post("/", this::RestfulApiRequestPost);
    }

    private String RestfulApiRequestGet(Request req, Response res){
        System.out.println(passwordStrength(req.body()));
        return passwordStrength(req.body());
    }

    private String RestfulApiRequestPost(Request req, Response res){
        System.out.println(req.body());
        return passwordStrength(req.body());
    }

    private String echoRequest(Request request, Response response){
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.status(200); //OK
        response.body(passwordStrength(request.body()));
        return HttpRequestToJson(request);
    }

    private String HttpRequestToJson(Request request) {
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
                + "\"userAgent\":\"" + request.userAgent() + "\"\n"
                + "}";
    }

    private String passwordStrength(String password){
        int length = password.length();
        float[] arrayOfEntropy = shannonEntropy(password, length);

        return String.format("Simple Shannon Entropy: %.2f" + "\n" +
                "Entropy - Randomly Generated: %.2f" + "\n" +
                "Entropy - User Generated: %.2f" + "\n", arrayOfEntropy[0], arrayOfEntropy[1], arrayOfEntropy[2]);
    }

    private float[] shannonEntropy(String password, int passwordLength) {
        float[] byteOccurance = new float[256];
        for (int i = 0; i < passwordLength; i++) {
            byteOccurance[password.charAt(i)]++;
        }

        int lowerCaseUsed = 0;
        int upperCaseUsed = 0;
        int digitUsed = 0;
        int symbolUsed = 0;
        int characterSet = 0;

        for (int i = 0; i < byteOccurance.length; i++) {
            if (byteOccurance[i] != 0) {
                if (lowerCaseUsed == 0 && i >= 97 && i <= 121) {
                    characterSet += 26;
                    lowerCaseUsed = 1;
                }
                if (upperCaseUsed == 0 && i >= 65 && i <= 90) {
                    characterSet += 26;
                    upperCaseUsed = 1;
                }
                if (digitUsed == 0 && i >= 48 && i <= 57) {
                    characterSet += 10;
                    digitUsed = 1;
                }
                if (symbolUsed == 0 && ((i >= 32 && i <= 47) || (i >= 58 && i <= 64) || (i >= 91 && i <= 96) || (i >= 123 && i <= 126))) {
                    characterSet += 32;
                    symbolUsed = 1;
                }
            }
        }

        float entropyIfPassTrulyRandom = (float) (Math.log(Math.pow(characterSet, passwordLength))/Math.log(2));

        float entropy = 0;
        for (int i = 0; i < passwordLength; i++) {
            if ((i > 3 && i < passwordLength - 3) && ((((int) password.charAt(i) <= 96) && ((int) password.charAt(i) >= 32)) || (((int) password.charAt(i) <= 126) && ((int) password.charAt(i) >= 123)))) {
                if (i <= 7) entropy += characterSet * 8;
                if (i >= 9 && i <= 20) entropy += characterSet * 7.5;
                if (i >= 21) entropy += characterSet * 6;
            } else {
                if (i == 0) entropy += characterSet * 4;
                if (i >= 1 && i <= 2) entropy += characterSet * 2;
                if (i >= 3 && i <= 7) entropy += characterSet * 2;
                if (i >= 9 && i <= 20) entropy += characterSet * 1.5;
                if (i >= 21) entropy += characterSet;
            }
        }

        float simplifiedShannonEntropy = 0;
        List<Float> byteProbability = new ArrayList<>();
        for (float v : byteOccurance) {
            if (v > 0) byteProbability.add(v / passwordLength);
        }
        for (Float aFloat : byteProbability) {
            simplifiedShannonEntropy += aFloat * Math.log(aFloat) / Math.log(2);
        }

        return new float[]{-simplifiedShannonEntropy, entropyIfPassTrulyRandom, entropy};
    }

//    private void verbalRating(float[] arrayOfEntropy) {
//        int entropyRounded = 0;
//        entroyRounded = Math.round((Math.round(entropy * 4)/4f) * 100);
//        String verbalRating = "Abysmal";
//        switch (entropyRounded) {
//            case 25:
//                verbalRating = "Heinous";
//                break;
//            case 50:
//                verbalRating = "Revolting";
//                break;
//            case 75:
//                verbalRating = "Vile";
//                break;
//            case 100:
//                verbalRating = "Horrendous";
//                break;
//            case 125:
//                verbalRating = "Atrocious";
//                break;
//            case 150:
//                verbalRating = "Appalling";
//                break;
//            case 175:
//                verbalRating = "Abominable";
//                break;
//            case 200:
//                verbalRating = "Deplorable";
//                break;
//            case 225:
//                verbalRating = "Repulsive";
//                break;
//            case 250:
//                verbalRating = "Despicable";
//                break;
//            case 275:
//                verbalRating = "Dreadful";
//                break;
//            case 300:
//                verbalRating = "Horrid";
//                break;
//            case 325:
//                verbalRating = "Horrible";
//                break;
//            case 350:
//                verbalRating = "Awful";
//                break;
//            case 375:
//                verbalRating = "Terrible";
//                break;
//            case 400:
//                verbalRating = "Shameful";
//                break;
//            case 425:
//                verbalRating = "Very Bad";
//                break;
//            case 450:
//                verbalRating = "Really Bad";
//                break;
//            case 475:
//                verbalRating = "Rubbish";
//                break;
//            case 500:
//                verbalRating = "Unsatisfactory";
//                break;
//            case 525:
//                verbalRating = "Bad";
//                break;
//            case 550:
//                verbalRating = "Poor";
//                break;
//            case 575:
//                verbalRating = "Quite Bad";
//                break;
//            case 600:
//                verbalRating = "Pretty Bad";
//                break;
//            case 625:
//                verbalRating = "Somewhat Bad";
//                break;
//            case 650:
//                verbalRating = "Below Average";
//                break;
//        }
//    }

    public static void main(String[] programArgs){
        Server restfulServer = new Server(); //Never returns!
    }
}