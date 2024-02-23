package com.elecon.asset_mgt.AssetRequest.Controllers;

import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import com.elecon.asset_mgt.AssetRequest.Service.AssetRequestService;
import com.elecon.asset_mgt.Exceptions.ForeignKeyViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/AssetRequest/")
public class AssetController {
    @Autowired
    private AssetRequestService assetreqservice;

}
