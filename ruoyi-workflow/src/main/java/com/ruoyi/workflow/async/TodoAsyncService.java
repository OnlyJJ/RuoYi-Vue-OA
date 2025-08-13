package com.ruoyi.workflow.async;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.im.chat.enums.BusinessMessageType;
import com.ruoyi.message.service.IBusinessSystemMessageService;
import com.ruoyi.mq.domain.AsyncLog;
import com.ruoyi.mq.execute.IAsyncHandler;
import com.ruoyi.todo.domain.Todo;
import com.ruoyi.todo.domain.vo.TodoVO;
import com.ruoyi.todo.mapper.TodoSourceTargetMapper;
import com.ruoyi.todo.service.ITodoService;
import com.ruoyi.workflow.exception.TodoAsyncException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.flowable.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

/**
 * <p> 新启待办异步处理服务 </p>
 *
 * @Author wocurr.com
 */
@Slf4j
@Service
public class TodoAsyncService implements IAsyncHandler {

    @Autowired
    private ITodoService todoService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IBusinessSystemMessageService systemMessageService;

    private static final String MSG_CONTENT = "您的待办任务已更新！";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void doAsync(AsyncLog asyncLog) {
        log.info("收到消息，消息内容：{}", asyncLog);
        String msg = asyncLog.getMessageContent();
        Assert.hasText(msg, "消息内容为空");
        try {
            TodoVO todoVO = JSONObject.parseObject(msg, TodoVO.class);
            Todo todo = TodoSourceTargetMapper.INSTANCE.todoVo2Todo(todoVO);
            todoService.saveAndUpdateTodo(todo);
            runtimeService.updateBusinessKey(todo.getProcInstId(), todo.getBusinessId());
            setFormDataVariable(todoVO);
            systemMessageService.sendBusinessSystemMessage(BusinessMessageType.TODO_LIST_REFRESH.getCode(), null, Collections.singletonList(todo.getCreateId()), MSG_CONTENT);
        } catch (Exception e) {
            log.error("异步处理待办失败，原因：", e);
            throw new TodoAsyncException("异步处理待办失败:" + e.getMessage());
        }
    }

    /**
     * 设置表单数据变量
     *
     * @param todoVO 待办对象
     */
    private void setFormDataVariable(TodoVO todoVO) {
        Map<String, Object> valData = todoVO.getValData();
        if (MapUtils.isEmpty(valData)) {
            return;
        }
        valData.replaceAll((key, value) -> {
            if (value instanceof BigDecimal) {
                return ((BigDecimal) value).doubleValue();
            }
            if (value instanceof JSONArray) {
                JSONArray array = (JSONArray) value;
                if (array.isEmpty()) {
                    return StringUtils.EMPTY;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < array.size(); i++) {
                    if (i > 0) {
                        sb.append(Constants.COMMA);
                    }
                    sb.append(array.get(i) == null ? StringUtils.EMPTY : array.get(i).toString());
                }
                return sb.toString();
            }
            return value;
        });
        runtimeService.setVariables(todoVO.getProcInstId(), valData);
    }
}
