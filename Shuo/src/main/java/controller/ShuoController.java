package controller;

import model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/Shuo")
public class ShuoController {
  private static final Logger logger = LoggerFactory.getLogger(ShuoController.class);

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity register(@RequestBody UserModel userModel) {
    logger.info(userModel.getUserId());
    return new ResponseEntity(HttpStatus.OK);
  }
}
