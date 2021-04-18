package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.consts.GlobalConst;
import com.github.fashionbrot.entity.SysRoleEntity;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.enums.RespEnum;
import com.github.fashionbrot.exception.MarsException;
import com.github.fashionbrot.mapper.SysRoleMapper;
import com.github.fashionbrot.mapper.SysUserMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.SysUserReq;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.util.CookieUtil;
import com.github.fashionbrot.util.EncryptUtil;
import com.github.fashionbrot.util.JwtTokenUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.fashionbrot.vo.RespVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 系统用户表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public Object pageReq(SysUserReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SysUserEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }


    @Override
    public void updatePwd(String pwd, String newPwd) {
        if (pwd.equals(newPwd)) {
            throw new MarsException("新密码和原密码一致，请修改");
        }
        LoginModel login = getLogin();
        SysUserEntity user = baseMapper.selectById(login.getUserId());
        if (user != null) {
            String salt =user.getSalt();
            String encryptPassword = EncryptUtil.encryptPassword(pwd, salt);
            if (!user.getPassword().equals(encryptPassword)) {
                throw new MarsException("原密码输入错误，请重新输入");
            }
            salt =EncryptUtil.getSalt();
            String password =EncryptUtil.encryptPassword(newPwd,salt);
            user.setPassword(password);
            user.setSalt(salt);
            if (baseMapper.updateById(user) != 1) {
                throw new MarsException(RespEnum.UPDATE_ERROR);
            }
        }else{
            throw new MarsException("请刷新重试");
        }
    }

    @Override
    public Object login(String account, String password) {

        SysUserEntity userInfo = baseMapper.selectOne(new QueryWrapper<SysUserEntity>().eq("account", account));
        if (userInfo == null) {
            throw new MarsException(RespEnum.USER_OR_PASSWORD_IS_ERROR);
        }
        if (userInfo.getStatus() == 0) {
            throw new MarsException("用户已关闭");
        }
        String salt = userInfo.getSalt();
        String encryptPassword = EncryptUtil.encryptPassword(password, salt);
        if (!userInfo.getPassword().equals(encryptPassword)) {
            throw new MarsException(RespEnum.USER_OR_PASSWORD_IS_ERROR);
        }

        SysRoleEntity roleInfo =userInfo.getRoleId()==null?null : sysRoleMapper.selectById(userInfo.getRoleId());
        String roleName ="";
        Long roleId = null;
        if (roleInfo!=null){
            roleName =roleInfo.getRoleName();
            roleId = roleInfo.getId();
        }
        String token = JwtTokenUtil.createToken(userInfo.getId(),userInfo.getUserName(),roleId,roleName,userInfo.getSuperAdmin()==1);

        CookieUtil.setCookie(request,response,userInfo.getUserName(),roleName,token);

        LoginModel loginModel=LoginModel.builder()
                .userId(userInfo.getId())
                .userName(userInfo.getUserName())
                .roleName(roleName)
                .roleId(roleId)
                .build();
        setRequest(loginModel);

        return null;
    }

    @Override
    public void add(SysUserEntity entity) {
       String salt = EncryptUtil.getSalt();
       entity.setSalt(salt);
       entity.setPassword(EncryptUtil.encryptPassword(entity.getPassword(),salt));
       baseMapper.insert(entity);
    }

    @Override
    public void edit(SysUserEntity entity) {
        String salt = EncryptUtil.getSalt();
        entity.setSalt(salt);
        entity.setPassword(EncryptUtil.encryptPassword(entity.getPassword(),salt));
        baseMapper.updateById(entity);
    }

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