package com.mall.dao;

import com.mall.po.DailyArrival;
import com.mall.po.DailyArrivalPager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DailyArrivalDao {
    /**
     * 获取每日到货计划分页数据
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param date 日期筛选
     * @param supplier 供应商筛选
     * @return 每日到货计划分页对象
     */
    DailyArrivalPager getDailyArrivalPager(int offset, int pageSize, String date, String supplier);

    /**
     * 获取每日到货计划总数
     * @param date 日期筛选
     * @param supplier 供应商筛选
     * @return 记录总数
     */
    int getDailyArrivalCount(Connection conn, String date, String supplier) throws SQLException;

    /**
     * 获取每日到货计划列表
     * @param offset 起始记录索引
     * @param pageSize 每页记录数
     * @param date 日期筛选
     * @param supplier 供应商筛选
     * @return 每日到货计划列表
     */
    List<DailyArrival> getDailyArrivalList(Connection conn, int offset, int pageSize, String date, String supplier) throws SQLException;


}