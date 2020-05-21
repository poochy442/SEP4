package WineCellar.SEP4.main;

import WineCellar.SEP4.WebSocket.WebSocketClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Sep4Application {
	public static void main(String[] args) {
		WebSocketClient client=new WebSocketClient("wss://iotnet.teracom.dk/app?token=vnoSzwAAABFpb3RuZXQudGVyYWNvbS5ka9oKoZjjuRRrYXdGtr_qztU=");
		SpringApplication.run(Sep4Application.class, args);
	}
}
