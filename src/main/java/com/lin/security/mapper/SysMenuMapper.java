package com.lin.security.mapper;

import com.lin.security.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author fengsulin
 * @since 2022-08-14
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户id查询菜单参数
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(long userId);
}
