package com.github.fashionbrot.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.fashionbrot.annotation.MarsPermission;
import com.github.fashionbrot.entity.SysMenuEntity;
import com.github.fashionbrot.mapper.SysMenuMapper;
import com.github.fashionbrot.model.LoginModel;
import com.github.fashionbrot.req.SysMenuReq;
import com.github.fashionbrot.service.SysMenuService;
import com.github.fashionbrot.service.SysUserService;
import com.github.fashionbrot.service.UserLoginService;
import com.github.fashionbrot.util.CaffeineCacheUtil;
import com.github.fashionbrot.util.ConvertUtil;
import com.github.fashionbrot.validated.util.StringUtil;
import com.github.fashionbrot.vo.PageVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 菜单表
 *
 * @author fashionbrot
 * @email fashionbrot@163.com
 * @date 2021-04-18
 */
@Service
public class SysMenuServiceImpl  extends ServiceImpl<SysMenuMapper, SysMenuEntity> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private UserLoginService userLoginService;

    @Override
    public Object pageReq(SysMenuReq req) {
        Page<?> page = PageHelper.startPage(req.getPageNum(),req.getPageSize());
        Map<String,Object> map = ConvertUtil.toMap(req);
        List<SysMenuEntity> listByMap = baseMapper.selectByMap(map);

        return PageVo.builder()
                .rows(listByMap)
                .total(page.getTotal())
                .build();
    }

    @Override
    public boolean checkPermission(Object handler, LoginModel model) {
        if (handler instanceof HandlerMethod) {
            //如果是超级管理员
            if (model!=null && model.isSuperAdmin()){
                return true;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            String permission = null;
            MarsPermission classAnnotation = method.getDeclaringClass().getAnnotation(MarsPermission.class);
            if (classAnnotation!=null){
                MarsPermission methodAnnotation = method.getAnnotation(MarsPermission.class);
                if (methodAnnotation==null){ //方法没有权限直接返回
                    return true;
                }
                permission = classAnnotation.value() + methodAnnotation.value();
            }else{
                MarsPermission methodAnnotation = method.getAnnotation(MarsPermission.class);
                if (methodAnnotation ==null){ //方法没有权限直接返回
                    return true;
                }
                permission =  methodAnnotation.value();
            }

            if (permission!=null) {
                List<SysMenuEntity> menuBarList = getMenus(model);
                if (CollectionUtils.isNotEmpty(menuBarList)) {
                    for (SysMenuEntity m : menuBarList) {
                        //验证菜单是否有权限
                        if ( (m.getMenuLevel() == 2 ) && StringUtil.isNotEmpty(m.getPermission()) && m.getPermission().contains(permission)) {
                            return true;
                        }
                    }
                }
                return false;
            }
            return true;
        }

        return false;
    }

    public List<SysMenuEntity> getMenus(LoginModel model) {
        Long userId = model.getUserId();
        List<SysMenuEntity> menuBarList = (List<SysMenuEntity>) CaffeineCacheUtil.getCache(userId);

        if (CollectionUtils.isNotEmpty(menuBarList)) {
            return menuBarList;
        } else {
            if (model.isSuperAdmin()){
                menuBarList = baseMapper.selectList(null);
            }else{
                Map<String,Object> map =new HashMap<>();
                map.put("roleId",model.getRoleId());
                menuBarList = baseMapper.selectMenuRoleByUser(map);
            }
            CaffeineCacheUtil.setCache(userId,menuBarList);
        }
        return menuBarList;
    }

    @Override
    public List<SysMenuEntity> loadAllMenu() {

        LoginModel loginModel = userLoginService.getLogin();

        List<SysMenuEntity> checkedList = null;
        if (loginModel.isSuperAdmin() ){
            QueryWrapper q = new QueryWrapper();
            q.in("menu_level", Arrays.asList(1,2));
            checkedList = baseMapper.selectList(q);
        }else{
            Map<String,Object> map=new HashMap<>();
            if (!loginModel.isSuperAdmin()){
                map.put("roleId",loginModel.getRoleId());
                map.put("orgId",loginModel.getOrgId());
            }
            checkedList = baseMapper.selectMenuRole(map);
        }

        Map<String, Boolean> checkedMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(checkedList)) {
            for (SysMenuEntity mm : checkedList) {
                checkedMap.put(mm.getId().toString(), true);
            }
        }

        if (CollectionUtils.isNotEmpty(checkedList)) {
            checkedList = loadChildMenu(checkedList, checkedMap);
        }
        return checkedList;
    }

    private List<SysMenuEntity> loadChildMenu(List<SysMenuEntity> menuBarList, Map<String, Boolean> checkedMap) {
        if (CollectionUtils.isNotEmpty(menuBarList)) {
            List<SysMenuEntity> menuList = new ArrayList<>(15);
            for (SysMenuEntity m : menuBarList) {
                if (m.getMenuLevel() != 1 ) {
                    continue;
                }
                if (checkedMap.containsKey(m.getId().toString())) {
                    m.setActive(1);
                }
                m.setChildMenu(loadChildMenu(menuBarList, m, checkedMap));
                menuList.add(m);
            }
            return menuList;
        }
        return null;
    }

    private List<SysMenuEntity> loadChildMenu(List<SysMenuEntity> menuBarList, SysMenuEntity parentMenu, Map<String, Boolean> checkedMap) {
        if (CollectionUtils.isNotEmpty(menuBarList)) {
            List<SysMenuEntity> menuList = new ArrayList<>(10);
            for (SysMenuEntity m : menuBarList) {
                if (Objects.equals(m.getParentMenuId(), parentMenu.getId())) {
                    if (checkedMap.containsKey(m.getId().toString())) {
                        m.setActive(1);
                    }
                    menuList.add(m);
                }
            }
            return menuList;
        }
        return null;
    }

    @Override
    public List<SysMenuEntity> loadMenuAll(Long roleId, Integer isShowCode) {
        List<SysMenuEntity> checkedList =null;
        if (roleId==null){
            checkedList = baseMapper.selectList(null);
        }else{
            Map<String,Object> map=new HashMap<>();
            map.put("roleId",roleId);
            checkedList = baseMapper.selectMenuRole(map);
        }


        Map<String, Boolean> checkedMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(checkedList)) {
            for (SysMenuEntity mm : checkedList) {
                checkedMap.put(mm.getId().toString(), true);
            }
        }

        List<SysMenuEntity> list = baseMapper.selectList(new QueryWrapper<SysMenuEntity>().orderByAsc("priority"));
        if (CollectionUtils.isNotEmpty(list)) {
            return loadChildMenuNotStructure(list, checkedMap);
        }
        return null;
    }

    private List<SysMenuEntity> loadChildMenuNotStructure(List<SysMenuEntity> menuBarList, Map<String, Boolean> checkedMap) {
        if (CollectionUtils.isNotEmpty(menuBarList)) {
            List<SysMenuEntity> menuList = new ArrayList<>(15);
            for (SysMenuEntity m : menuBarList) {

                m.setOpen(false);
                if (checkedMap.containsKey(m.getId().toString())) {
                    m.setActive(1);
                    m.setChecked(true);
                }else{
                    m.setChecked(false);
                }
                m.setName(m.getMenuName()+"&nbsp;"+m.getPermission());
                menuList.add(m);
            }
            return menuList;
        }
        return null;
    }
}