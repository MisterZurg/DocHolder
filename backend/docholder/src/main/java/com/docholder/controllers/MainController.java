package com.docholder.controllers;

import com.docholder.model.Client;
import com.docholder.model.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    Repository rep = new Repository();

    // Поиск клиента
    @GetMapping("/clients")
    public Client clientById(
            @RequestParam(value = "id") Integer id) {
        return rep.clientById(id);
    }

    @PostMapping("/clients")
    public Client setClient(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name) {
        Client cl = rep.clientById(id);
        if (cl != null) {
            cl.setName(name);
            return cl;
        } else {
            return rep.addClient(new Client(id, name));
        }
    }

    @DeleteMapping("/clients")
    public Client deleteClient(
            @RequestParam(value = "id") Integer id) {
        /// Client cl = rep.clientById(id);
        return rep.deleteClient(id);
    }
    // Мозг отказал, мапинг выше уже реализует POST / UPDATE
    /*
    @PutMapping("/clients")
    public Client updateClient(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name){
        Client cl = rep.clientById(id);
        if (cl != null){
            cl.setName(name);
            return cl;
        } else {
            return rep.addClient(new Client(id, name));
        }
    }
    */

    // Это писал Влад, мб пригодится
	/*

	@RequestMapping("/")	// http://server/path
	 public Map<String, String> home(){
	 	HashMap<String, String> map = new HashMap<>();
	 	map.put("company", "Рога и Копыта");
	 	map.put("company", "Пе'шков Limited");
	 	map.put("company", "Stuff");
	 	return map;
	 }

	@Configuration
	public class WebConfig extends WebMvcConfigurerAdapter {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
		}
	}
	*/
}