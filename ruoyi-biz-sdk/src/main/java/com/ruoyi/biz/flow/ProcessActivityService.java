package com.ruoyi.biz.flow;

import com.ruoyi.flowable.utils.FindNextNodeUtil;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.engine.RepositoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

/**
 * <p> 流程环节服务 </p>
 *
 * @Author wocurr.com
 */
@Service
public class ProcessActivityService {

    @Resource
    protected RepositoryService repositoryService;

    /**
     * 判断当前流程流转是否结束（如果是子流程，则未结束）
     *
     * @param procDefId 流程定义ID
     * @param activityId 活动节点ID
     * @return
     */
    public Boolean isFlowFinished(String procDefId, String activityId, Map<String, Object> variables) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
        FlowElement flowElement = bpmnModel.getFlowElement(activityId);
        return FindNextNodeUtil.checkNextNodeIsFinished(flowElements, flowElement, variables);
    }
}
