package com.elecon.asset_mgt.AssetRequest.Controllers;

import com.elecon.asset_mgt.AssetRequest.DAO.CreateAssetRequestDao;
import com.elecon.asset_mgt.AssetRequest.Models.AssetRequestModel;
import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import com.elecon.asset_mgt.AssetRequest.Service.AssetRequestService;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Exceptions.ForeignKeyViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/AssetRequest/")
public class AssetController {
    @Autowired
    private AssetRequestService assetreqservice;

    @PostMapping("createAssetRequest/")
    public ResponseEntity<?> createAssetRequest(@RequestBody CreateAssetRequestDao assetreqdao , Principal principal) {
        System.out.println("in create");
        try {
            System.out.println("in create asset");
            List<String> actions = assetreqservice.save(assetreqdao,principal);
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "New asset request is created successfully!");
            successResponse.put("serviceResponse", actions);
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (Exception e) {
            return handleException(e);
        }
    }

//    stage:
//            1) pullback, cancel -- comment
//            2) manager approve, reject -- comment
//            3) allocation approve, reject -- comment
//            4) handover -- comment

    //can be cancelled api for employee or manager whoever is requesting
    @DeleteMapping("{assetreqID}")
    public ResponseEntity<?> deleteAssetRequest(@PathVariable Integer assetreqID,Principal principal){
        System.out.println("in delete");
        try {
            assetreqservice.deletebyID(assetreqID,principal) ;
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("status", true);
            successResponse.put("message", "Delete asset request  successfully!");
            return ResponseEntity.status(HttpStatus.OK).body(successResponse);
        } catch (Exception e) {
             return handleException(e);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(@NotNull Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", false);
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
