package com.rolixtech.cravings.module.users.services;

import com.opencsv.CSVParser;
import com.opencsv.exceptions.CsvValidationException;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.order.dao.CustomerOrderChangeLogDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderRemarksDao;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.model.CustomerOrderChangeLog;
import com.rolixtech.cravings.module.order.model.CustomerOrderRemarks;
import com.rolixtech.cravings.module.order.model.CustomerOrderStatus;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.dao.CommonUsersRiderStatusesDao;
import com.rolixtech.cravings.module.users.dao.RiderAssignDetailsDao;
import com.rolixtech.cravings.module.users.models.CommonUsers;
import com.rolixtech.cravings.module.users.models.CommonUsersRiderStatuses;
import com.rolixtech.cravings.module.users.models.RiderAssignDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
public class CommonUsersRiderStatusesService {
    @Autowired
    private CommonUsersRiderStatusesDao commonUsersRiderStatusesDao;

    @Autowired
    private CustomerOrderDao customerOrderDao;

    @Autowired
    private GenericUtility utility;

    @Autowired
    private CustomerOrderChangeLogDao customerOrderChangeLogDao;

    @Autowired
    private CustomerOrderRemarksDao orderRemarksDao;

    @Autowired
    RiderAssignDetailsDao riderAssignDetailsDao;

    @Autowired
    private CommonUsersDao commonUsersDao;

    public List<Map> getAllStatuses(){
        List<Map> list=new ArrayList<>();
        List<CommonUsersRiderStatuses> statuses=commonUsersRiderStatusesDao.findAll();
        if(!statuses.isEmpty()) {
            statuses.stream().forEach(
                    status->{
                        Map Row=new HashMap<>();
                        Row.put("id", status.getId());
                        Row.put("riderStatus", status.getStatusStringValue());

                        list.add(Row);
                    }

            );
        }
        System.out.println("");
        return list;

    }

    public List<Map> getAllNames(Long userId) throws CsvValidationException, IOException {
        List<Map> list = new ArrayList<>();
        List<String> statuses = commonUsersRiderStatusesDao.findCommonUsersRiderNameByUserRoleId(4);
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> conatctNumber = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

        if (!statuses.isEmpty()) {

            for (String value : statuses) {
                String[] splitValues = new String[0];
                try {
                    splitValues = new CSVReaderBuilder(new StringReader(value)).withCSVParser(parser).build().readNext();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (CsvValidationException e) {
                    throw new RuntimeException(e);
                }
                ids.add(splitValues[0]);
                names.add(splitValues[1]);
                conatctNumber.add(splitValues[2]);
            }

        }
        CustomerOrderRemarks orderRemarks=new CustomerOrderRemarks();
        orderRemarks.setRemarksAddedBy(userId);
        for (int i = 0; i < ids.size(); i++) {
            Map Row = new HashMap<>();
            Row.put("id", ids.get(i));
            Row.put("riderName", names.get(i));
            Row.put("contactNumber1",conatctNumber.get(i));
//          Row.put("RiderStatusChangedBy", orderRemarks.getRemarksAddedBy());
            list.add(Row);
        }

        return list;
    }

    public void selectRiderName(long orderId, long userId, String riderName, Long riderStatus, Long riderId){
        CustomerOrder order=customerOrderDao.findById(orderId);

        if (order != null){
            CustomerOrderChangeLog orderChangeLog=new CustomerOrderChangeLog();
            CustomerOrder iRealChange=customerOrderDao.findById(orderId);
            BeanUtils.copyProperties(iRealChange, orderChangeLog);

            orderChangeLog.setId(0);
            orderChangeLog.setRecordId(orderId);
            orderChangeLog.setLogCreatedBy(userId);
            orderChangeLog.setLogCreatedOn(new Date());
            orderChangeLog.setLogReason("Rider name assigned");
            orderChangeLog.setLogTypeId(utility.parseLong("1"));
            orderChangeLog.setRiderStatus("Assigned");

            if(riderStatus == 1){ // check missing if status value is not comming
                order.setRiderStatus("Assigned");
            }else {
                order.setRiderStatus("Unassigned");
            }
            order.setRiderName(riderName);
            String riderContactNumber = commonUsersDao.findMobileNumberById(riderId);
            order.setRiderContactNumber(riderContactNumber);
//            order.setRiderContactNumber(commonUsersDao.findRiderContactNumberByOrderId(orderId));
            customerOrderDao.save(order);
            customerOrderChangeLogDao.save(orderChangeLog);
        }

    }

    public void riderAssignedBy(long recordId,long userId, String riderName) {
        RiderAssignDetails riderAssignDetails=new RiderAssignDetails();

        riderAssignDetails.setOrderId(recordId);
        riderAssignDetails.setRemarksAddedOn(new Date());
        riderAssignDetails.setRemarksAddedBy(userId);
        riderAssignDetails.setRiderName(riderName);
        riderAssignDetailsDao.save(riderAssignDetails);
    }



}
