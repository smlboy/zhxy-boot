package com.example.zhxy.mapper;

import com.example.zhxy.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yl
* @description 针对表【tb_admin】的数据库操作Mapper
* @createDate 2022-09-01 18:03:57
* @Entity com.example.zhxy.entity.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}




