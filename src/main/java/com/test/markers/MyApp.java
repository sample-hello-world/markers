package com.test.markers;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.test.db.BiomarkerRepository;
import com.test.db.ProcessRepository;






@SpringBootApplication
@ComponentScan(basePackages = {"com.test.db"})
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages="com.test.db")
@EntityScan(basePackages="com.test.db")


public class MyApp {
	

//	
	@Autowired
	ApplicationContext ctx;
	
	//@Autowired
	private BiomarkerRepository biomarkerRepository;
	
	private ProcessRepository processRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    } 
    
    
//    @Bean
//	public CommandLineRunner loadProcesses(ProcessRepository repo){
//		return (args) -> {
//			
//			for (Process process : repo.findAll()){
//				System.out.println(process.toString());
//			}
//			
//		};
//    }
    
   
//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        return args -> {
//
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
//
//        };
//    }
//   
    
    @Bean
    CommandLineRunner init( final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService ) {

        return new CommandLineRunner() {

            public void run(String... strings) throws Exception {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("markerName", "Test");
                variables.put("temperature", "90");
                biomarkerRepository = (BiomarkerRepository) ctx.getBean("biomarkerRepository");
//                for(Biomarker makers : biomarkerRepository.findAll() ){
//               	System.out.println("-------" + makers.toString() );
//                }
                processRepository = (ProcessRepository) ctx.getBean("processRepository");
                for (com.test.db.Process process1 : processRepository.findAll()){
                	System.out.println("------------> " + process1.toString());
                	runtimeService.startProcessInstanceByKey("temperaturecropprocess", variables);
                }
            }
        };
    }
    
    @Bean
    public DataSource database() {
        return (DataSource) DataSourceBuilder.create()
                .url("jdbc:mysql://127.0.0.1:3306/biomarker?characterEncoding=UTF-8")
                .username("alfresco")
                .password("alfresco")
                .driverClassName("com.mysql.jdbc.Driver")
                .build();
    }
    
    
}