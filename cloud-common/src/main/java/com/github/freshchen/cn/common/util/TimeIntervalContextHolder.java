package com.github.freshchen.cn.common.util;

import cn.hutool.core.date.TimeInterval;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


/**
 * @author freshchen
 * @since 2021/11/18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeIntervalContextHolder {

    /**
     * @see TimeInterval 基于hutool-core
     * 耗时统计不应该跨线程因此使用 ThreadLocal
     */
    private static final ThreadLocal<TimeInterval> TIME_INTERVAL_TL = ThreadLocal.withInitial(TimeInterval::new);
    /**
     * 记录所有耗时检查结果
     */
    private static final ThreadLocal<Map<String, Long>> COST_TL = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public static TimeInterval get() {
        return TIME_INTERVAL_TL.get();
    }

    public static Map<String, Long> getCostMap() {
        return COST_TL.get();
    }

    /**
     * 一定要记得 stop 对应 id, 否则不会统计，响应日志中使用默认 id=req
     */
    public static void start(String id) {
        TimeInterval timeInterval = get();
        timeInterval.start(id);
    }

    /**
     * 避免冲突，id自增1
     */
    public static void stop(String id) {
        TimeInterval timeInterval = get();
        long cost = timeInterval.intervalMs(id);
        Map<String, Long> costMap = COST_TL.get();
        while (costMap.containsKey(id)) {
            id = id + 1;
        }
        costMap.put(id, cost);
    }

    public static String costString() {
        Map<String, Long> costMap = COST_TL.get();
        return costMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(e -> e.getKey() + ":" + e.getValue() + "ms").collect(Collectors.joining(","));
    }

    public static void clear() {
        TIME_INTERVAL_TL.remove();
        COST_TL.remove();
    }
}
