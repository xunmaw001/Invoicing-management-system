package com.service.impl;

import com.utils.StringUtil;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.*;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import com.utils.PageUtils;
import com.utils.Query;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;

import com.dao.PandianOrderListDao;
import com.entity.PandianOrderListEntity;
import com.service.PandianOrderListService;
import com.entity.view.PandianOrderListView;

/**
 * 盘点详情 服务实现类
 */
@Service("pandianOrderListService")
@Transactional
public class PandianOrderListServiceImpl extends ServiceImpl<PandianOrderListDao, PandianOrderListEntity> implements PandianOrderListService {

    @Override
    public PageUtils queryPage(Map<String,Object> params) {
        if(params != null && (params.get("limit") == null || params.get("page") == null)){
            params.put("page","1");
            params.put("limit","10");
        }
        Page<PandianOrderListView> page =new Query<PandianOrderListView>(params).getPage();
        page.setRecords(baseMapper.selectListView(page,params));
        return new PageUtils(page);
    }


}
