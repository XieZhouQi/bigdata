package com.technology.flink.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @description: 用户行为
 * @author: xzq
 * @date: 2021/2/10
 * @version:
 */
@Data
@Builder
public class UserBehaviorDto {

    /***
     * 用户id
     */
    private String userId;

    /***
     * 商品id
     */
    private String itemId;

    /***
     * 商品品类id
     */
    private String categoryId;

    /***
     * 用户行为（pv, buy、cart、fav）
     */
    private String behavior;

    /***
     * 时间戳
     */
    private String timestamp;

}
