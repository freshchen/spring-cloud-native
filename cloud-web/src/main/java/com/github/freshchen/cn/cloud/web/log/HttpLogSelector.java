package com.github.freshchen.cn.cloud.web.log;

/**
 * @author freshchen
 */
public interface HttpLogSelector {

    /**
     * 默认不记录 <b>Header</b>, 可以通过实现该接口实现指定header的记录
     * <pre>
     *  {@literal @}Component
     *  public class AllLogSelector implements LogSelector {
     *
     *      // 记录所有header
     *      {@literal @}Override
     *      public boolean selectHeader(String headerName) {
     *          return true;
     *      }
     *  }
     * </pre>
     *
     * @param headerName 请求头
     * @return true=记录  false=不记录
     */
    default boolean selectHeader(String headerName) {
        return false;
    }
}
