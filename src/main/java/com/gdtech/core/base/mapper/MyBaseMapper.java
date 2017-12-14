package com.gdtech.core.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础mapper
 * @param <T>
 */
public interface MyBaseMapper<T>  extends Mapper<T>, MySqlMapper<T> {
}
