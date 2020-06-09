package WineCellar.SEP4;

import WineCellar.SEP4.WebSocket.WebSocketClient;
import WineCellar.SEP4.database.Adapter;
import WineCellar.SEP4.database.Database;
import WineCellar.SEP4.resource.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Sep4Application {
	public static void main(String[] args) {
		WebSocketClient client=new WebSocketClient("wss://iotnet.teracom.dk/app?token=vnoSzwAAABFpb3RuZXQudGVyYWNvbS5ka9oKoZjjuRRrYXdGtr_qztU=",Adapter.getInstance());
		SpringApplication.run(Sep4Application.class, args);
	}
}
