package com.ruoyi.kbs.handle;

import com.ruoyi.common.enums.WhetherStatus;
import com.ruoyi.kbs.domain.KbsDocumentBase;
import com.ruoyi.kbs.domain.KbsRecycle;
import com.ruoyi.kbs.domain.KbsTopicInfo;
import com.ruoyi.kbs.enums.ObjectTypeEnum;
import com.ruoyi.kbs.exception.KbsHandleException;
import com.ruoyi.kbs.service.IKbsDocumentBaseService;
import com.ruoyi.kbs.service.IKbsDocumentInfoService;
import com.ruoyi.kbs.service.IKbsTopicAuthUserService;
import com.ruoyi.kbs.service.IKbsTopicInfoService;
import com.ruoyi.kbs.utils.TransferUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> 回收处理类 </p>
 *
 * @Author wocurr.com
 */
@Slf4j
@Component
public class RecycleHandleService {

    @Autowired
    private IKbsTopicInfoService kbsTopicInfoService;
    @Autowired
    private IKbsTopicAuthUserService kbsTopicAuthUserService;
    @Autowired
    private IKbsDocumentBaseService kbsDocumentBaseService;
    @Autowired
    private IKbsDocumentInfoService kbsDocumentInfoService;

    /**
     * 恢复
     *
     * @param recycles 回收内容
     */
    public void recover(List<KbsRecycle> recycles) {
        if (CollectionUtils.isEmpty(recycles)) {
            return;
        }

        List<String> objectIds = recycles.stream()
                .map(KbsRecycle::getObjectId)
                .collect(Collectors.toList());
        try {
            Map<String, List<KbsTopicInfo>> topicInfoListMap = getTopicInfoListMap(objectIds);
            Map<String, List<KbsDocumentBase>> documentBaseListMap = getDocumentBaseListMap(objectIds);

            List<KbsTopicInfo> updateTopicInfos = new ArrayList<>();
            List<KbsDocumentBase> updateDocumentBases = new ArrayList<>();

            for (KbsRecycle recycle : recycles) {
                ObjectTypeEnum recycleTypeEnum = ObjectTypeEnum.getByCode(recycle.getObjectType());
                if (recycleTypeEnum == null) {
                    continue;
                }

                switch (recycleTypeEnum) {
                    case TOPIC:
                        List<KbsTopicInfo> topicInfos = topicInfoListMap.get(recycle.getObjectId());
                        if (CollectionUtils.isNotEmpty(topicInfos)) {
                            updateTopicInfos.addAll(topicInfos);
                        }
                        break;
                    case DOCUMENT:
                        List<KbsDocumentBase> documentBasesList = documentBaseListMap.get(recycle.getObjectId());
                        if (CollectionUtils.isNotEmpty(documentBasesList)) {
                            updateDocumentBases.addAll(documentBasesList);
                        }
                        break;
                    default:
                        break;
                }
            }

            if (CollectionUtils.isNotEmpty(updateTopicInfos)) {
                kbsTopicInfoService.updateBatch(updateTopicInfos, WhetherStatus.NO.getCode());
            }

            if (CollectionUtils.isNotEmpty(updateDocumentBases)) {
                kbsDocumentBaseService.updateBatch(updateDocumentBases, WhetherStatus.NO.getCode());
            }
        } catch (Exception e) {
            log.error("恢复回收内容时发生异常, 回收对象ID集合：{}", objectIds, e);
            throw new KbsHandleException("恢复回收内容时发生异常");
        }
    }


    /**
     * 彻底删除
     *
     * @param recycles 回收内容
     */
    public void completelyDelete(List<KbsRecycle> recycles) {
        if (CollectionUtils.isEmpty(recycles)) {
            return;
        }

        // 使用 groupingBy 一次性分组
        Map<String, List<String>> groupedIds = recycles.stream()
                .collect(Collectors.groupingBy(
                        KbsRecycle::getObjectType,
                        Collectors.mapping(KbsRecycle::getObjectId, Collectors.toList())
                ));

        List<String> topicIds = groupedIds.get(ObjectTypeEnum.TOPIC.getCode());
        List<String> documentIds = groupedIds.get(ObjectTypeEnum.DOCUMENT.getCode());
        try {
            // 处理 TOPIC 类型
            if (CollectionUtils.isNotEmpty(topicIds)) {
                String[] objectIdsArray = TransferUtil.listToArray(topicIds);
                kbsTopicInfoService.deleteKbsTopicInfoByIds(objectIdsArray);
                kbsTopicAuthUserService.deleteByTopicIds(objectIdsArray);
            }

            // 处理 DOCUMENT 类型
            if (CollectionUtils.isNotEmpty(documentIds)) {
                String[] objectIdsArray = TransferUtil.listToArray(documentIds);
                kbsDocumentBaseService.deleteKbsDocumentBaseByIds(objectIdsArray);
                kbsDocumentInfoService.deleteKbsDocumentInfoByDocIds(objectIdsArray);
            }

            // 可选：处理未知类型
            groupedIds.forEach((type, ids) -> {
                if (!ObjectTypeEnum.TOPIC.getCode().equals(type) && !ObjectTypeEnum.DOCUMENT.getCode().equals(type)) {
                    // 记录日志或抛出警告
                    log.warn("发现未处理的 objectType: {}, 数量: {}", type, ids.size());
                }
            });

        } catch (Exception e) {
            // 记录异常日志
            log.error("删除回收站内容时发生异常, 回收主题对象ID集合：{}, 回收文档对象ID集合：{}", topicIds, documentIds, e);
            throw new KbsHandleException("删除回收站内容时发生异常"); // 或者根据业务决定是否继续抛出
        }
    }

    /**
     * 批量获取主题信息
     *
     * @param objectIds 主题ID
     */
    private Map<String, List<KbsTopicInfo>> getTopicInfoListMap(List<String> objectIds) {
        List<KbsTopicInfo> kbsTopicInfos = kbsTopicInfoService.listKbsTopicInfoByIds(objectIds);
        return kbsTopicInfos.stream()
                .collect(Collectors.groupingBy(KbsTopicInfo::getId));
    }

    /**
     * 批量获取文档信息
     *
     * @param objectIds
     * @return
     */
    private Map<String, List<KbsDocumentBase>> getDocumentBaseListMap(List<String> objectIds) {
        List<KbsDocumentBase> documentBases = kbsDocumentBaseService.listKbsDocumentBaseByIds(objectIds);
        return documentBases.stream()
                .collect(Collectors.groupingBy(KbsDocumentBase::getId));
    }
}
