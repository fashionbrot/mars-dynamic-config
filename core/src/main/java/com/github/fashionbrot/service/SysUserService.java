package com.github.fashionbrot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.fashionbrot.entity.SysUserEntity;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.SysUserReq;

/**
 * 系统用户表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-17
 */
public interface SysUserService extends IService<SysUserEntity> {

    Object pageReq(SysUserReq req);


    void updatePwd(String oldPassword, String newPassword);

    Object login(String account, String password);

    void add(SysUserEntity entity);

    void edit(SysUserEntity entity);
}