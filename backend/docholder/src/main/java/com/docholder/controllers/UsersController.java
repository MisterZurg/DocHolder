package com.docholder.controllers;

import com.docholder.model.Repository;
import com.docholder.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {
    Repository rep = new Repository();

    // Поиск клиента
    @GetMapping("/users")
    public User userById(
            @RequestParam(value = "id") Integer id) {
        return rep.userById(id);
    }

    @PostMapping("/users")
    public User setUser(
            @RequestParam(value = "id") int id,
            @RequestParam(value = "name") String name) {
        User usr = rep.userById(id);
        if (usr != null) {
            usr.setName(name);
            return usr;
        } else {
            return rep.addUser(new User(id, name));
        }
    }

    @DeleteMapping("/users")
    public User deleteUser(
            @RequestParam(value = "id") Integer id) {
        /// Client cl = rep.clientById(id);
        return rep.deleteUser(id);
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