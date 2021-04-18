package com.github.fashionbrot.service;

import com.github.fashionbrot.consts.GlobalConst;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.util.CookieUtil;
import com.github.fashionbrot.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class UserLoginService {

    @Autowired
    private HttpServletRequest request;

    public LoginModel getLogin() {
        String token = CookieUtil.getCookieValue(request, GlobalConst.AUTH_KEY, false);
        if (StringUtils.isEmpty(token)) {
            throw new MarsException(RespEnum.SIGNATURE_MISMATCH);
        }
        LoginModel model = JwtTokenUtil.getLogin(token);
        if (model!=null){
            request.setAttribute(GlobalConst.AUTH_KEY,model);
        }
        return model;
    }

    public void setRequest(LoginModel loginModel){
        request.setAttribute(GlobalConst.AUTH_KEY,loginModel);
    }

    public LoginModel getSafeLoginUser() {
        try {
            return getLogin();
        }catch (Exception e){}
        return null;
    }

    public LoginModel getSafeLogin() {
        try {
            Object attribute = request.getAttribute(GlobalConst.AUTH_KEY);
            if (attribute==null){
                return  getLogin();
            }else{
                return (LoginModel) attribute;
            }
        }catch (Exception e){}
        return null;
    }


}
