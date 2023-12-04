package com.xwhking.yuapi.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.xwhking.yuapi.annotation.AuthCheck;
import com.xwhking.yuapi.common.BaseResponse;
import com.xwhking.yuapi.common.DeleteRequest;
import com.xwhking.yuapi.common.ErrorCode;
import com.xwhking.yuapi.common.ResultUtils;
import com.xwhking.yuapi.constant.UserConstant;
import com.xwhking.yuapi.exception.BusinessException;
import com.xwhking.yuapi.exception.ThrowUtils;
import com.xwhking.yuapi.model.dto.IdRequest;
import com.xwhking.yuapi.model.dto.post.PostAddRequest;
import com.xwhking.yuapi.model.dto.post.PostEditRequest;
import com.xwhking.yuapi.model.dto.post.PostQueryRequest;
import com.xwhking.yuapi.model.dto.post.PostUpdateRequest;
import com.xwhking.yuapi.model.dto.userinterfaceinfo.UserInterfaceInfoAddRequest;
import com.xwhking.yuapi.model.dto.userinterfaceinfo.UserInterfaceInfoQueryRequest;
import com.xwhking.yuapi.model.dto.userinterfaceinfo.UserInterfaceInfoUpdateRequest;
import com.xwhking.yuapi.model.entity.Post;
import com.xwhking.yuapi.model.entity.User;
import com.xwhking.yuapi.model.entity.UserInterfaceInfo;
import com.xwhking.yuapi.model.vo.PostVO;
import com.xwhking.yuapi.service.PostService;
import com.xwhking.yuapi.service.UserInterfaceInfoService;
import com.xwhking.yuapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口信息接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
@RequestMapping("/invoke")
@Slf4j
public class UserInterfaceInfoController {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();


    /**
     * 创建
     *
     * @param userInterfaceInfoAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Long> addPost(@RequestBody UserInterfaceInfoAddRequest userInterfaceInfoAddRequest, HttpServletRequest request) {
        if (userInterfaceInfoAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfoAddRequest, userInterfaceInfo);
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo);
        boolean result = userInterfaceInfoService.save(userInterfaceInfo);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newPostId = userInterfaceInfo.getId();
        return ResultUtils.success(newPostId);
    }

    /**
     * 删除
     *
     * @param idRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<Boolean> deletePost(@RequestBody IdRequest idRequest, HttpServletRequest request) {
        if (idRequest == null || idRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = idRequest.getId();
        // 判断是否存在
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(userInterfaceInfo == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!userInterfaceInfo.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = userInterfaceInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param userInterfaceInfoUpdateRequest
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updatePost(@RequestBody UserInterfaceInfoUpdateRequest userInterfaceInfoUpdateRequest) {
        if (userInterfaceInfoUpdateRequest == null || userInterfaceInfoUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        BeanUtils.copyProperties(userInterfaceInfo, userInterfaceInfoUpdateRequest);
        // 参数校验
        userInterfaceInfoService.validUserInterfaceInfo(userInterfaceInfo);
        long id = userInterfaceInfoUpdateRequest.getId();
        // 判断是否存在
        UserInterfaceInfo userInterfaceInfo1 = userInterfaceInfoService.getById(id);
        ThrowUtils.throwIf(userInterfaceInfo1 == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = userInterfaceInfoService.updateById(userInterfaceInfo);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public BaseResponse<UserInterfaceInfo> getPostVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        UserInterfaceInfo userInterfaceInfo = userInterfaceInfoService.getById(id);
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return ResultUtils.success(userInterfaceInfo);
    }

}
