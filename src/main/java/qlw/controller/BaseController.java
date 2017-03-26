package qlw.controller;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import qlw.model.Users;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qlw on 2017/1/20 0020.
 *
 * @Date 2017/1/20 0020 13:52
 */
public class BaseController {
    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    public Users getUsers(HttpServletRequest request) {
        return (Users) request.getSession().getAttribute("user");
    }


    public boolean isSuperAdmin(Users user) {
        return user.getId() == 1;
    }
}
