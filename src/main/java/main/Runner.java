package main;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import controller.DatabaseController;
import controller.LoginController;

@Configuration 
@ComponentScan({"controller","dao","main","model_class","utility","view"})
public class Runner extends DatabaseController {
	@Autowired
	LoginController loginController;
	
	public static int databaseOption;
	
	public void start() {
	clearDatabase();
	initDatabase();
	loginController.runController();
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(Runner.class); 
		Runner runner = context.getBean(Runner.class); 
		System.out.println("Context contains AccountView:" + context.containsBean("AccountView"));
		System.out.println("Context contains Accountview:" + context.containsBean("Accountview"));
		System.out.println("Context contains accountview:" + context.containsBean("accountview"));
		runner.start();
	}
	
	//Oefenen met @Bean
    @Bean(name= "textIO")
    public TextIO getTextIO(){ 
    	TextIO textIO = TextIoFactory.getTextIO();
    	return textIO; 
    }
	
	
}

