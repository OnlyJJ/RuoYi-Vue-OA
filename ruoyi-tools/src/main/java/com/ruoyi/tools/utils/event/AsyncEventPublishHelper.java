package com.ruoyi.tools.utils.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p> 异步事件发布管理器 </p>
 *
 * @Author wocurr.com
 */
@Component
@RequiredArgsConstructor
public class AsyncEventPublishHelper {

    @Resource
    private ApplicationEventPublisher publisher;

    /**
     * 发布事件
     *
     * @param event
     */
    public void publish(ApplicationEvent event){
        publisher.publishEvent(event);
    }
}
