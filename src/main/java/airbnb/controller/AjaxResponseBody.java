package airbnb.controller;

import com.fasterxml.jackson.annotation.JsonView;
import airbnb.controller.Views;

/**
 * Created by Σταυρίνα on 27/9/2017.
 */
public class AjaxResponseBody {
    @JsonView(Views.Public.class)
    private  String msg;

    @JsonView(Views.Public.class)
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
