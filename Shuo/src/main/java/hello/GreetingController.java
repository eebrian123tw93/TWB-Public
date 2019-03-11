package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

  @GetMapping("/greeting")
  public String greeting(
      @RequestParam(name = "name", required = false, defaultValue = "World") String name,
      Model model) {
    model.addAttribute("name", name);
    return "greeting";
  }

  @GetMapping("/test")
  public String test() {
    return "test";
  }

  @GetMapping("/goodluck")
  public String goodluck() {
    return "goodluck";
  }

  @GetMapping("/bryan")
  public String bryan() {
    return "789";
  }

}
