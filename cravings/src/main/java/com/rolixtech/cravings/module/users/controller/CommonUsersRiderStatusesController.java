package com.rolixtech.cravings.module.users.controller;

import com.rolixtech.cravings.module.auth.model.ResponseEntityOutput;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.users.services.CommonUsersRiderStatusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin()
public class CommonUsersRiderStatusesController {

    public static final String CONTROLLER_URL = GenericUtility.APPLICATION_CONTEXT;

    @Autowired
    private CommonUsersRiderStatusesService commonUsersRiderStatusesService;

    @Autowired
    private GenericUtility utility;

    @GetMapping(CONTROLLER_URL+"/admin/rider-status/drop-down")
    public ResponseEntity<?>riderStatusDropDownView(@RequestHeader("authorization") String token){
        long UserId=utility.getUserIDByToken(token);
        ResponseEntityOutput response=new ResponseEntityOutput();
        Map map=new HashMap<>();

        try {
            response.CODE="1";
            response.USER_MESSAGE="Success";
            response.SYSTEM_MESSAGE="";
            response.DATA= commonUsersRiderStatusesService.getAllStatuses();

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            response.CODE="2";
            response.USER_MESSAGE="Error";
            response.SYSTEM_MESSAGE=e.toString();

        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value=CONTROLLER_URL+"/admin/rider/change-status")
    public ResponseEntity<?> selectRiderName(long orderId,@RequestHeader("authorization") String token, String riderName, Long riderStatus, Long riderId){
        long UserId=utility.getUserIDByToken(token);
        ResponseEntityOutput response=new ResponseEntityOutput();
        Map map=new HashMap<>();

        try {
            response.CODE="1";
            response.USER_MESSAGE="Updated";
            response.SYSTEM_MESSAGE="";
            commonUsersRiderStatusesService.selectRiderName(orderId, UserId, riderName, riderStatus,riderId);

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            response.CODE="2";
            response.USER_MESSAGE="Error";
            response.SYSTEM_MESSAGE=e.toString();

        }
        return ResponseEntity.ok(response);
    }


    @GetMapping(CONTROLLER_URL+"/admin/rider-name/drop-down")
    public ResponseEntity<?>riderNameDropDownView(@RequestHeader("authorization") String token){
        long userId=utility.getUserIDByToken(token);
        ResponseEntityOutput response=new ResponseEntityOutput();
        Map map=new HashMap<>();

        try {
            response.CODE="1";
            response.USER_MESSAGE="Success";
            response.SYSTEM_MESSAGE="";
            response.DATA= commonUsersRiderStatusesService.getAllNames(userId);

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            response.CODE="2";
            response.USER_MESSAGE="Error";
            response.SYSTEM_MESSAGE=e.toString();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value=CONTROLLER_URL+"/rider-assigned-by" )
    public ResponseEntity<?> addComments(long recordId,@RequestHeader("authorization") String token, String riderName)  {
        long userId=utility.getUserIDByToken(token);
        ResponseEntityOutput response=new ResponseEntityOutput();
        Map map=new HashMap<>();

        try {

            response.CODE="1";
            response.USER_MESSAGE="Updated";
            response.SYSTEM_MESSAGE="";
            commonUsersRiderStatusesService.riderAssignedBy(recordId,userId,riderName);

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            response.CODE="2";
            response.USER_MESSAGE="Error";
            response.SYSTEM_MESSAGE=e.toString();

        }


        return ResponseEntity.ok(response);
    }


}
