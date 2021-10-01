//package com.abelium.inatrace.components.stockorder.mappers;
//
//import com.abelium.inatrace.components.stockorder.api.ApiScoreTarget;
//import com.abelium.inatrace.db.entities.stockorder.ScoreTarget;
//
//public class ScoreTargetMapper {
//
//    public static ApiScoreTarget toApiScoreTarget(ScoreTarget entity) {
//        if(entity == null) return null;
//        ApiScoreTarget apiScoreTarget = new ApiScoreTarget();
//        apiScoreTarget.setId(entity.getId());
//        apiScoreTarget.setFairness(entity.getFairness());
//        apiScoreTarget.setProvenance(entity.getProvenance());
//        apiScoreTarget.setQuality(entity.getQuality());
//        apiScoreTarget.setQualityLevel(entity.getQualityLevel());
//        apiScoreTarget.setWomenShare(entity.getWomenShare());
//        apiScoreTarget.setOrder(entity.getOrderId());
//        apiScoreTarget.setPayment(entity.getPayment());
//        return apiScoreTarget;
//    }
//}
