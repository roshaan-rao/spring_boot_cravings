package com.rolixtech.cravings.module.order.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.order.dao.CustomerOrderDao;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.services.CommonUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.rolixtech.cravings.module.order.dao.CustomerOrderStatusDao;
import com.rolixtech.cravings.module.order.model.CustomerOrderStatus;
@Service
public class CustomerOrderStatusService {
	@Autowired
	private CustomerOrderStatusDao OrderStatusDao;

	@Autowired
	private CustomerOrderDao customerOrderDao;
	@Autowired
	private CommonUsersService usersService;
	@Autowired
	private CommonResturantsService resturantsService;
	@Autowired
	private CustomerOrderStatusService orderStatusService;

	public List<Map> getAllAdmin(){

		List<Map> list=new ArrayList<>();
		List<CustomerOrderStatus> statuses=OrderStatusDao.findAll();
		if(!statuses.isEmpty()) {
			statuses.stream().forEach(
					status->{
						Map Row=new HashMap<>();
						Row.put("id", status.getId());
						Row.put("label", status.getAdminLabel());
						list.add(Row);
					}
			);
		}


		return list;

	}

	/**
	 * @apiNote
	 * requirementType ==1 ===>> Admin
	 * requirementType ==2 ===>> Resturant
	 * requirementType ==3 ===>> Customer
	 * **/
	public String findLabelByIdAndRequirementType(long id,int requirementType) {
		String label="";
		CustomerOrderStatus Status=OrderStatusDao.findById(id);
		if(Status!=null) {
			if(requirementType==1) {
				label=Status.getAdminLabel();
			}else if(requirementType==2){
				label=Status.getResturantLabel();
			}else if(requirementType==3){
				label=Status.getCustomerLabel();
			}

		}
		return label;
	}

	public List<Map> getOrdersCount(){
		List<Map> list=new ArrayList<>();
		List <Map> totalOrderStatusCountsMap = new ArrayList<>();
		List<CustomerOrderStatus> statuses=OrderStatusDao.findAll();
		List <Long> idss = new ArrayList<>();

		if(!statuses.isEmpty()) {
			statuses.stream().forEach(
					status->{
						Map Row=new HashMap<>();
						Row.put("id", status.getId());
						list.add(Row);
					}
			);
		}
		for (Map<String, Long> map : list) {
			for (Long value : map.values()) {
				idss.add(value);
			}
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime currentTime = LocalDateTime.now();
		String currentFormattedDate = currentTime.format(formatter);

		System.out.println("currentDate "+ currentFormattedDate);

			Map totalOrderStatusCountsRow = new HashMap();
				totalOrderStatusCountsRow.put("allOrdersCount",customerOrderDao.countAllByOrderStatusId(idss,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("pendingCount",customerOrderDao.countByOrderStatusIdPending(1l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("processingCount",customerOrderDao.countByOrderStatusIdInProcessing(2l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("confirmCount",customerOrderDao.countByOrderStatusIdConfirm(3l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("placedAtResturantCount",customerOrderDao.countByOrderStatusIdPlacedAtResturant(4l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("pickUp", customerOrderDao.countByOrderStatusIdPickUp(5l, currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("readyForPickupCount",customerOrderDao.countByOrderStatusIdReadyForPickup(5l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("deliveredCount",customerOrderDao.countByOrderStatusIdDelivered(7l,currentFormattedDate, currentFormattedDate));
				totalOrderStatusCountsRow.put("cancelledCount", customerOrderDao.countByOrderStatusIdCancelled(8l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("failedCount",customerOrderDao.countByOrderStatusIdFailed(9l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("rejectedCount",customerOrderDao.countByOrderStatusIdRejected(10l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("onTheWayCount",customerOrderDao.countByOrderStatusIdOnTheWay(14l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("delayedCount", customerOrderDao.countByOrderStatusIdDelayed(15l,currentFormattedDate,currentFormattedDate));
				totalOrderStatusCountsRow.put("testCount",customerOrderDao.countByOrderStatusIdTest(17l,currentFormattedDate,currentFormattedDate));
			totalOrderStatusCountsMap.add(totalOrderStatusCountsRow);

		return totalOrderStatusCountsMap;

	}

	public List<Map> getAllCustomersOrderHistory(Long customerId){
		List<Long> orderStatusIds=new ArrayList<>();
		List<Map> list = new ArrayList<>();

		orderStatusIds.add(7l);
		orderStatusIds.add(8l);
		orderStatusIds.add(9l);
		orderStatusIds.add(10l);

		List<CustomerOrder> customerOrdersList = new ArrayList<>();
		customerOrdersList = customerOrderDao.finalStatusesCustomerOrder(customerId, orderStatusIds);


		if (!customerOrdersList.isEmpty() || customerOrdersList != null){
			for (int i = 0; i < customerOrdersList.size(); i++){
				Map row = new HashMap();
	//			row.put("previousRecords", customerOrdersList);
				row.put("resturantId", customerOrdersList.get(i).getResturantId());
				row.put("resturantLabel",resturantsService.findLabelById(customerOrdersList.get(i).getResturantId()));
				row.put("resturantBanner",resturantsService.findBannerImgById(customerOrdersList.get(i).getResturantId()));
				row.put("customerId", customerId);
				row.put("customerName", usersService.getUserName(customerId));
				row.put("customerNumber", usersService.getUserContactNumber(customerId));
				row.put("totalAmount",customerOrdersList.get(i).getTotalAmount());
				row.put("paymentType",customerOrdersList.get(i).getOrderType());
				row.put("orderStatusLabel", orderStatusService.findLabelByIdAndRequirementType(customerOrdersList.get(i).getOrderStatusId(),1));
				row.put("grossAmount",customerOrdersList.get(i).getSubtotal());
				row.put("orderTime",customerOrdersList.get(i).getCreatedOn());
				row.put("orderId",customerOrdersList.get(i).getId());

				list.add(row);
			}



		}

		return list;

	}





}
