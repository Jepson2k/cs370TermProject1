import spark.Spark;
import spark.Request;
import spark.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

public class Server{

    private final Logger log = LoggerFactory.getLogger(Server.class);

    public Server(String password){
        this.configureRestfulApiServer();
        this.sendRequest(password);
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

    private void sendRequest(String password){
        try {
            java.net.URL backEnd = new java.net.URL("http://BackendServer:8080/");
            HttpURLConnection connection = (HttpURLConnection) backEnd.openConnection();
            connection.setRequestMethod("GET");
            String toSend = "";
            toSend = toSend + (URLEncoder.encode("password", "UTF-8")) + "=" + (URLEncoder.encode(password, "UTF-8")) + "&";
            connection.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(toSend);
            out.flush();
            out.close();
        }
        catch (Exception bad){
        }
    }

    private String RestfulApiRequestPost(Request req, Response res){
        System.out.println(req.body());
        return req.body();
    }

    private String echoRequest(Request request, Response response){
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.status(200); //OK
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

    public static void main(String[] programArgs){
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter Password:");
        String password = userInput.nextLine();
        Server restfulServer = new Server(password); //Never returns!
    }
}