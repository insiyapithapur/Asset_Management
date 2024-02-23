package com.elecon.asset_mgt.AssetRequest.Service;

import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import com.elecon.asset_mgt.AssetRequest.Repository.StatusModelRepository;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetRequestService {
    @Autowired
    private StatusModelRepository statusrepo;
}
