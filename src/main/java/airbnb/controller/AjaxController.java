package airbnb.controller;

import airbnb.service.UsersService;
import airbnb.model.UsersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Σταυρίνα on 27/9/2017.
 */
@RestController
public class AjaxController {
    @Autowired
    private UsersService usersService;

    @RequestMapping(value = "/usernameCheck", method = RequestMethod.POST)
    public @ResponseBody AjaxResponseBody getSearchResultViaAjax(@RequestBody UsersEntity userInfo) {
//
        System.out.println("Inside AjaxController with |"+userInfo.getUsername()+ "|");
        AjaxResponseBody result = new AjaxResponseBody();
        if (usersService.findByUsername(userInfo.getUsername()) != null){
            result.setCode("400");
            result.setMsg("Username already in use");
        }

        return result;

    }
}
