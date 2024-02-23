package com.elecon.asset_mgt.AssetRequest.Service;

import com.elecon.asset_mgt.AssetRequest.DAO.CreateAssetRequestDao;
import com.elecon.asset_mgt.AssetRequest.Models.AssetRequestModel;
import com.elecon.asset_mgt.AssetRequest.Models.StatusModel;
import com.elecon.asset_mgt.AssetRequest.Repository.AssetRequestRepository;
import com.elecon.asset_mgt.AssetRequest.Repository.StatusModelRepository;
import com.elecon.asset_mgt.Category.Models.CategoryModel;
import com.elecon.asset_mgt.Category.Repository.CategoryRepo;
import com.elecon.asset_mgt.Classification.Models.ClassificationModel;
import com.elecon.asset_mgt.Classification.Repository.ClassificationRepo;
import com.elecon.asset_mgt.Employee.Models.EmployeeModel;
import com.elecon.asset_mgt.Employee.Repository.EmployeeRepo;
import com.elecon.asset_mgt.Type.Models.TypeModel;
import com.elecon.asset_mgt.Type.Repository.TypeRepo;
import com.elecon.asset_mgt.location.Models.LocationModel;
import com.elecon.asset_mgt.location.Repository.LocationRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AssetRequestService {

    @Autowired private AssetRequestRepository repo;
    @Autowired private StatusModelRepository statusrepo;
    @Autowired private ClassificationRepo classrepo;
    @Autowired private LocationRepo locationrepo;
    @Autowired private CategoryRepo catrepo;
    @Autowired private TypeRepo typerepo;
    @Autowired private EmployeeRepo employeerepo;
    public List<String> save(CreateAssetRequestDao assetreqdao,Principal principal){
        AssetRequestModel assetreqmodel = new AssetRequestModel();
        List<String> actionresponse = new ArrayList<>();
        Date currentDate = new Date(System.currentTimeMillis());

        String employeeCodejwt = principal.getName();
        System.out.println(employeeCodejwt);
        EmployeeModel employee = employeerepo.findByEmployeeCode(employeeCodejwt);

        //check if employee then pending else if manager than approved
        if(Objects.equals(employee.getRole(), "employee"))
        {
            StatusModel status = statusrepo.findByStatus("pending");
            System.out.println(status);
            assetreqmodel.setStatus(status);
            System.out.println("he is employee");
            actionresponse.add("RollBack");
        }
        if(Objects.equals(employee.getRole(), "manager"))
        {
            StatusModel status = statusrepo.findByStatus("approve");
            System.out.println(status);
            assetreqmodel.setStatus(status);
            System.out.println("he is manager");
            actionresponse.add("no action to be performed");
        }

        ClassificationModel classification = classrepo.findById(assetreqdao.getClassification_id()).orElseThrow(() -> new IllegalArgumentException("ID doesn't exist"));
        LocationModel location =  locationrepo.findById(assetreqdao.getLocation_id()).orElseThrow(() -> new IllegalArgumentException("Invalid classification ID"));
        CategoryModel category = catrepo.findById(assetreqdao.getCategory_id()).orElseThrow(() -> new IllegalArgumentException("Invalid category ID"));
        TypeModel type = typerepo.findById(assetreqdao.getAsset_type_id()).orElseThrow(() -> new IllegalArgumentException("Invalid type ID"));
        EmployeeModel reporting_to_id = employeerepo.findById(assetreqdao.getReporting_to_id()).orElseThrow(() -> new IllegalArgumentException("Invalid reporting_to ID"));

        assetreqmodel.setClassification_id(classification);
        assetreqmodel.setLocation_id(location);
        assetreqmodel.setCategory_id(category);
        assetreqmodel.setAsset_type_id(type);
        assetreqmodel.setEmployee(employee);
        assetreqmodel.setReporting_to_id(reporting_to_id);

        //set date from server side
        assetreqmodel.setUpdated_at(currentDate);
        BeanUtils.copyProperties(assetreqdao, assetreqmodel);

        repo.save(assetreqmodel);
        return actionresponse;
    }

    public void deletebyID(Integer assetreqID,Principal principal){
        // check employeeId in database with employeeId in jwt
        repo.deleteById(assetreqID);
    }
}
