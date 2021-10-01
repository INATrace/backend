//package com.abelium.inatrace.components.stockorder.mappers;
//
//import com.abelium.inatrace.components.stockorder.api.ApiScoreImpact;
//import com.abelium.inatrace.db.entities.stockorder.ScoreImpact;
//
//public class ScoreImpactMapper {
//
//    public static ApiScoreImpact toApiScoreImpact(ScoreImpact entity){
//        if (entity == null) return null;
//        ApiScoreImpact apiScoreImpact = new ApiScoreImpact();
//        apiScoreImpact.setId(entity.getId());
//        apiScoreImpact.setScore(entity.getScore());
//        apiScoreImpact.setType(entity.getType());
//        return apiScoreImpact;
//    }
//}
