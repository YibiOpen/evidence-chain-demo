package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 * 分页信息
 * @author yibi
 * @date 2021-06-24 12:14
 */
@Data
public class EviPage {
    /**每页显示数，默认10*/
    private Integer pageSize = 10;
    /**当前页，默认1*/
    private Integer currentPage = 1;
}
