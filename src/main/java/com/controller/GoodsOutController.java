package com.controller;


import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.entity.GoodsOutEntity;

import com.service.GoodsOutService;
import com.entity.view.GoodsOutView;
import com.service.GoodsService;
import com.entity.GoodsEntity;
import com.service.HuiyuanService;
import com.entity.HuiyuanEntity;
import com.service.YuangongService;
import com.entity.YuangongEntity;
import com.utils.PageUtils;
import com.utils.R;

/**
 * 销售
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/goodsOut")
public class GoodsOutController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsOutController.class);

    @Autowired
    private GoodsOutService goodsOutService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;



    //级联表service
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private HuiyuanService huiyuanService;
    @Autowired
    private YuangongService yuangongService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("员工".equals(role))
            params.put("yuangongId",request.getSession().getAttribute("userId"));
        params.put("orderBy","id");
        PageUtils page = goodsOutService.queryPage(params);

        //字典表数据转换
        List<GoodsOutView> list =(List<GoodsOutView>)page.getList();
        for(GoodsOutView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        GoodsOutEntity goodsOut = goodsOutService.selectById(id);
        if(goodsOut !=null){
            //entity转view
            GoodsOutView view = new GoodsOutView();
            BeanUtils.copyProperties( goodsOut , view );//把实体数据重构到view中

            //级联表
            GoodsEntity goods = goodsService.selectById(goodsOut.getGoodsId());
            if(goods != null){
                BeanUtils.copyProperties( goods , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setGoodsId(goods.getId());
            }
            //级联表
            HuiyuanEntity huiyuan = huiyuanService.selectById(goodsOut.getHuiyuanId());
            if(huiyuan != null){
                BeanUtils.copyProperties( huiyuan , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setHuiyuanId(huiyuan.getId());
            }
            //级联表
            YuangongEntity yuangong = yuangongService.selectById(goodsOut.getYuangongId());
            if(yuangong != null){
                BeanUtils.copyProperties( yuangong , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                view.setYuangongId(yuangong.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody GoodsOutEntity goodsOut, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,goodsOut:{}",this.getClass().getName(),goodsOut.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("员工".equals(role))
            goodsOut.setYuangongId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        GoodsEntity goodsEntity = goodsService.selectById(goodsOut.getGoodsId());
        if(goodsEntity == null || goodsEntity.getGoodsNumber() == null){
            return R.error(511,"商品为空或者商品数量为空");
        }
        Integer balance = goodsEntity.getGoodsNumber() - goodsOut.getGoodsOutNumber();
        if(balance<0){
            return R.error(511,"出库数量大于库存数量");
        }
        goodsEntity.setGoodsNumber(balance);
        goodsService.updateById(goodsEntity);

        goodsOut.setInsertTime(new Date());
        goodsOut.setCreateTime(new Date());
        goodsOutService.insert(goodsOut);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody GoodsOutEntity goodsOut, HttpServletRequest request){
        logger.debug("update方法:,,Controller:{},,goodsOut:{}",this.getClass().getName(),goodsOut.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(StringUtil.isEmpty(role))
            return R.error(511,"权限为空");
        else if("员工".equals(role))
            goodsOut.setYuangongId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        GoodsOutEntity oldGoodsOutEntity = goodsOutService.selectById(goodsOut.getId());
        GoodsEntity oldGoodsEntity = goodsService.selectById(oldGoodsOutEntity.getGoodsId());
        if(oldGoodsEntity == null || oldGoodsEntity.getGoodsNumber() == null)
            return R.error(511,"原先商品为空或者原先商品数量为空");
        oldGoodsEntity.setGoodsNumber(oldGoodsEntity.getGoodsNumber() + oldGoodsOutEntity.getGoodsOutNumber());
        goodsService.updateById(oldGoodsEntity);

        GoodsEntity newGoodsEntity = goodsService.selectById(goodsOut.getGoodsId());
        if(newGoodsEntity == null || newGoodsEntity.getGoodsNumber() == null)
            return R.error(511,"现在商品为空或者现在商品数量为空");
        Integer balance = newGoodsEntity.getGoodsNumber() - goodsOut.getGoodsOutNumber();
        if(balance<0)
            return R.error(511,"出库数量不能大于库存数量");
        newGoodsEntity.setGoodsNumber(balance);


        goodsService.updateById(newGoodsEntity);
        goodsOutService.updateById(goodsOut);//根据id更新
        return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        goodsOutService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }






}

