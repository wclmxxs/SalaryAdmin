package cwall.club.userapi.Controller;

import cwall.club.common.Annotation.SalaryPermission;
import cwall.club.common.DTO.GoodsDTO;
import cwall.club.common.Item.ApiResult;
import cwall.club.common.VO.CompanyVO;
import cwall.club.common.VO.GoodsVO;
import cwall.club.core.Service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
@Api(tags = "商品接口")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping("/create")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "创建商品", notes = "创建商品", httpMethod = "POST", response = GoodsVO.class)
    public ApiResult create(@RequestBody GoodsDTO goodsDTO,Long cid){
        GoodsVO goods = goodsService.createGoods(goodsDTO, cid);
        return ApiResult.renderSuccess(goods);
    }

    @PostMapping("/delete")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "删除商品", notes = "删除商品", httpMethod = "POST", response = Boolean.class)
    public ApiResult delete(Long goodsId,Long cid){
        return ApiResult.renderSuccess(goodsService.deleteGoods(goodsId, cid));
    }

    @PostMapping("/update")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.ADMIN)
    @ApiOperation(value = "更新商品", notes = "更新商品", httpMethod = "POST", response = GoodsVO.class)
    public ApiResult update(@RequestBody GoodsDTO goodsDTO, Long cid){
        GoodsVO goods = goodsService.updateGoods(goodsDTO, cid);
        return ApiResult.renderSuccess(goods);
    }

    @PostMapping("/get")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "获取商品", notes = "获取商品", httpMethod = "POST", response = GoodsVO.class)
    public ApiResult get(Long goodsId,Long cid){
        GoodsVO goods = goodsService.findGoods(goodsId, cid);
        return ApiResult.renderSuccess(goods);
    }

    @PostMapping("/getAll")
    @ResponseBody
    @SalaryPermission(SalaryPermission.FunctionPermission.STAFF)
    @ApiOperation(value = "获取商品", notes = "获取商品", httpMethod = "POST", response = GoodsVO.class)
    public ApiResult getAll(Long cid){
        List<GoodsVO> goods = goodsService.findAllGoods(cid);
        return ApiResult.renderSuccess(goods);
    }

}
